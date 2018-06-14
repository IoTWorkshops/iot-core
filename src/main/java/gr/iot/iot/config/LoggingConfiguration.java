package gr.iot.iot.config;

import ch.qos.logback.classic.AsyncAppender;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.net.SyslogAppender;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.RollingPolicyBase;
import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

/**
 * Created on 7/22/16.
 */
@Configuration
public class LoggingConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingConfiguration.class);
    private final LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    @Autowired
    private Environment env;


    @PostConstruct
    private void init() {

        if (env.getProperty("logging.console.custom.active", Boolean.class)) {
            addCustomConsoleAppender();
        }

        defaultConsoleAppender();

        if (env.getProperty("logging.syslog.active", Boolean.class)) {
            addSyslogAppender();
        }

        if (env.getProperty("logging.file.active", Boolean.class)) {
            addLogFileAppender();
        }

    }

    private void defaultConsoleAppender() {

        if (!env.getProperty("logging.console.default.active", Boolean.class)) {
            context.getLogger("ROOT").detachAppender("defaultConsoleAppender");
        }
    }

    private void addCustomConsoleAppender() {
        LOGGER.info("Initializing custom console logging");
        StopWatch watch = new StopWatch();
        watch.start();

        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setName("CONSOLE_APPENDER");
        consoleAppender.setContext(context);
        consoleAppender.setWithJansi(env.getProperty("logging.console.custom.withJansi", Boolean.class));

        PatternLayoutEncoder patternLayoutEncoder = new PatternLayoutEncoder();
        patternLayoutEncoder.setContext(context);
        patternLayoutEncoder.setPattern(env.getProperty("logging.console.custom.encoder.pattern"));
        patternLayoutEncoder.setCharset(Charset.forName(env.getProperty("logging.console.custom.encoder.charset")));
        patternLayoutEncoder.start();

        consoleAppender.setEncoder(patternLayoutEncoder);

        consoleAppender.start();

        context.getLogger("ROOT").addAppender(consoleAppender);

        watch.stop();
        LOGGER.info("Custom console logging started in {} ms", watch.getTotalTimeMillis());
    }

    private void addSyslogAppender() {
        LOGGER.info("Initializing syslog logging");
        StopWatch watch = new StopWatch();
        watch.start();

        SyslogAppender syslogAppender = new SyslogAppender();
        syslogAppender.setName("SYSLOG_APPENDER");
        syslogAppender.setContext(context);
        syslogAppender.setSyslogHost(env.getProperty("logging.syslog.host"));
        syslogAppender.setPort(env.getProperty("logging.syslog.port", Integer.class));
        syslogAppender.setFacility(env.getProperty("logging.syslog.facility"));
        syslogAppender.setThrowableExcluded(env.getProperty("logging.syslog.throwableExcluded", Boolean.class));
        syslogAppender.setSuffixPattern(
            env.getProperty("name") + " " + env.getProperty("logging.syslog.suffixPattern")
        );
        syslogAppender.start();

        if (env.getProperty("logging.syslog.async.active", Boolean.class)) {
            LOGGER.info("Adding async appender to syslog logging");
            AsyncAppender asyncAppender = new AsyncAppender();
            asyncAppender.setContext(context);
            asyncAppender.setName("ASYNC_SYSLOG_APPENDER");
            asyncAppender.setQueueSize(env.getProperty("logging.syslog.async.queueSize", Integer.class));
            asyncAppender.addAppender(syslogAppender);
            asyncAppender.start();

            context.getLogger("ROOT").addAppender(asyncAppender);
        } else {
            context.getLogger("ROOT").addAppender(syslogAppender);
        }

        watch.stop();
        LOGGER.info("Syslog logging started in {} ms", watch.getTotalTimeMillis());
    }

    private void addLogFileAppender() {

        LOGGER.info("Initializing rolling file logging");
        StopWatch watch = new StopWatch();
        watch.start();

        RollingFileAppender rollingFileAppender = new RollingFileAppender();
        rollingFileAppender.setContext(context);
        rollingFileAppender.setFile(env.getProperty("logging.file.file"));

        RollingPolicyBase rollingPolicyBase = new TimeBasedRollingPolicy<>();

        SizeAndTimeBasedFNATP<ILoggingEvent> sizeAndTimeBasedFNATP = new SizeAndTimeBasedFNATP<>();
        sizeAndTimeBasedFNATP.setMaxFileSize(FileSize.valueOf(env.getProperty("logging.file.maxFileSize")));
        sizeAndTimeBasedFNATP.setTimeBasedRollingPolicy((TimeBasedRollingPolicy) rollingPolicyBase);

        ((TimeBasedRollingPolicy) rollingPolicyBase).setTimeBasedFileNamingAndTriggeringPolicy(sizeAndTimeBasedFNATP);
        ((TimeBasedRollingPolicy) rollingPolicyBase).setFileNamePattern(env.getProperty("logging.file.fileNamePattern"));
        ((TimeBasedRollingPolicy) rollingPolicyBase).setMaxHistory(30);
        rollingPolicyBase.setContext(context);
        rollingPolicyBase.setParent(rollingFileAppender);
        rollingPolicyBase.start();

        PatternLayoutEncoder patternLayoutEncoder = new PatternLayoutEncoder();
        patternLayoutEncoder.setContext(context);
        patternLayoutEncoder.setPattern(env.getProperty("logging.file.encoder.pattern"));
        patternLayoutEncoder.setCharset(Charset.forName(env.getProperty("logging.file.encoder.charset")));
        patternLayoutEncoder.start();

        rollingFileAppender.setRollingPolicy(rollingPolicyBase);
        rollingFileAppender.setEncoder(patternLayoutEncoder);
        rollingFileAppender.start();

        if (env.getProperty("logging.file.async.active", Boolean.class)) {
            LOGGER.info("Adding async appender to file logging");
            AsyncAppender asyncAppender = new AsyncAppender();
            asyncAppender.setContext(context);
            asyncAppender.setName("ASYNC_SYSLOG_APPENDER");
            asyncAppender.setQueueSize(env.getProperty("logging.file.async.queueSize", Integer.class));
            asyncAppender.addAppender(rollingFileAppender);
            asyncAppender.start();

            context.getLogger("ROOT").addAppender(asyncAppender);
        } else {
            context.getLogger("ROOT").addAppender(rollingFileAppender);
        }

        watch.stop();
        LOGGER.info("Rolling file logging started in {} ms", watch.getTotalTimeMillis());
    }
}

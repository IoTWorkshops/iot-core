package gr.iot.iot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

import javax.annotation.PreDestroy;


@SuppressWarnings("unused")
@Configuration
@EnableCaching
@AutoConfigureAfter(value = {DatabaseConfiguration.class})
public class CacheConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfiguration.class);

    private net.sf.ehcache.CacheManager cacheManager;

    @PreDestroy
    public void destroy() {
        LOGGER.info("Closing Cache Manager");
        cacheManager.shutdown();
    }

    @Bean
    public CacheManager cacheManager() {
        LOGGER.info("Starting Ehcache");
        StopWatch watch = new StopWatch();
        watch.start();

        cacheManager = net.sf.ehcache.CacheManager.create();

        EhCacheCacheManager ehCacheManager = new EhCacheCacheManager();
        ehCacheManager.setCacheManager(cacheManager);

        watch.stop();
        LOGGER.info("Started Ehcache in {} ms", watch.getTotalTimeMillis());

        return ehCacheManager;
    }
}

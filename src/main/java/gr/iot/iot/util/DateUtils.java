package gr.iot.iot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;


public class DateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    private DateUtils() {

    }

    public static Long currentTimestampUtc() throws ParseException {
        Instant instant = Instant.now();
        return instant.getEpochSecond();
    }

    public static Date stringToDate(String d, boolean showSeconds) {
        d = d.replaceAll("^\\s+", "");
        Date res = null;

        try {
            if (!d.equals("")) {
                if (showSeconds) {
                    res = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss").parse(d);
                    new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(res);
                } else {
                    res = new SimpleDateFormat("dd/mm/yyyy hh:mm").parse(d);
                    new SimpleDateFormat("yyyy-MM-dd hh:mm").format(res);
                }
            }
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return res;
    }
}

package com.github.essien.banquemisr.irrigation;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;

/**
 * @author bodmas
 * @since Nov 26, 2022.
 */
public class Constants {

    private Constants() {
    }

    public static final ZoneId ZONE_GMT = ZoneId.of("Z");
    public static final LocalDate EPOCH_DATE = LocalDate.of(1970, Month.JANUARY, 1);
}

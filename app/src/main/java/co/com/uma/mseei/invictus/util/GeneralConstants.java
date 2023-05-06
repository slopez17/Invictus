package co.com.uma.mseei.invictus.util;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

public class GeneralConstants {
    public static final String CLEAN = "";
    public static final String HYPHEN_WITH_SPACE = " - ";
    public static final String HASH = "#";
    public static final int ERROR = -1;

    public static final String KG_UND = "kg";
    public static final String LBS_UND = "lbs";
    public static final String M_UND = "m";
    public static final String IN_UND = "in";

    public static final DecimalFormat TWO_DIGITS = new DecimalFormat("#.00");
    public static final DateTimeFormatter DD_MMM_YYYY = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public static final String SELECTED_SPORT = "selected_sport";

}

package com.wlcg.aroundme.cc.util;


public class Constants {
    public static final boolean DEBUG = true;
    
    public static final String rawkey = "517662315";

    public static final String PREF_NAME = "AroundMe";
    
    public static final long OUTDATED_LOCATION_THRESHOLD_MILLIS = 10L * 60L * 1000L; // 10 minutes
    
    public static final String WEATHER_ICONS = "weather_icons";
    public static final String MONOCHROME = "mono";
    public static final String USE_METRIC = "metric";
    public static final String CITY_ID = "city_id";
    public static final String CITY_NAME = "city_name";
    public static final String COUNTRY_NAME = "country_name";
    public static final String WEATHER_REFRESH_INTERVAL = "weather_refresh_interval";
    public static final String WEATHER_REFRESH_TIMESTAMP = "weather_refresh_timestamp";
    public static final String CALENDAR_24H_FORMATE = "calendar_24h_formate";
    
    public static final String ISREMEMBERPWD = "is_remember_pwd";
    public static final String USER_PWD = "user_pwd";
    public static final String ISAUTOLOG = "is_auto_log";
    public static final String USER_ACOUNT = "user_acount";
    public static final String IS_FIRST_LOG = "is_first_log";
    
    public final static byte TEMP_UPDATE = 0X10;
    public final static byte HUMINITY_UPDATE = 0X11;
    public final static byte AIR_UPDATE = 0X12;
    
}
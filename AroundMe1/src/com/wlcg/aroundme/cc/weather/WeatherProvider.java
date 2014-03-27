package com.wlcg.aroundme.cc.weather;

import java.util.List;

import android.location.Location;

public interface WeatherProvider {
	
	/**
	 * 抽象类,继承此类以实现某一个具体的天气源,从而在WeatherEngine.java里面进行操作
	 *
	 */
	    public class LocationResult {
	        public String id;
	        public String city;
	        public String postal;
	        public String countryId;
	        public String country;
	    }

	    /**
	     * @param input city,State/Country
	     * @return
	     */
	    List<LocationResult> getLocations(String input);
	    
	    WeatherResProvider getWeatherResProvider();

	    /**
	     * @param id
	     * @param localizedCityName
	     * @param metricUnits
	     * @return
	     */
	    WeatherInfo getWeatherInfo(String id, String localizedCityName, boolean metricUnits);

	    /**
	     * @param location
	     * @param metricUnits
	     * @return
	     */
	    WeatherInfo getWeatherInfo(Location location, boolean metricUnits);
	    /**
	     * @return
	     */
	    //WeatherInfo getWeatherInfo();
	    
	    //void refreshData();
	    //void refreshData(String id, String localizedCityName, boolean metricUnits);
	    //void refreshData(Location location, boolean metricUnits);

	    int getNameResourceId();
	    
	    void setDataChangedListener(WeatherDataChangedListener listener);
}

package com.wlcg.aroundme.cc.weather;

/*************************************************************************

Copyright 2014 MagicMod Project

This file is part of MagicMod Weather.

MagicMod Weather is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

MagicMod Weather is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with MagicMod Weather. If not, see <http://www.gnu.org/licenses/>.

*************************************************************************/
import java.util.ArrayList;

/**
 * ���������������ϸ
 * 
 * @author SunRain 2014��1��12��
 */
public class WeatherInfo {
    public static final String DATA_NULL = "N/A";

    private String yujing;// �Ƿ���Ԥ��������ޣ���Ϊ������Ԥ����
    private String alarmtext;

    private ArrayList<DayForecast> forecasts;

    public WeatherInfo(ArrayList<DayForecast> forecasts) {
        this.forecasts = forecasts;
    }

    /**
     * 2014��1��19��
     * 
     * the ArrayList should be ordered by weather date
     * 
     * @return
     */
    public ArrayList<DayForecast> getDayForecast() {
        return forecasts;
    }

    public static class DayForecast {
        private String city;
        private String date; // �������ݵ�����
        private String temperature; // ��ǰ�¶�
        private String temphight; // ����¶�
        private String templow; // ����¶�
        private String tempUnit; // �¶ȸ�ʽ==>���ϻ��߻���
        private String condition; // �������=>��/��֮��
        private String conditionCode; //������Ӧ��ͼ��
        private String windSpeed; // ����
        private String windDirection; // ����
        private String windSpeedUnit; // ���ٵ�λ,�� km/h
        private String humidity; // ʪ��
        private String synctimestamp; // ͬ��ʱ��

        private String PM2Dot5Data; // PM2.5
        private String AQIData; // AQI(��������ָ��)
        private String sunrise;
        private String sunset;

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity() {
            return city == null ? null : city;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDate() {
            return date == null ? null : date;
        }

        public void setTemperature(String temp) {
            this.temperature = temp;
        }

        public String getTemperature() {
            return temperature == null ? DATA_NULL : temperature;
        }

        public void setTempHigh(String s) {
            this.temphight = s;
        }

        public String getTempHigh() {
            return temphight == null ? DATA_NULL : temphight;
        }

        public void setTempLow(String s) {
            this.templow = s;
        }

        public String getTempLow() {
            return templow == null ? DATA_NULL : templow;
        }

        public void setTempUnit(String s) {
            this.tempUnit = s;
        }

        public String getTempUnit() {
            return tempUnit == null ? DATA_NULL : tempUnit;
        }

        public void setCondition(String s) {
            this.condition = s;
        }

        public String getCondition() {
            return condition == null ? DATA_NULL : condition;
        }

        public void setConditionCode(String s) {
            this.conditionCode = s;
        }

        public String getConditionCode() {
            return conditionCode == null ? DATA_NULL : conditionCode;
        }

        public void setWindSpeed(String s) {
            this.windSpeed = s;
        }

        public String getWindSpeed() {
            return windSpeed == null ? DATA_NULL : windSpeed;
        }

        public void setWindDirection(String s) {
            this.windDirection = s;
        }

        public String getWindDirection() {
            return windDirection == null ? DATA_NULL : windDirection;
        }

        public void setWindSpeedUnit(String s) {
            this.windSpeedUnit = s;
        }

        public String getWindSpeedUnit() {
            return windSpeedUnit == null ? DATA_NULL : windSpeedUnit;
        }

        public void setHumidity(String s) {
            this.humidity = s;
        }

        public String getHumidity() {
            return humidity == null ? DATA_NULL : humidity;
        }

        public void setSynctimestamp(String s) {
            this.synctimestamp = s;
        }

        public String getSynctimestamp() {
            return synctimestamp == null ? DATA_NULL : synctimestamp;
        }

        public void setPM2Dot5Data(String s) {
            this.PM2Dot5Data = s;
        }

        public String getPM2Dot5Data() {
            return PM2Dot5Data == null ? DATA_NULL : PM2Dot5Data;
        }

        public void setAQIData(String s) {
            this.AQIData = s;
        }

        public String getAQIData() {
            return AQIData == null ? DATA_NULL : AQIData;
        }

        public void setSunRaise(String s) {
            this.sunrise = s;
        }

        public String getSunRise() {
            return sunrise == null ? DATA_NULL : sunrise;
        }

        public void setSunSet(String s) {
            this.sunset = s;
        }

        public String getSunSet() {
            return sunset == null ? DATA_NULL : sunset;
        }

        @Override
        public String toString() {
            return String
                    .format("AllWeather [city = %s, day = %s, temperature = %s, tempUnit = %s, condition = %s, conditionCode = %s, wind = %s, windDirection = %s, windSpeedUnit = %s, humidity = %s, PM2Dot5Data = %s, AQIData = %s, sunraise = %s, sunset = %s, synctimestamp = %s ]",
                            city, date, temperature, tempUnit, condition, conditionCode, windSpeed,
                            windDirection, windSpeedUnit, humidity, PM2Dot5Data, AQIData, sunrise,
                            sunset, synctimestamp);
        }
    }

    @Override
    public String toString() {
        int i = 0;
        StringBuilder builder = new StringBuilder();
        for (DayForecast forecast : forecasts) {
            builder.append(String.format("DayForecast[%d] => {%s}\n", i, forecast.toString()));
            i++;
        }
        return builder.toString();
    }

}

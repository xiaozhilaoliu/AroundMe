package com.wlcg.aroundme.cc.weather;

/*
 * Copyright (C) 2014 The MagicMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Bitmap;


import java.util.Locale;

import com.wlcg.aroundme.cc.weather.WeatherInfo.DayForecast;

/**
 * ������,�̳д����Ա�õ�һ���޸Ĺ���DayForecast���ݼ�
 * 
 * ���������ڲ�ͬ����Դ�õ���ͬ�����������Ժ��������,�Ա����ձ�¶���û�������ͳһ
 * 
 */
public interface WeatherResProvider {
    
    /**
     * @param context
     * @param weatherInfo conditionCode from DayForecast
     * @param iconSet the style of the weather icons
     * @return the R.id.xx 
     */
    int getWeatherIconResId(Context context, String conditionCode, String iconSet);
    
    /**
     * @param context
     * @param weatherInfo conditionCode from DayForecast
     * @param iconSet the style of the weather icons
     * @return bitmap type of icon
     */
    Bitmap getWeatherIconBitmap(Context context, String conditionCode, String iconSet);
    
    /**
     * @param context
     * @param DayForecast DayForecast
     * @return pre-fixed DayForecast
     */
    DayForecast getPreFixedWeatherInfo(Context context, DayForecast forecast);
    
    /**
     * @param DayForecast DayForecast
     * @return pre-fixed DayForecast
     */
    DayForecast getPreFixedWeatherInfo(final DayForecast forecast);
    
    String getYear(final DayForecast forecast);
    String getMonth(final DayForecast forecast);
    String getDay(final DayForecast forecast);
    String getWeek(final DayForecast forecast, Context context);
}

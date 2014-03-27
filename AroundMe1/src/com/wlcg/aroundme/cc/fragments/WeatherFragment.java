package com.wlcg.aroundme.cc.fragments;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.wlcg.aroundme.cc.R;
import com.wlcg.aroundme.cc.baidumap.LocationManager;
import com.wlcg.aroundme.cc.callback.DataCallBack;
import com.wlcg.aroundme.cc.config.Preferences;
import com.wlcg.aroundme.cc.util.widget.RotateImageView;
import com.wlcg.aroundme.cc.weather.WeatherEngine;
import com.wlcg.aroundme.cc.weather.WeatherInfo;
import com.wlcg.aroundme.cc.weather.WeatherInfo.DayForecast;
import com.wlcg.aroundme.cc.weather.WeatherProvider;
import com.wlcg.aroundme.cc.weather.WeatherProvider.LocationResult;
import com.wlcg.aroundme.cc.weather.WeatherResProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Typeface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherFragment extends Fragment implements DataCallBack{
	private final static String TAG = "WeatherFragment";
	private WeatherEngine mWeatherEngine = null;
	private RotateImageView mUpdateProgressBar = null;
	private Context mContext = null;
	private Button location_bt = null;


	private TextView tv_city = null, tv_temp = null,tv_date = null,tv_huminity = null;
	private DigitalClock time_dc = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i(TAG, "here is onCreate");
		mWeatherEngine = WeatherEngine.getinstance(getActivity()
				.getApplicationContext());
		mContext = getActivity();
	}

	/**
	 * <h1>初始化天气的数据</h1> 2014-3-17
	 */
	public void initWeatherData() {
		WeatherInfo info = mWeatherEngine.getCache(); // get weather info from
														// cache
		if (null == info) {
			showCityInputDialog(true);
		}
		updateWeatherView(info, false);

	}

	public void updateWeatherView(WeatherInfo info, boolean b) {
		// TODO Auto-generated method stub
		if (info == null) {
			return;
		}

		WeatherProvider provider = mWeatherEngine.getWeatherProvider();
		WeatherResProvider res = provider.getWeatherResProvider();

		ArrayList<DayForecast> days = info.getDayForecast();
		final DayForecast today = res.getPreFixedWeatherInfo(mContext,
				days.get(0));

		tv_city.setText(today.getCity());
		tv_temp.setText(today.getTempHigh());
		tv_date.setText(today.getDate());
		tv_huminity.setText("Humidity:"+today.getHumidity());
	}

	/**
	 * <h1>弹出选择城市的对话框</h1> 2014-3-17
	 * 
	 * @param exitAppWhenFail
	 *            失败的时候是否退出
	 */
	public void showCityInputDialog(final boolean exitAppWhenFail) {
		final EditText editText = new EditText(getActivity());
		AlertDialog.Builder dialog = new Builder(getActivity());
		dialog.setView(editText);
		dialog.setTitle("Enter Location");

		dialog.setNegativeButton("cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (exitAppWhenFail) {
							getActivity().finish();
						}
					}
				});

		dialog.setPositiveButton("ok", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String city = editText.getText().toString();
				if (null != city && !city.isEmpty()) {
					showCitySelectDialog(city, exitAppWhenFail);
				} else {
					if (exitAppWhenFail) {
						getActivity().finish();
					}
				}
			}

		});

		dialog.show();
	}

	/**
	 * 
	 * <h1>选择城市的对话框</h1> 2014-3-17
	 * 
	 * @param city
	 * @param exitAppWhenFail
	 */
	private void showCitySelectDialog(String city, boolean exitAppWhenFail) {
		new WeatherLocationTask(mContext, city, exitAppWhenFail).execute();
	}

	private class WeatherLocationTask extends
			AsyncTask<Void, Void, List<LocationResult>> {

		private ProgressDialog mProgressDialog;
		private String mLocation;
		private Context mContext;
		private boolean mExitAppWhenFail;

		public WeatherLocationTask(Context context, String location,
				boolean exitAppWhenFail) {
			this.mContext = context;
			this.mExitAppWhenFail = exitAppWhenFail;
			this.mLocation = location;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(mContext);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setMessage("Verifying location");

			mProgressDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					cancel(true);
				}
			});
			mProgressDialog.show();
		}

		@Override
		protected List<LocationResult> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return mWeatherEngine.getWeatherProvider().getLocations(mLocation);
		}

		@Override
		protected void onPostExecute(List<LocationResult> results) {
			// TODO Auto-generated method stub
			super.onPostExecute(results);
			if (null == results || results.isEmpty()) {
				Toast.makeText(getActivity(), "Cannot retrieve location!",
						Toast.LENGTH_LONG).show();
				if (mExitAppWhenFail) {
					getActivity().finish();
				}
			} else if (results.size() > 1) {
				handleResultDisambiguation(results);
			} else {
				applyLocation(results.get(0));
			}
			mProgressDialog.dismiss();
		}

		/**
		 * <h1></h1> 2014-3-17
		 * 
		 * @param results
		 */
		private void applyLocation(final LocationResult results) {
			new WeatherUpdateTask(results, Preferences.isMetric(mContext))
					.execute();
		}

		private CharSequence[] buildItemList(List<LocationResult> results) {
			boolean needCountry = false, needPostal = false;
			String countryId = results.get(0).countryId;
			HashSet<String> postalIds = new HashSet<String>();

			for (LocationResult result : results) {
				if (!TextUtils.equals(result.countryId, countryId)) {
					needCountry = true;
				}
				String postalId = result.countryId + "##" + result.city;
				if (postalIds.contains(postalId)) {
					needPostal = true;
				}
				postalIds.add(postalId);
				if (needPostal && needCountry) {
					break;
				}
			}

			int count = results.size();
			CharSequence[] items = new CharSequence[count];
			for (int i = 0; i < count; i++) {
				LocationResult result = results.get(i);
				StringBuilder builder = new StringBuilder();
				if (needPostal && result.postal != null) {
					builder.append(result.postal).append(" ");
				}
				builder.append(result.city);
				if (needCountry) {
					String country = result.country != null ? result.country
							: result.countryId;
					builder.append(" (").append(country).append(")");
				}
				items[i] = builder.toString();
			}
			return items;
		}

		private void handleResultDisambiguation(
				final List<LocationResult> results) {
			CharSequence[] items = buildItemList(results);
			new AlertDialog.Builder(getActivity())
					.setSingleChoiceItems(items, -1, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							applyLocation(results.get(which));
							dialog.dismiss();
						}
					}).setNegativeButton("cancel", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (mExitAppWhenFail) {
								getActivity().finish();
							}
						}
					}).setTitle("select city").show();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_weather, container, false);
		initViews(v);
		return v;
	}

	private void initViews(View v) {
		tv_city = (TextView) v.findViewById(R.id.tv_city);
		tv_temp = (TextView) v.findViewById(R.id.tv_temp);
		time_dc = (DigitalClock)v.findViewById(R.id.time_dc);
		tv_date = (TextView)v.findViewById(R.id.tv_date);
		tv_huminity = (TextView)v.findViewById(R.id.tv_huminity);
		mUpdateProgressBar = (RotateImageView) v
				.findViewById(R.id.title_update_progress);
		location_bt = (Button) v.findViewById(R.id.location_bt);
		
		
		Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "sniglet_.ttf");
		tv_city.setTypeface(typeface);
		tv_temp.setTypeface(typeface);
		time_dc.setTypeface(typeface);
		tv_date.setTypeface(typeface);
		tv_huminity.setTypeface(typeface);
	}

	private LocationManager manager = null;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mUpdateProgressBar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LocationResult locationResult = new LocationResult();
				locationResult.id = Preferences.getCityID(mContext);
				locationResult.country = Preferences.getCityName(mContext);
				locationResult.city = Preferences.getCityName(mContext);
				new WeatherUpdateTask(locationResult, Preferences.isMetric(mContext)).execute();
			}
		});
		
		location_bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				manager = new LocationManager(mContext, new MyLocationListener());
				manager.startLocation();
				location_bt.setEnabled(false);
			}
		});
		initWeatherData();
	}

	private class WeatherUpdateTask extends AsyncTask<Void, Void, WeatherInfo> {
		private LocationResult mLocationResult;
		private Location mLocation;
		private boolean mIsMeric;

		public WeatherUpdateTask(LocationResult result, boolean isMeric) {
			this.mLocationResult = result;
			this.mIsMeric = isMeric;

		}

		public WeatherUpdateTask(Location location, boolean isMeric) {
			this.mLocation = location;
			this.mIsMeric = mIsMeric;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mUpdateProgressBar.startAnim();
		}

		@Override
		protected WeatherInfo doInBackground(Void... params) {
			WeatherInfo info = null;
			if (mLocationResult != null) {
				info = mWeatherEngine.getWeatherProvider().getWeatherInfo(
						mLocationResult.id, mLocationResult.city, mIsMeric);
			} else if (mLocation != null) {
				info = mWeatherEngine.getWeatherProvider().getWeatherInfo(
						mLocation, mIsMeric);
			}
			if (info != null) {
				mWeatherEngine.setToCache(info);
				Preferences.setCityID(mContext, mLocationResult.id);
				Preferences.setCountryName(mContext, mLocationResult.country);
				Preferences.setCityName(mContext, mLocationResult.city);
			}
			return info;
		}

		@Override
		protected void onPostExecute(WeatherInfo info) {
			super.onPostExecute(info);
			mUpdateProgressBar.stopAnim();
			updateWeatherView(info, true);
			// sendBroadcast(new
			// Intent(WeatherUpdateService.ACTION_FORCE_UPDATE));
		}
	}

	@Override
	public void dataChanged(Object data) {
		// TODO Auto-generated method stub
		
	}
	
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if (location == null) {
				return;
			}
			StringBuilder sb = new StringBuilder();
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if(location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
		     } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
		           sb.append("\naddr : ");
		           sb.append(location.getAddrStr());
			}
			location_bt.setEnabled(true);
			
			new WeatherLocationTask(mContext, location.getCity(), true).execute();
			manager.stopLocation();
		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
			// TODO Auto-generated method stub
			
		}
	}
}
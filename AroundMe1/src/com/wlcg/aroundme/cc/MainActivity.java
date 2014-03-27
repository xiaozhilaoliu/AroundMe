package com.wlcg.aroundme.cc;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import cc.wulian.ihome.wan.NetSDK;

import com.wlcg.aroundme.cc.fragments.AirFragment;
import com.wlcg.aroundme.cc.fragments.FirstFragment;
import com.wlcg.aroundme.cc.fragments.HumnityFragment;
import com.wlcg.aroundme.cc.fragments.SecondFragment;
import com.wlcg.aroundme.cc.fragments.TemperatureFragment;
import com.wlcg.aroundme.cc.fragments.ThirdFragment;
import com.wlcg.aroundme.cc.fragments.WeatherFragment;
import com.wlcg.aroundme.cc.util.NetControl;

public class MainActivity extends FragmentActivity implements OnClickListener{
	private Button humidity_button = null, temp_button = null,
			air_button = null, weather_button = null;
	private Fragment currentFragment = null;
	private Fragment contentFragment = null;

	private HumnityFragment humnityFragment = null;
	private AirFragment airFragment = null;
	private WeatherFragment weatherFragment = null;
	private TemperatureFragment temperatureFragment = null;
	
	
	//test fragment
	FirstFragment firstFragment = null;
	SecondFragment secondFragment = null;
	ThirdFragment thirdFragment = null;
	private MyFragmentPagerAdapter adapter = null;
	
	private ViewPager view_pager = null;
	private MyPageListener listener = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		humnityFragment = new HumnityFragment(this);
		airFragment = new AirFragment(this);
		weatherFragment = new WeatherFragment();
		temperatureFragment = new TemperatureFragment(this);

		humidity_button = (Button) this.findViewById(R.id.humidity);
		temp_button = (Button) this.findViewById(R.id.temp);
		air_button = (Button) this.findViewById(R.id.air);
		weather_button = (Button) this.findViewById(R.id.weather);
		
		firstFragment = new FirstFragment();
		secondFragment = new SecondFragment();
		thirdFragment = new ThirdFragment();

		view_pager = (ViewPager) this.findViewById(R.id.view_pager);
		adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
		listener = new MyPageListener();
		view_pager.setAdapter(adapter);
		view_pager.setOffscreenPageLimit(4);
		view_pager.setOnPageChangeListener(listener);
		view_pager.setCurrentItem(2);

		currentFragment = contentFragment = weatherFragment;

		humidity_button.setOnClickListener(this);
		temp_button.setOnClickListener(this);
		air_button.setOnClickListener(this);
		weather_button.setOnClickListener(this);
		NetControl.getInstance(getApplicationContext()).registFragment(humnityFragment, airFragment, weatherFragment, temperatureFragment);

//		if (savedInstanceState == null) {
//			getFragmentManager().beginTransaction()
//					.add(R.id.play_wave, weatherFragment).commit();
//		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (R.id.humidity == v.getId()) {
			view_pager.setCurrentItem(0);
		}else if (R.id.temp == v.getId()) {
			view_pager.setCurrentItem(1);
		} else if (R.id.air == v.getId()) {
			view_pager.setCurrentItem(3);
		} else if (R.id.weather == v.getId()) {
			view_pager.setCurrentItem(2);
		}
	}

//	public void swichContent(Fragment from, Fragment to) {
//		if ((from = currentFragment) != null) {
//			FragmentTransaction transaction = getFragmentManager()
//					.beginTransaction();
//			transaction.setCustomAnimations(R.animator.fragment_in,R.animator.fragment_out);
//			if (!to.isAdded()) {
//				transaction.hide(from).add(R.id.play_wave, to).commit();
//			} else {
//				transaction.hide(from).show(to).commit();
//			}
//			currentFragment = to;
//		}
//	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if(id == R.id.app_exit){
			showDialog();
			return true;
		}
		
		if(id == R.id.change_acount){
			Intent intent = new Intent(this, LogingActivity.class);
			Bundle bundle = new Bundle();
			intent.putExtras(bundle);
			try {
				NetSDK.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				NetSDK.uninit();
			}
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.splash_in, R.anim.splash_out);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void showDialog(){
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("是否退出，将退出程序")
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				})
				.setPositiveButton("退出", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(NetSDK.isConnected()){
							try {
								Log.i("MainActivity", "正在断开连接...");
								NetSDK.disconnect();
								Log.i("MainActivity", "断开连接成功...");
								
							} catch (Exception e) {
								e.printStackTrace();
							}
							finally{
								NetSDK.uninit();
								Log.i("MainActivity", "反初始化成功...");
							}
						}
						finish();
					}
				})
				.create().show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}
	
	public class MyFragmentPagerAdapter extends FragmentPagerAdapter{

		public MyFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			return super.instantiateItem(container, position);
		}
		@Override
		public android.support.v4.app.Fragment getItem(int arg0) {
			switch (arg0) {
			case 0:
				return humnityFragment;
			case 1:
				return temperatureFragment;
			case 2:
				return weatherFragment;
			case 3:
				return airFragment;
			default:
			return airFragment;
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 4;
		}
	}	
	
	public class MyPageListener implements OnPageChangeListener{
		private int state = 0;
		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			Log.i("MainActivity", "onPageScrolled"+position);
			if(positionOffset + positionOffsetPixels == 0){
				if(state == ViewPager.SCROLL_STATE_DRAGGING && position == 0){
					
				}
			}
			
		}
		@Override
		public void onPageSelected(int position) {
			Log.i("MainActivity", "onPageSelected"+position);
			
		}
		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
			Log.i("MainActivity", "onPageScrollStateChanged"+state);
			this.state = state;
		}
	}
}

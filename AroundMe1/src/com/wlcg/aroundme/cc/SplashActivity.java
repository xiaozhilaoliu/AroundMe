package com.wlcg.aroundme.cc;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import cc.wulian.ihome.wan.NetSDK;
import cc.wulian.ihome.wan.util.ResultUtil;

import com.wlcg.aroundme.cc.config.Preferences;
import com.wlcg.aroundme.cc.util.AESEncryptor;
import com.wlcg.aroundme.cc.util.Constants;
import com.wlcg.aroundme.cc.util.NetControl;

public class SplashActivity extends Activity {
	private final static String TAG = "SplashActivity";
	private PackageManager manager = null;
	private NetControl netControl = null;
	private String appVersion = null;
	
	private TextView loading = null;

	private Intent mainIntent = null;
	
	private String user_name = null;
	private String user_code = null;
	private Handler netHandler = new Handler(){
		public void handleMessage(android.os.Message message) {
			switch (message.what) {
			case ResultUtil.EXC_GW_OFFLINE:
				Toast.makeText(getApplicationContext(),"gateway offline", Toast.LENGTH_SHORT)
				.show();
				finish();
				break;
			case ResultUtil.EXC_GW_USER_WRONG:
				Toast.makeText(getApplicationContext(),"wrong gateway id", Toast.LENGTH_SHORT)
				.show();
				finish();
				break;
			case ResultUtil.EXC_GW_PASSWORD_WRONG:
				Toast.makeText(getApplicationContext(),"wrong gateway password", Toast.LENGTH_SHORT)
				.show();
				Intent intent = new Intent(SplashActivity.this, LogingActivity.class);
				startActivity(intent);
				finish();
				break;
			case ResultUtil.EXC_GW_LOCATION:
				Toast.makeText(getApplicationContext(),"gateway in other server", Toast.LENGTH_SHORT)
				.show();
				break;
			case ResultUtil.EXC_GW_OVER_CONNECTION:
				Toast.makeText(getApplicationContext(), "server connection has full", Toast.LENGTH_SHORT)
				.show();
				finish();
				break;
			case ResultUtil.RESULT_SUCCESS:
				if(NetControl.handleCallBack.isConnectSev && !NetControl.handleCallBack.isConnectGw){
					loading.setText("正在登陆... ... ...");
					netHandler.removeMessages(-1);
				}
				if(NetControl.handleCallBack.isConnectGw){
					loading.setText("登陆成功... ... ...");
					startActivity(mainIntent);
					finish();
					overridePendingTransition(R.anim.splash_in,
							R.anim.splash_out);
				}
				break;
			case -1:
				Toast.makeText(getApplicationContext(), "连接服务器失败，请检查网络",Toast.LENGTH_SHORT)
				.show();
				finish();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		setContentView(R.layout.activity_splash);
		Bundle bundle = getIntent().getExtras();
		mainIntent = new Intent(this, MainActivity.class);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		// 获取网络管理工具
		netControl = NetControl.getInstance(getApplicationContext());
		if(bundle != null){
			user_name = bundle.getString("user_name");
			user_code = bundle.getString("user_code");
		}else{
			try {
				user_code = AESEncryptor.decrypt(Constants.rawkey, Preferences.getUserPwd(this));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user_name = Preferences.getUserAcount(this);
		}
		
		// 初始化网络工具，并且尝试登陆
		if(NetControl.handleCallBack == null)
			netControl.InitUtil(netHandler);
		netControl.attemptSignin(user_name,user_code);
	}

	private void init() {
		manager = this.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
			appVersion = info.versionCode + "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			finish();
		}
	}

	public class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_splash,
					container, false);
			TextView tv_logo = (TextView) rootView.findViewById(R.id.tv_logo);
			TextView tv_version = (TextView) rootView
					.findViewById(R.id.version);
			loading = (TextView) rootView.findViewById(R.id.loading);
			tv_version.setText(tv_version.getText() + "	" + appVersion);
			ObjectAnimator oAnimator = ObjectAnimator.ofFloat(tv_logo, "alpha",
					new float[] { 0f, 1.0f });

			oAnimator.setDuration(500);
			oAnimator.setRepeatCount(ObjectAnimator.INFINITE);
			oAnimator.setRepeatMode(ObjectAnimator.REVERSE);
			oAnimator.start();
			
			ObjectAnimator oAnimator1 = ObjectAnimator.ofFloat(loading, "alpha", 
					new float[]{0f,1.0f});
			
			oAnimator1.setDuration(500);
			oAnimator1.setRepeatCount(ObjectAnimator.INFINITE);
			oAnimator1.setRepeatMode(ObjectAnimator.REVERSE);
			oAnimator1.start();
			return rootView;
		}
	}
	
}

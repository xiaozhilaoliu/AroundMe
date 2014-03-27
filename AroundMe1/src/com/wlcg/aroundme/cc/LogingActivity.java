package com.wlcg.aroundme.cc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import cc.wulian.ihome.wan.util.MD5Util;

import com.wlcg.aroundme.cc.config.Preferences;
import com.wlcg.aroundme.cc.util.AESEncryptor;
import com.wlcg.aroundme.cc.util.Constants;

public class LogingActivity extends Activity implements OnClickListener,OnCheckedChangeListener{
	private EditText user_pwd = null,
					user_acount = null;
	private Button bt_log = null;
	private Button bt_aount = null;
	
	private CheckBox cb_pwd = null,
					cb_auto = null;
	
	private String user_name = null;
	private String user_code = null;
	
	private boolean is_remember_code = false;
	private boolean is_auto_log = false;
	private Intent intent = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loging);
		intent = new Intent(this, SplashActivity.class);
		user_acount = (EditText) this.findViewById(R.id.acount);
		user_pwd  = (EditText) this.findViewById(R.id.password);
		
		bt_aount = (Button) this.findViewById(R.id.bt_about);
		bt_log = (Button) this.findViewById(R.id.log_bt);
		
		cb_pwd = (CheckBox) this.findViewById(R.id.pwd_cb);
		cb_auto = (CheckBox) this.findViewById(R.id.auto_cb);
		
		bt_aount.setOnClickListener(this);
		bt_log.setOnClickListener(this);
	
		cb_auto.setOnCheckedChangeListener(this);
		cb_pwd.setOnCheckedChangeListener(this);
		
		user_name = Preferences.getUserAcount(this);
		user_code = Preferences.getUserPwd(this);
		
		is_auto_log = Preferences.getIsAutoLog(this);
		is_remember_code = Preferences.getIsRememberPWD(this);
		
		if(user_name != null){
			user_acount.setText(user_name);
		}
		
		if(is_remember_code){
			try {
				if(user_code != null){
					user_code = AESEncryptor.decrypt(Constants.rawkey,user_code);
					user_pwd.setText(user_code);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				Log.i("LogingActivity", "Ω‚¬Î ß∞‹"+e.toString());
			}
			cb_pwd.setChecked(true);
		}
		if(is_auto_log){
			cb_auto.setChecked(true);
		}
		if(is_auto_log && (getIntent().getExtras() == null)){
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void onClick(View v) {
		user_code = user_pwd.getText().toString();
		user_name = user_acount.getText().toString();
		Bundle bundle = new Bundle();
		Log.i("LogingActivity", user_code +"-----"+user_name);
		bundle.putString("user_code", user_code);
		bundle.putString("user_name", user_name);
		intent.putExtras(bundle);
		if(v.getId() == R.id.log_bt){
			if(cb_pwd.isChecked()){
				try {
					Preferences.setUserPwd(this, AESEncryptor.encrypt(Constants.rawkey, user_code));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Preferences.setUserAcount(this, user_name);
			startActivity(intent);
			finish();
		}
		
		if(v.getId() == R.id.bt_about){
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setData(Uri.parse("http://www.wulian.cc/"));
			startActivity(intent);
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.auto_cb:
			cb_auto.setChecked(isChecked);
			Preferences.setIsAutoLog(this, isChecked);
			break;

		case R.id.pwd_cb:
			cb_pwd.setChecked(isChecked);
			Preferences.setIsRememberPWD(this, isChecked);
			break;
		}
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Process.killProcess(Process.myPid());
			System.exit(1);
		}
		return super.onKeyUp(keyCode, event);
	}
}

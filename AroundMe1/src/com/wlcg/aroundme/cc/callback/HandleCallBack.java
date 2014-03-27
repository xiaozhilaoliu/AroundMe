package com.wlcg.aroundme.cc.callback;

import java.util.Iterator;
import java.util.Set;

import com.wlcg.aroundme.cc.util.NetControl.MessageHandle;

import android.os.Handler;
import android.os.Message;
import cc.wulian.ihome.wan.MessageCallback;
import cc.wulian.ihome.wan.NetSDK;
import cc.wulian.ihome.wan.entity.DeviceEPInfo;
import cc.wulian.ihome.wan.entity.DeviceInfo;
import cc.wulian.ihome.wan.entity.GatewayInfo;
import cc.wulian.ihome.wan.util.ResultUtil;

public class HandleCallBack implements MessageCallback {
	private static final String TAG = HandleCallBack.class.getSimpleName();

	public boolean isConnectSev = false; // 是否连上服务器
	public boolean isConnectGw = false; // 是否连上网关

	private MessageHandle mHandle = null;
	private Handler netHandler = null;

	public MessageHandle getmHandle() {
		return mHandle;
	}

	public void setmHandle(MessageHandle mHandle) {
		this.mHandle = mHandle;
	}

	public Handler getNetHandler() {
		return netHandler;
	}

	public void setNetHandler(Handler netHandler) {
		this.netHandler = netHandler;
	}

	public HandleCallBack(MessageHandle mHandle, Handler netHandler) {
		this.mHandle = mHandle;
		this.netHandler = netHandler;
	}

	@Override
	public void ConnectServer(int result) {
		log("ConnectServer", "result", result);

		isConnectSev = ResultUtil.RESULT_SUCCESS == result;
		if(isConnectSev){
			netHandler.sendEmptyMessage(ResultUtil.RESULT_SUCCESS);
		}
	}

	@Override
	public void ConnectGateway(int result, String gwID, GatewayInfo gwInfo) {
		log("ConnectGateway", "result", result, "gwID", gwID, "GatewayInfo",
				gwInfo.toString());
		checkGatewayResult(result);

		if (ResultUtil.RESULT_SUCCESS == result) {
			log("NetSDK.sendRefreshDevListMsg");
			//获取网关设备列表
			NetSDK.sendRefreshDevListMsg(gwID, null);
			isConnectGw = true;
			netHandler.sendEmptyMessage(result);
			
		} else {
			isConnectGw = false;
		}
	}

	private void checkGatewayResult(int result) {
		String msg;
		switch (result) {
		case ResultUtil.EXC_GW_OFFLINE:
			msg = "gateway offline";
			break;
		case ResultUtil.EXC_GW_USER_WRONG:
			msg = "wrong gateway id";
			break;
		case ResultUtil.EXC_GW_PASSWORD_WRONG:
			msg = "wrong gateway password";
			break;
		case ResultUtil.EXC_GW_LOCATION:
			msg = "gateway in other server";
			break;
		case ResultUtil.EXC_GW_OVER_CONNECTION:
			msg = "server connection has full";
			break;
		default:
			msg = null;
			break;
		}
		if(ResultUtil.RESULT_SUCCESS != result)
			netHandler.sendEmptyMessage(result);
		log(msg);
	}

	@Override
	public void DisConnectGateway(int result, String gwID) {
		log("DisConnectGateway", "result", result, "gwID", gwID);
		isConnectGw = false;
	}

	@Override
	public void GatewayData(int result, String gwID) {
		log("GatewayData", "result", result, "gwID", gwID);
	}

	@Override
	public void GatewayDown(String gwID) {
		log("GatewayDown", "gwID", gwID);
	}

	@Override
	public void DeviceUp(DeviceInfo devInfo, Set<DeviceEPInfo> devEPInfoSet) {
		log("DeviceUp", "gwID", devInfo.getGwID(), "devID", devInfo.getDevID()
				,"DeviceName =",devInfo.getName(),"Device Type="+devInfo.getType());
		System.out.println("deinfo json data = "+devInfo.getData().toString());
		Iterator<DeviceEPInfo> iterators = devEPInfoSet.iterator();
		while (iterators.hasNext()) {
			System.out.println("devEPinfoSet ====="+iterators.next().getEpData());
			
		}

		devInfo.setDevEPInfo((DeviceEPInfo) devEPInfoSet.toArray()[0]);
		Message.obtain(mHandle, MessageHandle.MSG_DEVICE_UP, devInfo)
				.sendToTarget();
	}

	@Override
	public void DeviceDown(String gwID, String devID) {
		log("DeviceDown", "gwID", gwID, "devID", devID);

		Message.obtain(mHandle, MessageHandle.MSG_DEVICE_DOWN, devID)
				.sendToTarget();
	}

	@Override
	public void DeviceData(String gwID, String devID, DeviceEPInfo devEPInfo) {
		log("DeviceData", "gwID", gwID, "devID", devID, "DeviceEPInfo",
				devEPInfo.toString());

		DeviceInfo devInfo = new DeviceInfo();
		devInfo.setGwID(gwID);
		devInfo.setDevID(devID);
		devInfo.setDevEPInfo(devEPInfo);
		Message.obtain(mHandle, MessageHandle.MSG_DEVICE_DATA, devInfo)
				.sendToTarget();
	}

	@Override
	public void HandleException(String gwID, Exception e) {
		log("HandleException", "gwID", gwID, "Exception",
				e.getCause() + e.getMessage());
	}

	private void log(Object... msgs) {
		System.out.print(TAG);
		for (Object msg : msgs) {
			System.out.print(" : " + msg);// LOG
		}
		System.out.println();// LOG
	}
}
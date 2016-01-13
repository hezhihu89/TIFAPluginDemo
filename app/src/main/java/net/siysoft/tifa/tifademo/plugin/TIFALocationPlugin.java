package net.siysoft.tifa.tifademo.plugin;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;

import net.siysoft.tifa.webview.TIFAWebView;
import net.siysoft.tifa.webview.plugin.WebViewJavaScriptPlugin;
import net.siysoft.tifa.webview.plugin.dialog.TIFAPluginDialog;

/**
 * Created by hezhihu89 on 2016/1/13.
 */
public class TIFALocationPlugin extends WebViewJavaScriptPlugin {

    private LocationManager mLM;
    private Context mContext;
    private TIFAWebView tifaWebView;
    private String callbackName;

    @Override
    public void onAttach(TIFAWebView tifaWebView) {
        //获取到上下文
        this.mContext = tifaWebView.getContext();
        this.tifaWebView = tifaWebView;

}
    @JavascriptInterface
    public void getLocation(String callbackName) {
        this.callbackName = callbackName;
        //获取LocationManager 对象
        if (mLM == null) {
            mLM = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Log.d("TAG", "经度:,纬度");
                //使用GPS 进行位置获取
                mLM.requestLocationUpdates(LocationManager.GPS_PROVIDER,  //使用GPS获取位置
                        1000, //1000毫秒
                        0, //0米
                        listener); //监听
            }
        }).start();

    }

    private LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d("TAG", "经度:" + location.getLongitude() + ",纬度" + location.getLatitude());
            //通过回调方法将经纬度返回给 html页面处理
            tifaWebView.callJavaScriptCallback(callbackName, "经度:" + location.getLongitude(),
                    "纬度" + location.getLatitude());
            //获取到位置之后 移除监听
            mLM.removeUpdates(listener);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

}

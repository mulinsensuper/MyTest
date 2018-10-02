package com.baidu.roosdkdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.baidu.tvsafe.TVSafe;
import android.os.SystemProperties;

/**
 * Created by sen on 2018/8/10.
 */

public class Receivegb extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action  = intent.getAction();
        Log.i("tvsafe", "Receivegb " + "action=" + action);
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            // Log.i("tvsafe", "Receivegb " + "action=" + action);
            ConnectivityManager connection = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = connection.getActiveNetworkInfo();
            if ( mNetworkInfo != null && mNetworkInfo.isConnected() ) {
                Log.i("tvsafe", "net is connected ! ");
                if ( SystemProperties.get("sys.dns.scan.caller", "null").equals("null")) {
                    new Thread() {
                        @Override
                        public void run() {
                            Log.i("tvsafe", "aconnetokscan call TVSafe.startNetScanner() ");
                            TVSafe.startNetScanner();
                            SystemProperties.set("sys.dns.scan.caller", "aconnetok");
                        }
                    }.start();
                }
            }
        } else if (action.equals("com.changhong.sys.dns.change")) {
            new Thread() {
                @Override
                public void run() {
                    Log.i("tvsafe", "dnschangescan call TVSafe.startNetScanner() ");
                    TVSafe.startNetScanner();
                    SystemProperties.set("sys.dns.scan.caller", "dnschange");
                }
            }.start();
        }
    }
}

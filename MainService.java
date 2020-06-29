package com.example.mqttapp;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MainService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NotifyMng.createChannel(this, "mainService", "Main Service", NotificationManager.IMPORTANCE_HIGH);
        startForeground(1, NotifyMng.callNotify4mainService(this, "mainService", "MQTT App", "Running"));
        Log.d("TYM","serviceYes");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}

package com.example.wgustudentapp.Classes;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.wgustudentapp.R;

public class NotificationHelper extends ContextWrapper {

    public static final String channelId = "channelId";
    public static final String channelName = "Channel Name";

    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel(){

        NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if(manager == null){
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public NotificationCompat.Builder getChannelNotification(){
        return new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle("WGU Student App Alert!")
                .setContentText("You have a course or assessment event today!")
                .setSmallIcon(R.drawable.ic_launcher_foreground);
    }
}

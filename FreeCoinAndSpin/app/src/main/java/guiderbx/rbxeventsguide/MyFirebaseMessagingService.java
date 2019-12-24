package guiderbx.rbxeventsguide;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "8321";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();
        if (remoteMessage.getData() != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("myPref",MODE_PRIVATE);

            for (String key : remoteMessage.getData().keySet()) {
                String value = remoteMessage.getData().get(key);

                if (key.equals("link"))
                {
                    SharedPreferences.Editor mSharedEditor = sharedPreferences.edit();
                    mSharedEditor.putString("link", value);
                    mSharedEditor.apply();
                }
                if (key.equals("message"))
                {
                    SharedPreferences.Editor mSharedEditor = sharedPreferences.edit();
                    mSharedEditor.putString("message", value);
                    mSharedEditor.apply();
                }

            }

        }


        Intent resultIntent = new Intent(this , playActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.appicon);
        mBuilder.setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(resultPendingIntent);

        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(0 /* Request Code */, mBuilder.build());

    }
}

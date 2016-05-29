package com.antypaymentguard.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.antypaymentguard.R;
import com.antypaymentguard.activities.MainActivity;

import java.util.Random;

public class Notifier {
    static void showNotification(Context context, Intent intent, final int priority) {
        Log.d(TAG, "Show notification");

        final Bundle extras = intent.getExtras();
        if (extras == null) {
            Log.e(TAG, "Extras is null!");
            return;
        }

        String title = extras.getString(KEY_NOTIFICATION_TITLE);
        if (title == null) {
            title = "";
        }
        String content = extras.getString(KEY_NOTIFICATION_CONTENT);
        if (content == null) {
            content = "";
        }


        final int uniquePushId = new Random().nextInt();
        final String vibesString = extras.getString(KEY_NOTIFICATION_VIBES);
        boolean isVibrating = (vibesString == null || vibesString.equals("1"));
        Log.d("GCM", "Vibrations are: " + (isVibrating ? "ON" : "OFF"));

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        showNotification(uniquePushId, context, pendingIntent, isVibrating, title, content, priority);
    }

    public static void showNotification(final Context context, final long delay) {
        final Intent intent = new Intent(context, MainActivity.class);
        final String title = "Zbliża się okres rozliczniowy";
        final String content = "Dokanaj płatności, aby uniknąć opłaty!";
        intent.putExtra(KEY_NOTIFICATION_TITLE, title).putExtra(KEY_NOTIFICATION_CONTENT, content).putExtra(KEY_NOTIFICATION_VIBES, false);

        showDelayedNotification(context, System.currentTimeMillis() + delay, intent);
    }

    private static void showNotification(int id, Context context, PendingIntent pendingIntent, boolean isVibrating,
                                         String title, String content, final int priority) {
        NotificationCompat.Builder builder =
                createNotificationBuilder(context, pendingIntent, isVibrating, title, content, true, 0);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, builder.build());
    }

    public static boolean showDelayedNotification(Context context, long timeInMilliseconds, Intent intentToSend) {
        if (System.currentTimeMillis() > timeInMilliseconds) {
            Log.i("###hash", "Notification is too old: " + timeInMilliseconds + " current time: " + System.currentTimeMillis());
            return false;
        }

        Log.i("###hash", "Showing notification in future " + timeInMilliseconds + " current time: " + System.currentTimeMillis());


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMilliseconds, createPendingIntent(context, intentToSend));
        return true;
    }

    private static NotificationCompat.Builder createNotificationBuilder(Context context, PendingIntent pendingIntent,
                                                                        boolean isVibrating, String title,
                                                                        String content, boolean defaultSound,
                                                                        int priority) {
        return createNotificationBuilder(context, pendingIntent, isVibrating, title, content, defaultSound, priority,
                null);
    }

    private static NotificationCompat.Builder createNotificationBuilder(Context context, PendingIntent pendingIntent,
                                                                        boolean isVibrating, String title,
                                                                        String content, boolean defaultSound,
                                                                        int priority,
                                                                        @Nullable PendingIntent dismissIntent) {
        int flags = (isVibrating ? Notification.DEFAULT_VIBRATE : 0);
        if (defaultSound && isVibrating) {
            flags |= Notification.DEFAULT_SOUND;
        }

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_condition_not_fulfilled_24dp);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_condition_not_fulfilled_24dp)
                .setLargeIcon(largeIcon)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setContentText(content)
                .setAutoCancel(true)
                .setDefaults(flags)
                .setDeleteIntent(dismissIntent)
                .addAction(0, "Sprawdź", pendingIntent)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setPriority(priority);
        }

        return builder;
    }

    private static PendingIntent createPendingIntent(Context context, Intent intent) {
        Intent intentDelayed = new Intent(context, NotificationAlarmReceiver.class);
        intentDelayed.putExtra(NotificationAlarmReceiver.EXTRA_INTENT, intent);
        return PendingIntent.getBroadcast(context, 0, intentDelayed, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static final String TAG = Notifier.class.getName();

    public static final String KEY_NOTIFICATION_TITLE = "text1";
    public static final String KEY_NOTIFICATION_CONTENT = "text2";
    public static final String KEY_NOTIFICATION_VIBES = "vibes";
}

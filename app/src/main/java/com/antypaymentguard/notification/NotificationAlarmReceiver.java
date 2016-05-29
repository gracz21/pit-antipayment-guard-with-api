package com.antypaymentguard.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationAlarmReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(final Context context, final Intent intent) {
		Log.d(TAG, "onReceive");

		try {
			Intent intentToSend = intent.getParcelableExtra(EXTRA_INTENT);
			if (intentToSend == null) {
				Log.e(TAG, "No intent!");
				return;
			}
			Notifier.showNotification(context, intentToSend, 10000);
		} catch (Exception ex) {
			// TODO
		}
	}

	public static final String TAG = NotificationAlarmReceiver.class.getSimpleName();
	public static final String EXTRA_INTENT = "extra_intent";
}

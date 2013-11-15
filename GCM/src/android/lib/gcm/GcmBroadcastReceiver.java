package android.lib.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.text.TextUtils;

/**
 * {@link WakefulBroadcastReceiver} that receives GCM messages and delivers them to
 * an application-specific {@link GcmBaseIntentService} subclass.
 */
public abstract class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public final void onReceive(final Context context, final Intent intent) {
        final String className = this.getGcmIntentServiceClassName(intent);

        if (!TextUtils.isEmpty(className)) {
            WakefulBroadcastReceiver.startWakefulService(context, intent.setComponent(new ComponentName(context.getPackageName(), className)));
        }

        this.setResultCode(Activity.RESULT_OK);
    }

    /**
     * Returns the class name of the intent service that will handle GCM messages.
     * @param intent the intent being received.
     * @return the class name of the intent service that will handle GCM messages.
     */
    protected abstract String getGcmIntentServiceClassName(Intent intent);
}

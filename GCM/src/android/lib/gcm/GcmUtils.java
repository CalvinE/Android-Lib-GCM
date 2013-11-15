package android.lib.gcm;

import android.app.Activity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * GCM-related utility functions.
 */
public final class GcmUtils {
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private GcmUtils() {
    }

    /**
     * Checks whether Google Play Services are available on the device.
     * <p>Whenever possible, users will be prompted to install or update Google Play Services if it is not available.
     * Otherwise, the calling activity will end immediately for security reasons.</p>
     * @param activity the activity in which Google Play Services are being checked for availability.
     * @return <code>true</code> if Google Play Services are available; otherwise, <code>false</code>.
     */
    public static boolean checkPlayServices(final Activity activity) {
        final int code = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);

        if (code == ConnectionResult.SUCCESS) {
            return true;
        }

        if (GooglePlayServicesUtil.isUserRecoverableError(code)) {
            GooglePlayServicesUtil.getErrorDialog(code, activity, GcmUtils.PLAY_SERVICES_RESOLUTION_REQUEST).show();
        } else {
            activity.finish();
        }

        return false;
    }
}

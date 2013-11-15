GCM Client Android Library
==========================

A <a href="http://developer.android.com/google/gcm/index.html">Google Cloud Messaging (GCM)</a> client helper library for Android suitable for common use cases.

Implementing GCM Client
-----------------------

A full GCM implementation requires both implementations of a client and a server. The following sections only cover the steps involved in developing a GCM client.

**Step 1: Obtaining an API Key**

Follow <a href="http://developer.android.com/google/gcm/gs.html#gcm-service">this guide</a> to create a Google API project, enable the GCM service, and get your sender ID and an API key if you have not done this already.

**Step 2: Set Up Google Play Services**

You must set up your project to use the Google Play Services SDK, as described in <a href="http://developer.android.com/google/play-services/setup.html">here</a> and <a href="http://developer.android.com/google/gcm/client.html#play-services">here</a>.

**Step 3: Check for Google Play Services Availability**

As described in <a href="http://developer.android.com/google/gcm/client.html#app">here</a>, you should check the device for Google Play Services availbility before accessing Google Play Services features. This check should be done in two places: in the main activity's `onCreate()` and `onResume()` methods. For example:

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ...
        
        if (GcmUtils.checkPlayServices()) {
            // If this check succeeds, proceed with normal processing.
            // Otherwise, the client library will automatically prompt
            // the user to install or upgrade Google Play Services.
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        GcmUtils.checkPlayServices();
    }

**Step 4: Register for GCM**

You app must register with GCM servers before it can receive message. When an app registers, it receives a registration ID, which should then be stored for future use.

To register with GCM, extend <a href="https://github.com/ayltai/Android-Lib-GCM/GCM/src/android/lib/gcm/GcmRegistrar">GcmRegistrar</a>. In your main activity, call `getRegistrationId()` to see whether there is an existing registration ID stored in shared preferences.

If the registration ID does not exist, you should call `registerInBackground()` method to register. This takes place on a background thread.

After registering, `onRegister()` will be called. You should store the registration ID in a server-side application for future use.

**Step 5: Receive messages**

To receive GCM messages, extends <a href="https://github.com/ayltai/Android-Lib-GCM/GCM/src/android/lib/gcm/GcmIntentService">GcmIntentService</a>. When your app receives messages from GCM servers, `onMessageReceived()` method will be called with the message payload in the extras.

**Step 6: Edit You Application's Manifest**

You must edit your `AndroidManifest.xml` as described in <a href="http://developer.android.com/google/gcm/client.html#manifest">here</a>.

Here is an example manifest:

    ...
    <application ...>
        <receiver
            android:name=".MyGcmBroadcastReceiver"
            android:permission="com.google.android.c2dm.permission.SEND">
            <intent-filter>
                <action android:name="com.google.android.c2dm.intent.RECEIVE" />
                <category android:name="com.example.gcm" />
            </intent-filter>
        </receiver>
        <service android:name=".MyGcmIntentService" />
    </application>


package layout;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class SigListener extends Service
{
    private TelephonyManager TelephonManager;

    private static String TAG = "SigListener";
    int SignalStrength = 0;

    public SigListener()
    {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        MyPhoneStateListener phoneStateListener = new MyPhoneStateListener();
        TelephonManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        return 1;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        return null;
    }

    private class MyPhoneStateListener extends PhoneStateListener
    {
        @Override
        public void onSignalStrengthsChanged(android.telephony.SignalStrength signalStrength)
        {
            super.onSignalStrengthsChanged(signalStrength);
            SignalStrength = signalStrength.getGsmSignalStrength();
            SignalStrength = (2 * SignalStrength) - 113; // -> dBm
            Log.d("SignalChange", "" + SignalStrength);
        }
    }
}

package com.android.test.sigbuddy;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class SigBuddyCoreService extends Service
{


    TelephonyManager TelephonManager;
    LocationManager locationManager;
    MyPhoneStateListener pslistener;
    int SignalStrength = 0;
    private final static int SIGNAL_MIN_THRESHOLD = -90;
    private final static int SIGNAL_MAX_THRESHOLD = -87;
    private boolean isInBadArea;
    private Location lastLocation;

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Toast.makeText(getBaseContext(), "Intent received", Toast.LENGTH_SHORT).show();
        Log.d("SigBuddyCoreService", "Intent Received");
        try
        {
            pslistener = new MyPhoneStateListener();
            TelephonManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            TelephonManager.listen(pslistener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

            // Acquire a reference to the system Location Manager
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener()
            {
                public void onLocationChanged(Location location)
                {
                    lastLocation = location;
//                    Toast.makeText(getBaseContext(),""+location.getLatitude()+","+location.getLongitude(),Toast.LENGTH_SHORT).show();
                }

                public void onStatusChanged(String provider, int status, Bundle extras)
                {
                }

                public void onProviderEnabled(String provider)
                {
                }

                public void onProviderDisabled(String provider)
                {
                }
            };

            // Register the listener with the Location Manager to receive location updates
            try
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            } catch (SecurityException se)
            {
                se.printStackTrace();
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return 1;
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

            String lat = "";
            String lng = "";
            if (lastLocation != null)
            {
                lat = lastLocation.getLatitude() + "";
                lng = lastLocation.getLongitude() + "";
            }

            if (SignalStrength <= SIGNAL_MIN_THRESHOLD)
            {
                if (isInBadArea)
                {
                    Log.d("SignalStatus", "Already in bad area");
                } else
                {
                    isInBadArea = true;
                    if (lastLocation != null)
                    {
                        Intent intent = new Intent(getBaseContext(), SendSmsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("data", lastLocation.getLatitude() + " " + lastLocation.getLongitude());
                        startActivity(intent);
                    }
                    Toast.makeText(getBaseContext(), "Into bad area: " + SignalStrength + " " + lat + "," + lng, Toast.LENGTH_SHORT).show();
                }
            } else
            {
                if (isInBadArea)
                {
                    isInBadArea = false;
                    Toast.makeText(getBaseContext(), "Into good area: " + SignalStrength + " " + lat + "," + lng, Toast.LENGTH_SHORT).show();
                    //TODO: remind the user to call if he made calls in bad service
                } else
                {
                    Log.d("SignalStatus", "Already in good area");
                }
            }
        }
    }
}

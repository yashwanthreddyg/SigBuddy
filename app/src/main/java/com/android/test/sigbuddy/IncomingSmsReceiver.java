package com.android.test.sigbuddy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class IncomingSmsReceiver extends BroadcastReceiver
{
    private SmsManager smsManager;

    public IncomingSmsReceiver()
    {
        smsManager=SmsManager.getDefault();
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle bundle=intent.getExtras();
        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                assert pdusObj != null;
                for (Object aPdusObj : pdusObj)
                {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);

                    String senderNum = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    Log.d("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);


                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    /*Toast toast = Toast.makeText(context,
                            "senderNum: " + senderNum + ", message: " + message, duration);
                    toast.show();
*/
                    Securer securer=new Securer(5);
                    String decryptedMessage=securer.getDecryptedString(message);
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                    Intent intentSms=new Intent(context, SmsReceiveActivity.class);
                    intentSms.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intentSms.putExtra("data", message);
                    context.startActivity(intentSms);
                } // end for loop
            } // bundle is null

        } catch (Exception e)
        {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
        }
    }
}
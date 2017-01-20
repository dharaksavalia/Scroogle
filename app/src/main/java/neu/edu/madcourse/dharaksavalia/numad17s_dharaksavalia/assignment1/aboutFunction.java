package neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.assignment1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import neu.edu.madcourse.dharaksavalia.numad17s_dharaksavalia.R;


/**
 * Created by Dharak on 1/18/2017.
 */

public class aboutFunction extends Activity {
    TelephonyManager telephonyManager;
    final int PermissionYesorNo=1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        this.setTitle("About");
        if (ContextCompat.checkSelfPermission(aboutFunction.this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(aboutFunction.this,
                    Manifest.permission.READ_PHONE_STATE)) {

            }
            else{
                ActivityCompat.requestPermissions(aboutFunction.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        PermissionYesorNo);

            }
        }
        TextView imei_number = (TextView)findViewById(R.id.imeinumber);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        imei_number.setText("IMEI N0 : "+telephonyManager.getDeviceId());







    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PermissionYesorNo: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TextView imei_number = (TextView)findViewById(R.id.imeinumber);
                    telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    imei_number.setText(telephonyManager.getDeviceId());


                } else {
                    TextView imei_number = (TextView)findViewById(R.id.imeinumber);

                    imei_number.setText("Permission Denied for imei");

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}

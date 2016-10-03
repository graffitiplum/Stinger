package org.hacktivity.stinger;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private Switch toggleSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleSwitch = (Switch) findViewById(R.id.toggleSwitch);

        if (serviceRunning(WaspService.class)) {
            toggleSwitch.setText("On");
            toggleSwitch.setChecked(true);
        }
        else {
            toggleSwitch.setText("Off");
            toggleSwitch.setChecked(false);
        }
    }

    public void toggleService(View view) {

        // TODO: Better way to find if service is running?

        if (! serviceRunning(WaspService.class)) {
            startService(new Intent(this, WaspService.class));
            toggleSwitch.setText("On");
            toggleSwitch.setChecked(true);
        } else {
            stopService(new Intent(this, WaspService.class));
            toggleSwitch.setText("Off");
            toggleSwitch.setChecked(false);
        }

    }

    private boolean serviceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}

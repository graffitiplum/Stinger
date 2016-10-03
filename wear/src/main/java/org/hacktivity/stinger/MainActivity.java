package org.hacktivity.stinger;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Switch toggleSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                toggleSwitch = (Switch) stub.findViewById(R.id.toggleSwitch);

                if (serviceRunning(WaspService.class)) {
                    toggleSwitch.setText("On");
                    toggleSwitch.setChecked(true);
                }
                else {
                    toggleSwitch.setText("Off");
                    toggleSwitch.setChecked(false);
                }

            }
        });

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

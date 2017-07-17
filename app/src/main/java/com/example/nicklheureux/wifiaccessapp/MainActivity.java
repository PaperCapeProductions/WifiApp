package com.example.nicklheureux.wifiaccessapp;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.wifi.WifiManager;
import android.content.Context;

import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.text.NumberFormat;
import java.util.List;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Intent;

import com.hardsoftstudio.rxflux.RxFlux;
import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;

import actions.Actions;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;


public class MainActivity extends AppCompatActivity implements com.hardsoftstudio.rxflux.dispatcher.RxViewDispatch {
    private Calories c;
    private ProgressBar progressBar;
    private EditText walking_mins;

    private DailyCaloriesStore dailyCaloriesStore;
    private NumberFormat percentage = NumberFormat.getPercentInstance();
    private RxFlux rxFlux;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rxFlux = RxFlux.init(this.getApplication());

        c = new Calories();
        wifiManager();
    }

    public void calculate(View v) {
        TextView target = (TextView) findViewById(R.id.target_label);
        EditText runningMinutes = (EditText) findViewById(R.id.running_entry);
        EditText bikingMinutes = (EditText) findViewById(R.id.biking_entry);
        EditText walkingMinutes = (EditText) findViewById(R.id.walking_entry);

        String calories = target.getText().toString();
        String runningCalories = runningMinutes.getText().toString();
        String bikingCalories = bikingMinutes.getText().toString();
        String walkingCalories = walkingMinutes.getText().toString();

        try {
            c.setCalories(Double.parseDouble(calories));
            c.setBikingMinutes(Double.parseDouble(bikingCalories));
            c.setRunningMinutes(Double.parseDouble(runningCalories));
            c.setWalkingMinutes(Double.parseDouble(walkingCalories));


            Toast.makeText(this, "Keep up the good work!" + "\nCalories burned: "
                    + percentage.format(c.getPercentage()), Toast.LENGTH_LONG).show();
            progressBar.setProgress((int) (c.getPercentage() * 100));

        } catch (NumberFormatException nfe) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show();
        } catch (IllegalArgumentException iae) {
            Toast.makeText(this, iae.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void wifiManager() {
        final WifiManager wifi;
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true);


        wifi.startScan();

        IntentFilter i = new IntentFilter();
        i.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(new BroadcastReceiver(){
                             @Override
                             public void onReceive(Context c, Intent i){
                                 List<ScanResult> wireless = wifi.getScanResults(); // Returns a <list> of scanResults
                                 for (ScanResult scan : wireless) {

                                     if (scan.SSID.equals("\"" + "TravelTab" + "\"")) {
                                         boolean cont = true;
                                         for (WifiConfiguration w : wifi.getConfiguredNetworks()) {
                                             String s = "\"" + scan.SSID + "\"";
                                             String bs = scan.BSSID;
                                             if ((w.SSID != null && w.SSID.equals(s)) || (w.BSSID != null && w.BSSID.equals(bs))) {
                                                 cont = false;
                                                 break;
                                             }
                                         }

                                         if (cont) {
                                             WifiConfiguration config = new WifiConfiguration();
                                             config.SSID = "\"" + scan.SSID + "\"";
                                             config.BSSID = scan.BSSID;
                                             config.priority = 1;
                                             config.preSharedKey = "\"" + "123456789" + "\"";
                                             config.status = WifiConfiguration.Status.ENABLED;
                                             config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                                             config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                                             config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                                             config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                                             config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                                             String[] wepkey = {"123456789"};
                                             config.wepKeys = wepkey;
                                             WifiInfo wifiInfo = wifi.getConnectionInfo();
                                             int networkid = wifiInfo.getNetworkId();

                                             wifi.addNetwork(config);
                                             wifi.enableNetwork(networkid, true);
                                             wifi.saveConfiguration();
                                         }
                                     }
                                 }
                             }
                         }
                ,i);
    }

    @Override
    public void onRxError(@NonNull RxError error) {

    }

    @Override
    public void onRxViewRegistered() {
//        dailyCaloriesStore = dailyCaloriesStore.get(WifiAccessApp.get(this).getRxFlux().getDispatcher());
//        dailyCaloriesStore.register();
    }

    @Override
    public void onRxViewUnRegistered() {
        //dailyCaloriesStore.unregister();
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        return null;
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return null;
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case DailyCaloriesStore.ID:
                switch (change.getRxAction().getType()) {
                    case Actions.GET_CALORIES:
                        walking_mins.setText("IT WORKED!!");
                        break;
                    case Actions.GET_DATES:
                        break;
                }
                break;
        }
    }
}

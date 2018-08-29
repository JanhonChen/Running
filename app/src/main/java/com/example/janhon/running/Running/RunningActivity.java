package com.example.janhon.running.Running;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.janhon.running.OtherMainFunction.FilledFragment;
import com.example.janhon.running.OtherMainFunction.FriendShipFragment;
import com.example.janhon.running.R;
import com.example.janhon.running.OtherMainFunction.ShopFragment;
import com.example.janhon.running.OtherMainFunction.UserFragment;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class RunningActivity extends FragmentActivity  {
    TextView text, text2, text3;
    long starttime = 0;
    private Button b;
    //this  posts a message to the main thread from our timertask
    //and updates the textfield
    final Handler h = new Handler(new Handler.Callback() {

        @SuppressLint("DefaultLocale")
        @Override
        public boolean handleMessage(Message msg) {
            long millis = System.currentTimeMillis() - starttime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds     = seconds % 60;

            text.setText(String.format("%d:%02d", minutes, seconds));
            return false;
        }
    });
    //runs without timer be reposting self
    Handler h2 = new Handler();
    Runnable run = new Runnable() {

        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - starttime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds     = seconds % 60;

            text3.setText(String.format("%d:%02d", minutes, seconds));

            h2.postDelayed(this, 500);
        }
    };

    //tells handler to send a message
    class firstTask extends TimerTask {

        @Override
        public void run() {
            h.sendEmptyMessage(0);
        }
    };

    //tells activity to run on ui thread
    class secondTask extends TimerTask {

        @Override
        public void run() {
            RunningActivity.this.runOnUiThread(new Runnable() {

                @SuppressLint("DefaultLocale")
                @Override
                public void run() {
                    long millis = System.currentTimeMillis() - starttime;
                    int seconds = (int) (millis / 1000);
                    int minutes = seconds / 60;
                    seconds     = seconds % 60;

                    text2.setText(String.format("%d:%02d", minutes, seconds));
                }
            });
        }
    };


    Timer timer = new Timer();

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener //當選項轉換時進入監聽器.
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.item_Sport:
                    fragment = new RunningFragment();
                    changeFragment(fragment);
                    setTitle(R.string.textSubmit);
                    return true;
                case R.id.item_User:
                    fragment = new UserFragment();
                    changeFragment(fragment);
                    setTitle(R.string.textUser);
                    return true;
                case R.id.item_Filled:
                    fragment = new FilledFragment();
                    changeFragment(fragment);
                    setTitle(R.string.textFilled);
                    return true;
                case R.id.item_FriendShip:
                    fragment = new FriendShipFragment();
                    changeFragment(fragment);
                    setTitle(R.string.textFriendShip);
                    return true;
                case R.id.item_Shop:
                    fragment = new ShopFragment();
                    changeFragment(fragment);
                    setTitle(R.string.textShop);
                    return true;
                default:
                    initContent(); //設定為首頁
                    break;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        initContent();

    }

    private void initContent() {
        Fragment fragment = new RunningFragment();
        changeFragment(fragment);

        setTitle(R.string.tsStart);

    }

    private void changeFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.body, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        askPermissions();
    }

    private static final int REQ_PERMISSIONS = 0;

    // New Permission see Appendix A
    private void askPermissions() {
        //因為是群組授權，所以請求ACCESS_COARSE_LOCATION就等同於請求ACCESS_FINE_LOCATION，因為同屬於LOCATION群組
        String[] permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        Set<String> permissionsRequest = new HashSet<>();
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                permissionsRequest.add(permission);
            }
        }

        if (!permissionsRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionsRequest.toArray(new String[permissionsRequest.size()]),
                    REQ_PERMISSIONS);
        }
    }

    public void onStartClick(View view) {
        Intent intent = new Intent(this, RunningDataActivity.class);
        /* 建立Bundle儲存各科成績 */
//        Bundle bundle = new Bundle();
//        bundle.putInt("programming", programming);
//        bundle.putInt("dataStructure", dataStructure);
//        bundle.putInt("algorithm", algorithm);
        /* 將Bundle儲存在Intent內方便帶至下一頁 */
//        intent.putExtras(bundle);
        /* 呼叫startActivity()開啟新的頁面 */
        startActivity(intent);

        text = (TextView)findViewById(R.id.text);
        text2 = (TextView)findViewById(R.id.text2);
        text3 = (TextView)findViewById(R.id.text3);
        b = (Button)findViewById(R.id.btSubmit);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button)v;
                if(b.getText().equals("STOP")){
                    timer.cancel();
                    timer.purge();
                    h2.removeCallbacks(run);
                    // b.setText("start");
                }else{
                    starttime = System.currentTimeMillis();
                    timer = new Timer();
                    timer.schedule(new RunningActivity.firstTask(), 0,500);
                    timer.schedule(new RunningActivity.secondTask(),  0,500);
                    h2.postDelayed(run, 0);
                    // b.setText("stop");
                }
            }
        });


    }


}

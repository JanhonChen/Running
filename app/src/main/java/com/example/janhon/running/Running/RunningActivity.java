package com.example.janhon.running.Running;

import android.Manifest;
import android.content.pm.PackageManager;
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

import com.example.janhon.running.OtherMainFunction.FilledFragment;
import com.example.janhon.running.OtherMainFunction.FriendShipFragment;
import com.example.janhon.running.R;
import com.example.janhon.running.OtherMainFunction.ShopFragment;
import com.example.janhon.running.OtherMainFunction.UserFragment;

import java.util.HashSet;
import java.util.Set;

public class RunningActivity extends FragmentActivity  {


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
        Fragment fragment = new ShowRunningData();
        changeFragment(fragment);

    }


}

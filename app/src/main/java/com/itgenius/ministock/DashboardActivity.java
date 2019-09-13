package com.itgenius.ministock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    // Toolbar
    Toolbar toolbar;

    // BottomNavigationView
    BottomNavigationView navigationView;

    // Fragment
    final Fragment homeFragment = new HomeFragment();
    final Fragment reportFragment = new ReportFragment();
    final Fragment notificationFragment = new NotificationFragment();
    final Fragment accountFragment = new AccountFragment();

    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.nav_home);

        // BottomNavigationView
        navigationView = findViewById(R.id.bottom_navigation);

        // กำหนด Event Click ให้กับตัว BottomNavigationView
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Fragment Transaction
        fm.beginTransaction().add(R.id.content_main, homeFragment, "1").commit();
        fm.beginTransaction().add(R.id.content_main, reportFragment, "2").hide(reportFragment).commit();
        fm.beginTransaction().add(R.id.content_main, notificationFragment, "3").hide(notificationFragment).commit();
        fm.beginTransaction().add(R.id.content_main, accountFragment, "4").hide(accountFragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()){
                        case R.id.navhome:
                            fm.beginTransaction().hide(active).show(homeFragment).commit();
                            active = homeFragment;
                            getSupportActionBar().setTitle(R.string.nav_home);
                            return true;
                        case R.id.navreport:
                            fm.beginTransaction().hide(active).show(reportFragment).commit();
                            active = reportFragment;
                            getSupportActionBar().setTitle(R.string.nav_report);
                            return true;
                        case R.id.navnotificaton:
                            fm.beginTransaction().hide(active).show(notificationFragment).commit();
                            active = notificationFragment;
                            getSupportActionBar().setTitle(R.string.nav_notification);
                            return true;
                        case R.id.navaccount:
                            fm.beginTransaction().hide(active).show(accountFragment).commit();
                            active = accountFragment;
                            getSupportActionBar().setTitle(R.string.nav_account);
                            return true;
                    }

                    return false;
                }
            };
}

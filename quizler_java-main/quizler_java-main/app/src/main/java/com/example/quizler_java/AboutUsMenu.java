package com.example.quizler_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AboutUsMenu extends AppCompatActivity {
    BottomNavigationView bottom_navigation_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_menu);

        //title bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("About Us");  // Set the title
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Show back button

        bottom_navigation_view = findViewById(R.id.bottomNavigationView);
        bottom_navigation_view.setSelectedItemId(R.id.about_us);
        bottom_navigation_view.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    startActivity(new Intent(getApplicationContext(), MainMenu.class));
                    overridePendingTransition(0,0);
                    finish();
                }
                /*else if (item.getItemId() == R.id.settings) {
                    startActivity(new Intent(getApplicationContext(), SettingsMenu.class));
                    overridePendingTransition(0,0);
                    finish();
                }

                 */
                return false;
            }
        });
    }
}
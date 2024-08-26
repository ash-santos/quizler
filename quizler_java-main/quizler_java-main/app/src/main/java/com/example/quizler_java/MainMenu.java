package com.example.quizler_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {
    RecyclerView recyclerView;
    BottomNavigationView bottom_navigation_view;
    DatabaseConnection db;
    ArrayList<String> quiz_id, quiz_name;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        //title bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main Menu");  // Set the title

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Show back button

        recyclerView = findViewById(R.id.recycleView);
        bottom_navigation_view = findViewById(R.id.bottomNavigationView);
        bottom_navigation_view.setSelectedItemId(R.id.home);
        bottom_navigation_view.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.about_us){
                    startActivity(new Intent(getApplicationContext(), AboutUsMenu.class));
                    overridePendingTransition(0,0);
                    finish();
                }
                /*
                else if (item.getItemId() == R.id.settings) {
                    startActivity(new Intent(getApplicationContext(), SettingsMenu.class));
                    overridePendingTransition(0,0);
                    finish();
                }


                 */
                return false;
            }
        });
        db = new DatabaseConnection(MainMenu.this);
        quiz_id = new ArrayList<>();
        quiz_name = new ArrayList<>();
        storeDataInArrays();
        customAdapter = new CustomAdapter(MainMenu.this,quiz_id,quiz_name);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainMenu.this));

    }

    void storeDataInArrays(){
        Cursor cursor = db.read_quiz_name();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "no data ",Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                quiz_id.add(cursor.getString(0));
                quiz_name.add(cursor.getString(1));
            }
        }
    }
    public void create_quiz(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
        builder.setTitle("Create Quiz");

        // Create the EditText for user input
        final EditText editText = new EditText(MainMenu.this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setHint("Quiz name");
        builder.setView(editText);

        // Set the cancel button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Set the create button
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text = editText.getText().toString();
                addItemToList(text); // Add the text to your list or perform any desired operation
                dialog.dismiss();
            }
        });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void addItemToList(String text) {
        DatabaseConnection database = new DatabaseConnection(MainMenu.this);
        database.add_quiz(text);
        finish();
        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }


}
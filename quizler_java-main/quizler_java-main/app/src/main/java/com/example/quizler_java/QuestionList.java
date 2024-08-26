package com.example.quizler_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class QuestionList extends AppCompatActivity {
    RecyclerView recyclerView;
    BottomNavigationView bottom_navigation_view;
    DatabaseConnection db;
    ArrayList<String> quiz_id, quiz_name;
    ArrayList<String> quiz_question, quiz_answer,quiz_wrong_1,quiz_wrong_2,quiz_wrong_3,quiz_name_id;
    CustomAdapterQuestion customAdapter;
    String test_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        //title bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Question List");  // Set the title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Show back button

        recyclerView = findViewById(R.id.recycleView);
        Intent intent = getIntent();
        test_id = intent.getStringExtra("test_id");

        db = new DatabaseConnection(QuestionList.this);
        quiz_id = new ArrayList<>();
        quiz_name = new ArrayList<>();
        quiz_name_id = new ArrayList<>();
        quiz_question = new ArrayList<>();
        quiz_answer = new ArrayList<>();
        quiz_wrong_1= new ArrayList<>();
        quiz_wrong_2= new ArrayList<>();
        quiz_wrong_3= new ArrayList<>();

        storeDataInArrays();
        customAdapter = new CustomAdapterQuestion(QuestionList.this,quiz_id,quiz_name,quiz_name_id);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(QuestionList.this));

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) { // Check if back button is selected
            onBackPressed(); // Call onBackPressed() to go back to the previous activity or fragment
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    void storeDataInArrays(){
        Cursor cursor = db.read_quiz_question(test_id);
        if(cursor.getCount() == 0){
            Toast.makeText(this, "no data ",Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                quiz_id.add(cursor.getString(0));
                quiz_name_id.add(cursor.getString(1));
                quiz_name.add(cursor.getString(2));

            }
        }

    }

}
package com.example.quizler_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Flashcard extends AppCompatActivity {
int quiz_id;
private int currentIndex = 0;
String rightAnswer = "";
DatabaseConnection db;
private TextView txt_question, lblQuestion;
private Button btn_next;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        //title bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Flashcard");  // title yarn
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // back button yarn

        quiz_id = getIntent().getIntExtra("quiz_id", 0);
        txt_question = findViewById(R.id.txt_question);
        lblQuestion = findViewById(R.id.lblQuestion);
        btn_next = findViewById(R.id.btn_flashcard);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_next.getText().equals("Start")) {
                    displayNextData();
                    txt_question.setVisibility(View.VISIBLE);
                    lblQuestion.setVisibility(View.VISIBLE);
                    btn_next.setText("Show");
                } else if (btn_next.getText().equals("Show")) {
                    txt_question.setText(rightAnswer);
                    lblQuestion.setText("Answer");
                    btn_next.setText("Next");
                } else if (btn_next.getText().equals("Next")) {
                    displayNextData();
                    lblQuestion.setText("Question");
                    btn_next.setText("Show");
                }
            }
        });
        db = new DatabaseConnection(Flashcard.this);
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
    private void displayNextData() {
        DatabaseConnection database = new DatabaseConnection(this);
        SQLiteDatabase db = database.getReadableDatabase();

        //columns ung kukunan na data tpos ung selection ung where quiz_name = blablabla
        String selection = "quiz_name = ?";
        String[] selectionArgs = {String.valueOf(quiz_id)};
        String[] columns = {"quiz_question", "quiz_correct_answer","retention"};
        Cursor cursor = db.query("quiz_questions", columns, selection, selectionArgs, null, null, "retention");

        if (cursor.moveToPosition(currentIndex)) {
            @SuppressLint("Range") String data1 = cursor.getString(cursor.getColumnIndex("quiz_question"));
            @SuppressLint("Range") String data2 = cursor.getString(cursor.getColumnIndex("quiz_correct_answer"));
            @SuppressLint("Range") String data3 = cursor.getString(cursor.getColumnIndex("retention"));

            // Display the data in the text fields
            txt_question.setText(data1);
            rightAnswer = data2;
        }else {
            // All data has been shown
            txt_question.setVisibility(View.INVISIBLE);
            lblQuestion.setVisibility(View.INVISIBLE);
            btn_next.setText("Start");
            cursor.close();
            db.close();
            finish();
        }

        currentIndex++;
    }

}
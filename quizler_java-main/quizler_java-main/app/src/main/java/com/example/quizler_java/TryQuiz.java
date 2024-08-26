package com.example.quizler_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class TryQuiz extends AppCompatActivity {
    DatabaseConnection db;
    Button btn_1,btn_2,btn_3,btn_4,btn_start_next;
    TextView txt_try_quiz_question;
    int quiz_id,points;
    int currentIndex = 0;
    String data1,data2,data3,data4,data5,data6;
    String question_id;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_quiz);

        //title bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quiz Menu");  // Set the title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Show back button


        db = new DatabaseConnection(TryQuiz.this);
        quiz_id = getIntent().getIntExtra("quiz_id", 0);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_start_next = findViewById(R.id.btn_start_next);
        txt_try_quiz_question = findViewById(R.id.txt_try_quiz_question);
        btn_start_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_start_next.getText().equals("Start")) {
                    btn_1.setEnabled(true);
                    btn_2.setEnabled(true);
                    btn_3.setEnabled(true);
                    btn_4.setEnabled(true);
                    displayNextData();
                    btn_start_next.setText("Next");
                    btn_start_next.setVisibility(View.INVISIBLE);
                } else if (btn_start_next.getText().equals("Next")) {
                    displayNextData();
                    btn_1.setBackgroundColor(Color.MAGENTA);
                    btn_2.setBackgroundColor(Color.MAGENTA);
                    btn_3.setBackgroundColor(Color.MAGENTA);
                    btn_4.setBackgroundColor(Color.MAGENTA);
                    btn_1.setEnabled(true);
                    btn_2.setEnabled(true);
                    btn_3.setEnabled(true);
                    btn_4.setEnabled(true);
                    btn_start_next.setVisibility(View.INVISIBLE);
                }
            }
        });
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = btn_1.getText().toString();
                if (buttonText.equals(data2)) {
                    btn_1.setBackgroundColor(Color.GREEN);
                    score("Correct");
                }else {
                    btn_1.setBackgroundColor(Color.RED);
                    score("Wrong");
                }
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = btn_2.getText().toString();
                if (buttonText.equals(data2)) {
                    btn_2.setBackgroundColor(Color.GREEN);
                    score("Correct");
                }else {
                    btn_2.setBackgroundColor(Color.RED);
                    score("Wrong");
                }
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = btn_3.getText().toString();
                if (buttonText.equals(data2)) {
                    btn_3.setBackgroundColor(Color.GREEN);
                    score("Correct");
                }else {
                    btn_3.setBackgroundColor(Color.RED);
                    score("Wrong");
                }
            }
        });
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String buttonText = btn_4.getText().toString();
                if (buttonText.equals(data2)) {
                    btn_4.setBackgroundColor(Color.GREEN);
                    score("Correct");
                }else {
                    btn_4.setBackgroundColor(Color.RED);
                    score("Wrong");
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) { // Check if back button is selected

            quit_midway(); // Call onBackPressed() to go back to the previous activity or fragment
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("Range")
    private void displayNextData() {
        DatabaseConnection database = new DatabaseConnection(this);
        SQLiteDatabase db = database.getReadableDatabase();

        //columns ung kukunan na data tpos ung selection ung where quiz_name = blablabla
        String selection = "quiz_name = ?";
        String[] selectionArgs = {String.valueOf(quiz_id)};
        String[] columns = {"_id","quiz_question", "quiz_correct_answer","quiz_wrong_answer_1",
                "quiz_wrong_answer_2","quiz_wrong_answer_3","retention"};
        cursor = db.query("quiz_questions", columns, selection, selectionArgs, null, null, "_id");

        if (cursor.moveToPosition(currentIndex)) {
            question_id = cursor.getString(cursor.getColumnIndex("_id"));
            data1 = cursor.getString(cursor.getColumnIndex("quiz_question"));
            data2 = cursor.getString(cursor.getColumnIndex("quiz_correct_answer"));
            data3 = cursor.getString(cursor.getColumnIndex("quiz_wrong_answer_1"));
            data4 = cursor.getString(cursor.getColumnIndex("quiz_wrong_answer_2"));
            data5 = cursor.getString(cursor.getColumnIndex("quiz_wrong_answer_3"));
            data6 = cursor.getString(cursor.getColumnIndex("retention"));

            // Create an ArrayList to store the answer options
            ArrayList<String> answerOptions = new ArrayList<>();
            answerOptions.add(data2);  // Correct answer
            answerOptions.add(data3);
            answerOptions.add(data4);
            answerOptions.add(data5);

            // Shuffle the answer options
            Collections.shuffle(answerOptions);


            // Display the data in the text fields
            txt_try_quiz_question.setText(data1);
            btn_1.setText(answerOptions.get(0));
            btn_2.setText(answerOptions.get(1));
            btn_3.setText(answerOptions.get(2));
            btn_4.setText(answerOptions.get(3));

        }else {
            // All data has been shown
            cursor.close();
            db.close();
            finish();
            Intent intent = new Intent(getApplicationContext(), Score.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("points", points);
            intent.putExtra("total", cursor.getCount());
            startActivity(intent);
        }

        currentIndex++;
    }
    void score(String answer){
        if(answer.equals("Correct")){
            btn_start_next.setVisibility(View.VISIBLE);
            btn_1.setEnabled(false);
            btn_2.setEnabled(false);
            btn_3.setEnabled(false);
            btn_4.setEnabled(false);
            points++;
            Log.d("betlog", question_id + " " + data6);
            db.update_retention(question_id,Integer.parseInt(data6));
        } else if (answer.equals("Wrong")) {
            btn_start_next.setVisibility(View.VISIBLE);
            btn_1.setEnabled(false);
            btn_2.setEnabled(false);
            btn_3.setEnabled(false);
            btn_4.setEnabled(false);
        }



    }

    private void quit_midway() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quit");
        builder.setMessage("Are you sure you want to quit?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (currentIndex == 0){
                    onBackPressed();
                }
                else {
                    cursor.close();
                    db.close();
                    finish();
                    Intent intent = new Intent(getApplicationContext(), Score.class);
                    intent.putExtra("points", points);
                    intent.putExtra("total", cursor.getCount());
                    startActivity(intent);

                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
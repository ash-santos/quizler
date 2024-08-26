package com.example.quizler_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class QuizMenu extends AppCompatActivity {
    TextView quiz_name;
    BottomNavigationView bottom_navigation_view;
    String test_name, test_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_menu);

        //title bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Quiz Menu");  // Set the title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Show back button

        //kinukuha ung data na nasa main_menu
        Intent intent = getIntent();
        test_name = intent.getStringExtra("quiz_name");
        test_id = intent.getStringExtra("quiz_id");
        quiz_name = findViewById(R.id.quiz_name);
        quiz_name.setText(test_name);

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
    public void quiz_list(View v) {
        add_multiple_choice("multiple answer");
        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Question Type")
                .setItems(R.array.quiz_type, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle item click here
                        switch (which) {
                            case 0:
                                add_multiple_choice("multiple answer");
                                break;
                            case 1:
                                add_identification("identification");
                                break;
                            case 2:
                                add_true_or_false("identification");
                                break;
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

         */
    }
    private void add_multiple_choice(String nestedDialogMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Question");

        // Set the custom layout for the dialog
        View dialogView = getLayoutInflater().inflate(R.layout.add_multiple_question, null);
        builder.setView(dialogView);

        // Find the text boxes in the custom layout
        EditText questionEditText = dialogView.findViewById(R.id.question);
        EditText answerEditText = dialogView.findViewById(R.id.answer);
        EditText wrongAnswer1EditText = dialogView.findViewById(R.id.wrongAnswer1);
        EditText wrongAnswer2EditText = dialogView.findViewById(R.id.wrongAnswer2);
        EditText wrongAnswer3EditText = dialogView.findViewById(R.id.wrongAnswer3);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                // Retrieve the values entered in the text boxes
                String question = questionEditText.getText().toString();
                String answer = answerEditText.getText().toString();
                String wrongAnswer1 = wrongAnswer1EditText.getText().toString();
                String wrongAnswer2 = wrongAnswer2EditText.getText().toString();
                String wrongAnswer3 = wrongAnswer3EditText.getText().toString();

                //salpak sa database
                DatabaseConnection database = new DatabaseConnection(QuizMenu.this);
                database.add_quiz_question(Integer.parseInt(test_id),question,answer,wrongAnswer1,wrongAnswer2,wrongAnswer3,1);
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void add_identification(String nestedDialogMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Question");

        // Set the custom layout for the dialog
        View dialogView = getLayoutInflater().inflate(R.layout.add_question_identification, null);
        builder.setView(dialogView);

        // Find the text boxes in the custom layout
        EditText questionEditText = dialogView.findViewById(R.id.questionEditText);
        EditText answerEditText = dialogView.findViewById(R.id.answerEditText);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Retrieve the values entered in the text boxes
                String question = questionEditText.getText().toString();
                String answer = answerEditText.getText().toString();

                // TODO: Handle the data entered in the text boxes (e.g., save it to a database)

                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void add_true_or_false(String nestedDialogMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Question");

        // Set the custom layout for the dialog
        View dialogView = getLayoutInflater().inflate(R.layout.add_question_true_or_false, null);
        builder.setView(dialogView);

        // Find the text box and radio buttons in the custom layout
        EditText questionEditText = dialogView.findViewById(R.id.questionEditText);
        RadioButton trueRadioButton = dialogView.findViewById(R.id.trueRadioButton);
        RadioButton falseRadioButton = dialogView.findViewById(R.id.falseRadioButton);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Retrieve the values entered in the text box and selected radio button
                String question = questionEditText.getText().toString();
                boolean isTrue = trueRadioButton.isChecked();

                // TODO: Handle the data entered (e.g., save it to a database)

                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void delete_quiz(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to delete this quiz?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DatabaseConnection db = new DatabaseConnection(QuizMenu.this);
                db.delete_quiz(test_id);
                dialog.dismiss();

                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);
                finishAffinity();
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
    public void rename_quiz(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rename Quiz");

        // Set the custom layout for the dialog
        View dialogView = getLayoutInflater().inflate(R.layout.rename_quiz, null);
        builder.setView(dialogView);

        // Find the text boxes in the custom layout
        EditText questionEditText = dialogView.findViewById(R.id.rename_quiz);
        questionEditText.setText(quiz_name.getText());
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DatabaseConnection db = new DatabaseConnection(QuizMenu.this);
                db.update_quiz_name(questionEditText.getText().toString(),test_id);
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void flashcard(View v){
        Intent intent = new Intent(getApplicationContext(), Flashcard.class);
        intent.putExtra("quiz_id", Integer.parseInt(test_id));
        startActivity(intent);

    }
    public void try_quiz(View v){
        Intent intent = new Intent(getApplicationContext(), TryQuiz.class);
        intent.putExtra("quiz_id", Integer.parseInt(test_id));
        startActivity(intent);
    }
    public void question_list(View v){
        Intent intent = new Intent(getApplicationContext(), QuestionList.class);
        intent.putExtra("test_id", test_id);
        startActivity(intent);

    }

}


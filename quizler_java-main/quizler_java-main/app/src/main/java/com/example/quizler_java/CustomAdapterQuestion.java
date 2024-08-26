package com.example.quizler_java;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterQuestion extends RecyclerView.Adapter<CustomAdapterQuestion.MyViewHolder> {
    private Context context;
    private ArrayList<String> quizIdList;
    private ArrayList<String> quizNameList;
    private ArrayList<String> quizNameId;
    private String test_name_id;
    public CustomAdapterQuestion(Context context, ArrayList<String> quizIdList, ArrayList<String> quizNameList, ArrayList<String> quizNameId) {
        this.context = context;
        this.quizIdList = quizIdList;
        this.quizNameList = quizNameList;
        this.quizNameId = quizNameId;
    }

    @NonNull
    @Override
    public CustomAdapterQuestion.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.data_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterQuestion.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String quizId = quizIdList.get(position);
        String quizName = quizNameList.get(position);

        holder.quizId.setText(quizId);
        holder.quizName.setText(quizName);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // dito ilalagay ung edit ng questions and delete
                Intent intent = new Intent(context, EditQuestion.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                test_name_id = quizNameId.get(0);
                intent.putExtra("quiz_id", String.valueOf(quizId));
                intent.putExtra("test_name_id", String.valueOf(test_name_id));
                context.startActivity(intent);
                ((Activity) context).finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        return quizIdList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView quizId;
        TextView quizName;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            quizId = itemView.findViewById(R.id.data_list_question_id);
            quizName = itemView.findViewById(R.id.data_list_question);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

}

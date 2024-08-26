package com.example.quizler_java;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList quiz_id, quiz_name;
    int position;
    CustomAdapter(Context context,ArrayList quiz_id, ArrayList quiz_name){
    this.context = context;
    this.quiz_id = quiz_id;
    this.quiz_name = quiz_name;
    }


    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.data_list,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.quiz_id.setText(String.valueOf(quiz_id.get(position)));
        holder.quiz_name.setText(String.valueOf(quiz_name.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, QuizMenu.class);
                intent.putExtra("quiz_id", String.valueOf(quiz_id.get(position)));
                intent.putExtra("quiz_name", String.valueOf(quiz_name.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quiz_id.size()  ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView quiz_id, quiz_name;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            quiz_id = itemView.findViewById(R.id.data_list_question_id);
            quiz_name = itemView.findViewById(R.id.data_list_question);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}

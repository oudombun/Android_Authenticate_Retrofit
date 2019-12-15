package com.example.authenticate_retrofit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authenticate_retrofit.R;
import com.example.authenticate_retrofit.model.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    List<Student> mList;

    public StudentAdapter(List<Student> mList, OnStudentClickListener listener) {
        this.mList = mList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student,parent,false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = mList.get(position);
        holder.name.setText(student.getName_en());
        holder.sex.setText(student.getSex());
        holder.dob.setText(student.getDob());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder{
//        ImageView imageView;
        TextView name,dob,sex;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
//            imageView= itemView.findViewById(R.id.img);
            name= itemView.findViewById(R.id.txt_name);
            dob= itemView.findViewById(R.id.txt_dob);
            sex= itemView.findViewById(R.id.txt_sex);
            itemView.setOnClickListener(v->{
                listener.onStudentClick(getAdapterPosition());
            });
        }
    }

    private OnStudentClickListener listener;
    public interface OnStudentClickListener{
        void onStudentClick(int position);
    }
}

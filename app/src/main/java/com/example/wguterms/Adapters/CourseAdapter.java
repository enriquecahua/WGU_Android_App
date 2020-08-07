package com.example.wguterms.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguterms.Entities.CourseEntity;
import com.example.wguterms.R;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {


    //itializing this as an arraylist allows avoiding null checks in the 3 methods below
    private List<CourseEntity> courses = new ArrayList<>();


    private OnItemClickListener listener;

    //
    // Three methods that have to be overridden in recyclerview adapter
    //
    //

    //create and return a viewHolder
    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);
        return new CourseHolder(itemView);
    }


    //take data from single term java objects into view of termholder
    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        CourseEntity currentCourse = courses.get(position);
        holder.textViewTitle.setText(currentCourse.getCourse_title());
        holder.textViewStartDate.setText(currentCourse.getCourse_start_date());
        holder.textViewEndDate.setText(currentCourse.getCourse_end_date());
        holder.textViewStatus.setText(currentCourse.getCourse_status());
    }

    //return how many items to display in recyclerview
    @Override
    public int getItemCount() {return courses.size(); }

    public void setCourses(List<CourseEntity> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    class CourseHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewStartDate;
        private TextView textViewEndDate;
        private TextView textViewStatus;

        public CourseHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.course_text_view_title);
            textViewStartDate = itemView.findViewById(R.id.course_text_view_start);
            textViewEndDate = itemView.findViewById(R.id.course_text_view_end);
            textViewStatus = itemView.findViewById(R.id.course_text_view_status);

            itemView.setOnClickListener(new View.OnClickListener() {

                //this is where you call listener, and pass term to it
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(courses.get(position));
                    }
                    //steps
                    // click on itemView, which is the card itself
                    // get adapter position of the click
                    // then call listenener.onItemClick
                    // the listener is later implemented in main
                    // then pass terms.get(position)
                }
            });


        }
    }

    public CourseEntity getCourseAt(int position) {
        return courses.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(CourseEntity term);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}

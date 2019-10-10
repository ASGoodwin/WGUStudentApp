package com.example.wgustudentapp.View.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wgustudentapp.Model.Entities.Course;
import com.example.wgustudentapp.R;

import java.util.ArrayList;
import java.util.List;

public class AllTermCoursesAdapter extends RecyclerView.Adapter<AllTermCoursesAdapter.CourseHolder> {

    private List<Course> alCourses = new ArrayList<>();
    private static int termId; //to hold term id to sort alCourses in onBindViewHolder
    private static int itemCount;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_name_item, parent, false);
        return new CourseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {

                Course currentCourse = alCourses.get(position);
                holder.tvCourseName.setText(currentCourse.getTitle());
    }

    @Override
    public int getItemCount() {
        itemCount = alCourses.size();
        return alCourses.size();
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public void setCourse(List<Course> courses){
        this.alCourses = courses;
        notifyDataSetChanged();
    }

    public static int getTermId() { return termId; }

    public List<Course> getAlCourses() { return alCourses; }

    public Course getCourseAt(int position) { return alCourses.get(position); }

    class CourseHolder extends RecyclerView.ViewHolder {

        private TextView tvCourseName;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(alCourses.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Course course);
    }

    public void setOnItemClickListener(OnItemClickListener listener) { this.listener = listener; }
}

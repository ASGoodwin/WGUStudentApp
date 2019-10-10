package com.example.wgustudentapp.View.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wgustudentapp.Model.Entities.Assessment;
import com.example.wgustudentapp.R;

import java.util.ArrayList;
import java.util.List;

public class AllCourseAssessmentsAdapter extends RecyclerView.Adapter<AllCourseAssessmentsAdapter.AssessmentHolder> {

    private List<Assessment> alAssessments = new ArrayList<>();
    private static int courseId; //to hold course id to sort alAssessments in onBindViewHolder
    private static int itemCount;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assessment_name_item, parent, false);
        return new AssessmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentHolder holder, int position) {

        Assessment currentAssessment = alAssessments.get(position);
        holder.tvAssessmentName.setText((currentAssessment.getTitle()));
    }

    @Override
    public int getItemCount() {
        itemCount = alAssessments.size();
        return alAssessments.size();
    }

    public void setCourseId(int courseId){
        this.courseId = courseId;
    }

    public void setAssessment(List<Assessment> assessments){
        this.alAssessments = assessments;
        notifyDataSetChanged();
    }

    public static int getCourseId(){ return courseId; }

    public List<Assessment> getAlAssessments() { return alAssessments; }

    public Assessment getAssessmentAt(int position) { return alAssessments.get(position); }

    class AssessmentHolder extends RecyclerView.ViewHolder{

        private TextView tvAssessmentName;

        public AssessmentHolder(@NonNull View itemView) {
            super(itemView);
            tvAssessmentName = itemView.findViewById(R.id.tvAssessmentName);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener !=null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(alAssessments.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Assessment assessment);
    }

    public void setOnItemClickListener(OnItemClickListener listener) { this.listener = listener; }
}

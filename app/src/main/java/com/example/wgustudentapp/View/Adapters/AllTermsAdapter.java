package com.example.wgustudentapp.View.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wgustudentapp.Model.Entities.Course;
import com.example.wgustudentapp.Model.Entities.Term;
import com.example.wgustudentapp.R;

import java.util.ArrayList;
import java.util.List;

public class AllTermsAdapter extends RecyclerView.Adapter<AllTermsAdapter.TermHolder> {

    private List<Term> alTerms = new ArrayList<>();
    private List<Course> alCourses = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_name_item, parent, false);
        return new TermHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermHolder holder, int position) {
        Term currentTerm = alTerms.get(position);
        holder.tvTermName.setText(currentTerm.getName());
    }

    @Override
    public int getItemCount() {
        return alTerms.size();
    }

    public void setTerm(List<Term> terms) {
        this.alTerms = terms;
        notifyDataSetChanged();
    }

    public List<Term> getAlTerms() {
        return alTerms;
    }

    public void setCourse(List<Course> courses){
        this.alCourses = courses;
        notifyDataSetChanged();
    }

    public List<Course> getAlCourses(){
        return alCourses;
    }

    public Term getTermAt(int position) {
        return alTerms.get(position);
    }

    class TermHolder extends RecyclerView.ViewHolder {

        private TextView tvTermName;

        public TermHolder(@NonNull View itemView) {
            super(itemView);
            tvTermName = itemView.findViewById(R.id.tvTermName);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(alTerms.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Term term);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

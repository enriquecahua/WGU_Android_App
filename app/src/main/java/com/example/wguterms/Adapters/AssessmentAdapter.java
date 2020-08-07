package com.example.wguterms.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguterms.Entities.AssessmentEntity;
import com.example.wguterms.R;

import java.util.ArrayList;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentHolder> {

    //itializing this as an arraylist allows avoiding null checks in the 3 methods below
    private List<AssessmentEntity> assessments = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public AssessmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assessment_item, parent, false);
        return new AssessmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentHolder holder, int position) {
        AssessmentEntity currentAssessment = assessments.get(position);
        holder.textViewTitle.setText(currentAssessment.getAssessment_name());
        holder.textViewDate.setText(currentAssessment.getAssessment_date());
        holder.textViewType.setText(currentAssessment.getAssessment_type());
    }

    @Override
    public int getItemCount() {
        return assessments.size();
    }

    public void setAssessments(List<AssessmentEntity> assessments) {
        this.assessments = assessments;
        notifyDataSetChanged();
    }

    class AssessmentHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDate;
        private TextView textViewType;

        public AssessmentHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.assessment_text_view_title);
            textViewDate = itemView.findViewById(R.id.assessment_text_view_date);
            textViewType = itemView.findViewById(R.id.assessment_text_view_type);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(assessments.get(position));
                    }
                }
            });
        }
    }


    public AssessmentEntity getAssessmentAt(int position) {
        return assessments.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(AssessmentEntity assessment);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}

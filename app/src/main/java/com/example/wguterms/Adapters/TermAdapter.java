package com.example.wguterms.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguterms.Entities.TermEntity;
import com.example.wguterms.R;

import java.util.ArrayList;
import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermHolder> {

    //itializing this as an arraylist allows avoiding null checks in the 3 methods below
    private List<TermEntity> terms = new ArrayList<>();


//    listener memeber variable

    private OnItemClickListener listener;


    //
    // Three methods that have to be overridden in recyclerview adapter
    //
    //
    //create and return a viewHolder
    @NonNull
    @Override
    public TermHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_item, parent, false);
        return new TermHolder(itemView);
    }

    //take data from single term java objects into view of termholder
    @Override
    public void onBindViewHolder(@NonNull TermHolder holder, int position) {
        TermEntity currentTerm = terms.get(position);
        holder.textViewTitle.setText(currentTerm.getTerm_title());
        holder.textViewStartDate.setText(currentTerm.getTerm_start_date());
        holder.textViewEndDate.setText(currentTerm.getTerm_end_date());
    }

    //return how many items to display in recyclerview
    @Override
    public int getItemCount() {
        return terms.size();
    }

    public void setTerms(List<TermEntity> terms) {
        this.terms = terms;
        notifyDataSetChanged();

    }

    class TermHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewStartDate;
        private TextView textViewEndDate;


        public TermHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewStartDate = itemView.findViewById(R.id.text_view_start);
            textViewEndDate = itemView.findViewById(R.id.text_view_end);

            itemView.setOnClickListener(new View.OnClickListener() {

                //this is where you call listener, and pass term to it
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(terms.get(position));
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

    //get click event to main activity
    //this interface requires that when it is implemented,
    // it implements the onItemClick method with a Term as a parameter

    public interface OnItemClickListener {
        void onItemClick(TermEntity term);
    }

    // to call methods from this adapter, you need a reference to it
    // pass onItemClickListener from our package
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    //now need a place to call onItemClick on the listener.
    // in the listener is where you want to catch the click
    //when you click anywhere on the cardview item
    // in the termholder class, the itemView is the whole card view



    public TermEntity getTermAt(int position) {
        return terms.get(position);
    }



}

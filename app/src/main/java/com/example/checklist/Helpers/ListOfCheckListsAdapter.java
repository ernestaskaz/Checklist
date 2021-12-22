package com.example.checklist.Helpers;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.checklist.Entities.CheckList;
import com.example.checklist.R;
import java.util.ArrayList;
import java.util.List;

public class ListOfCheckListsAdapter extends RecyclerView.Adapter<ListOfCheckListsAdapter.MyViewHolder> {
     private List<CheckList> listOfCheckLists = new ArrayList<>();
    // private Context context;
     private OnCheckListListener listener;

//    public ListOfCheckListsAdapter(List<CheckList> listOfCheckLists, Context context) {
//        this.listOfCheckLists = listOfCheckLists;
//        this.context = context;
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checklist_one_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CheckList currentCheckList = listOfCheckLists.get(position);
        holder.progressBar.setMax(currentCheckList.getItemsToDo());
        holder.progressBar.setProgress(currentCheckList.getItemsDone());
        holder.tv_checkListName.setText(currentCheckList.getName());
        holder.tv_checkListNumbers.setText(currentCheckList.getItemsDone() + "/" + currentCheckList.getItemsToDo());
    }


    @Override
    public int getItemCount() {
        return listOfCheckLists.size();
    }

    public void setListOfCheckLists(List<CheckList> listOfCheckLists) {
        this.listOfCheckLists = listOfCheckLists;
        notifyDataSetChanged();
    }

    public CheckList getCurrentCheckList(int position) {
        return listOfCheckLists.get(position);

    }

//    public void setContext(Context context) {
//        this.context = context;
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_checkListName;
        private TextView tv_checkListNumbers;
        private ConstraintLayout parentLayout;
        private ProgressBar progressBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_checkListName = itemView.findViewById(R.id.checklist_name);
            tv_checkListNumbers = itemView.findViewById(R.id.checklist_numbers);
            parentLayout = itemView.findViewById(R.id.checklist_one_list_layout);
            progressBar = itemView.findViewById(R.id.progressBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onCheckListClick(listOfCheckLists.get(position));
                    }
                }
            });
        }
    }
    public interface OnCheckListListener {
        void onCheckListClick(CheckList checkList);
    }

    public void setOnItemClickListener(OnCheckListListener listener) {
        this.listener = listener;

    }
}

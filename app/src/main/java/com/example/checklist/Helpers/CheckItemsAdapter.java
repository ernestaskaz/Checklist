package com.example.checklist.Helpers;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.checklist.Entities.CheckItem;
import com.example.checklist.R;


public class CheckItemsAdapter extends ListAdapter<CheckItem, CheckItemsAdapter.ItemViewHolder> {
    //private Context context;
    private CheckBoxListener checkBoxListener;

    public CheckItemsAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<CheckItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<CheckItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull CheckItem oldItem, @NonNull CheckItem newItem) {
            return oldItem.getId() == newItem.getId();

        }

        @Override
        public boolean areContentsTheSame(@NonNull CheckItem oldItem, @NonNull CheckItem newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getDone().equals(newItem.getDone()) &&
                    oldItem.getComment().equals(newItem.getComment()) &&
                    oldItem.getChecklistId() == newItem.getChecklistId();

        }
    };


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkitem_one_item, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        CheckItem currentCheckItem = getItem(holder.getAdapterPosition());
        holder.tv_checkitem.setText(currentCheckItem.getName());
        holder.checkitem_checkbox.setOnCheckedChangeListener(null);
        Boolean isItemDone = getItem(holder.getAdapterPosition()).getDone();

        if (isItemDone == true) {
            holder.checkitem_checkbox.setChecked(true);
            holder.tv_checkitem.setPaintFlags(holder.tv_checkitem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_checkitem.setTextColor(Color.parseColor("#747474"));
            } else {
            holder.checkitem_checkbox.setChecked(false);
            holder.tv_checkitem.setPaintFlags(0);
            holder.tv_checkitem.setTextColor(Color.parseColor("#000000"));

        }

        holder.checkitem_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxListener != null && holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                    checkBoxListener.onCheckBoxClick(getItem(holder.getAdapterPosition()));
                }

                if (isChecked) {
                    holder.tv_checkitem.setPaintFlags(holder.tv_checkitem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.tv_checkitem.setTextColor(Color.parseColor("#747474"));
                    Toast.makeText(buttonView.getContext(), "Item Done! Good Job", Toast.LENGTH_SHORT).show();
                } else {
                    holder.tv_checkitem.setPaintFlags(0);
                    holder.tv_checkitem.setTextColor(Color.parseColor("#000000"));

                }
            }
        });

    }

    public CheckItem getCurrentCheckItem(int position) {
        return getItem(position);
    }

//    public void setContext(Context context) {
//        this.context = context;
//    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_checkitem;
        CheckBox checkitem_checkbox;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_checkitem = itemView.findViewById(R.id.tv_checkitem);
            checkitem_checkbox = itemView.findViewById(R.id.checkitem_checkbox);
        }
    }
    public interface CheckBoxListener {
        void onCheckBoxClick(CheckItem checkItem);

    }

    public void setOnCheckBoxListener(CheckBoxListener checkBoxListener) {
        this.checkBoxListener = checkBoxListener;

    }
}

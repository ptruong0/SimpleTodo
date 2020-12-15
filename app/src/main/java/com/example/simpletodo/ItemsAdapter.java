package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Responsible for displaying data from the model into a row in the recycler view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    // interface created in adapter, but implemented in main activity
    public interface OnLongClickListener {
        // declare a method signature, but do not define it
        // main activity will end up overriding this method
        void onItemLongClicked(int position);
        // for item removing
    }

    public interface OnClickListener {
        // for item editing
        void onItemClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;
    OnClickListener clickListener;

    // constructor
    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener, OnClickListener clickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
    }

    // creates a view out of a list item
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use layout inflater to inflate a view
            // pass in xml file (simple_list_item_1)
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        // wrap it inside a View Holder and return it
        return new ViewHolder(todoView);
    }

    // takes data at a particular position and puts it in the viewholder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Grab the item at the position
        String item = items.get(position);

        // Bind the item into the specified view holder
        holder.bind(item);
    }

    // tells the RV the number of items in the data
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Container to provide easy access to views that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        // Update the view inside of the view holder with this data
        public void bind(String item) {
            tvItem.setText(item);

            // function invoked when adapter is notified of an item click
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // notify MainActivity which position was edited
                    // calls interface function
                    clickListener.onItemClicked(getAdapterPosition());
                }

            });

            // event handler for long pressing list items
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // notify the listener which position was long pressed
                    // pass in the int position to be removed in main activity
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}

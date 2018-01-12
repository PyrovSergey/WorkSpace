package com.example.pyrov.recyclerview;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private int numberItems;
    final private ListItemClickListener listener;

    interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public MyAdapter(int numberItems, ListItemClickListener listener) {
        this.numberItems = numberItems;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.number_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewNumberItems;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewNumberItems = (TextView) itemView.findViewById(R.id.text_view_item_number);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {
            textViewNumberItems.setText(String.valueOf(listIndex));
        }

        @Override
        public void onClick(View view) {
            listener.onListItemClick(getAdapterPosition());
        }
    }
}

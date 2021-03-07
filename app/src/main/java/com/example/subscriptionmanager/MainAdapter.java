package com.example.subscriptionmanager;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    //Initialize variable
    private List<com.example.subscriptionmanager.MainData> dataList;
    private Activity context;
    private RoomDB database;

    //Create constructor
    public MainAdapter(Activity context, List<com.example.subscriptionmanager.MainData> dataList) {
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_expandable, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //Initialize main data

        com.example.subscriptionmanager.MainData data = dataList.get(position);
        //Initialize database
        database = RoomDB.getInstance(context);

        TextView textView;
        TextView priceView;
        TextView frequencyView;

        //Set text on text view
        //error
        holder.textView.setText(data.getText());
        holder.priceView.setText(Double.toString(data.getPrice()));
        holder.frequencyView.setText(Integer.toString(data.getFrequency()));

        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize main data
                com.example.subscriptionmanager.MainData d = dataList.get(holder.getAdapterPosition());
                //Get id
                final int sID = d.getID();
                //Get text/other info
                String sText = d.getText();
                double sPrice = d.getPrice();
                int sFrequency = d.getFrequency();

                //Create dialog
                final Dialog dialog = new Dialog(context);
                //Set content view
                dialog.setContentView(R.layout.activity_add_subscription);
                //Initialize width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                //Initialize height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //Set layout
                dialog.getWindow().setLayout(width, height);
                //Show dialog
                dialog.show();

                //Initialize and assign variable
                final EditText editText = dialog.findViewById(R.id.subscriptionName);
                final EditText editPrice = dialog.findViewById(R.id.cost);
                final EditText editFrequency = dialog.findViewById(R.id.frequency);

                Button btUpdate = dialog.findViewById(R.id.save_add_subscription);


                //Set text on edit text
                editText.setText(sText);
                editPrice.setText(Double.toString(sPrice));
                editFrequency.setText(Integer.toString(sFrequency));


                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Dismiss dialog
                        dialog.dismiss();

                        //Get updated text from edit text
                        String uText = editText.getText().toString().trim();
                        double uPrice = Double.parseDouble(editPrice.getText().toString().trim());
                        int uFrequency = Integer.parseInt(editFrequency.getText().toString().trim());

                        //Update text in database
                        database.mainDao().update(sID, uText, uPrice, uFrequency);
                        //Notify when data is updated
                        dataList.clear();
                        dataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });



        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initialize main data
                com.example.subscriptionmanager.MainData d = dataList.get(holder.getAdapterPosition());
                //Delete text from database
                database.mainDao().delete(d);
                //Notify when data is deleted
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize variable
        TextView textView;
        TextView priceView;
        TextView frequencyView;

        ImageView btEdit, btDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            textView = itemView.findViewById(R.id.text_view);
            priceView = itemView.findViewById(R.id.price_view);
            frequencyView = itemView.findViewById(R.id.frequency_view);

            btEdit = itemView.findViewById(R.id.bt_edit);
            btDelete = itemView.findViewById(R.id.bt_delete);
        }
    }
}
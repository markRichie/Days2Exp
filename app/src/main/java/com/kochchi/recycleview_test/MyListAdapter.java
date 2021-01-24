package com.kochchi.recycleview_test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kochchi.recycleview_test.eventdb.model.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{


    private List<Event> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnNoteItemClick onNoteItemClick;

    // RecyclerView recyclerView;
    public MyListAdapter(List<Event> list, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.onNoteItemClick = (OnNoteItemClick) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Date expD;
        expD = list.get(position).getExpDate();
        Log.e("bind", "onBindViewHolder: "+ list.get(position));
        holder.textViewName.setText(list.get(position).getName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String dateTime = dateFormat.format(expD);
            holder.textViewExpDate.setText(dateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //setting remainig days
        Date c = Calendar.getInstance().getTime();
        /*SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String todayFormattedDate = df.format(c);
        String expFormattedDate = df.format(expD);*/

        long diff = expD.getTime() - c.getTime();
        float dayCount =  diff / (24 * 60 * 60 * 1000);
        long n_day = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+ 1;
        if(n_day < 0){
           // n_day = 0;
            holder.textViewNoOfDays.setText("Expired");
            holder.textViewNoOfDays.setTextSize(18);
            //holder.textViewNoOfDays.setWidth(30);
        }
        else {
            holder.textViewNoOfDays.setText(Long.toString(n_day));
            holder.textViewDays_more.setText("Days more");
        }
        //holder.textViewNoOfDays.setText();
        //holder.textViewContent.setText(list.get(position).getContent());

        int numP = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        Log.e("bind", "pggress days: "+ numP);
        //holder.progressBarDays.setIndeterminate(true);
        //holder.progressBarDays.setMin(0);
        //holder.progressBarDays.setProgress(numP);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //TextView textViewContent;
        TextView textViewName, textViewExpDate, textViewNoOfDays, textViewDays_more;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            //textViewContent = itemView.findViewById(R.id.item_text);
            textViewName = itemView.findViewById(R.id.tv_name);
            textViewExpDate = itemView.findViewById(R.id.tv_date);
            textViewNoOfDays = itemView.findViewById(R.id.tv_days);
            textViewDays_more = itemView.findViewById(R.id.D_more);

        }

        @Override
        public void onClick(View view) {
            onNoteItemClick.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteItemClick{
        void onNoteClick(int pos);
    }
}

package com.cardview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.Database.Models.normalizeInfo;
import com.customColor.customcolor;
import com.yalantis.contextmenu.R;
import com.yalantis.contextmenu.sample.NoteActivity;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by choisunguk on 2015-11-18.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>  {

    private static int REQUEST_REMOVE = 1;
    private Context context;
    private List<normalizeInfo> items;
    private int item_layout;
    private NoteActivity noteActivity;

    public RecyclerAdapter(Context ctx, List<normalizeInfo> item, int layout)
    {
        context = ctx;
        items = item;
        item_layout = layout;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.customcardview,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter.ViewHolder holder, int position) {
        final normalizeInfo item = items.get(position);

        holder.roastername.setText(item.roastername);
        holder.roasterdate.setText(item.roasterdate);
        holder.roasterweight.setText(Integer.toString(item.roasterweight) + "g");
        holder.roatserpretemp.setText(Integer.toString(item.startTemp) + " ℃");
        holder.roasterfirstcracktime.setText(item.firstTime);
        holder.roasterfirstcracktemp.setText(Integer.toString(item.firsttemp) + " ℃");
        holder.roastersecondcracktime.setText(item.secondTime);
        holder.roastersecondcracktemp.setText(Integer.toString(item.secondtemp) + " ℃");

        holder.cardview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent I = new Intent(view.getContext(), LongClickactivity.class);
                I.putExtra("id", item._id);
                I.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                view.getContext().startActivity(I);
                items.clear();
                return false;

            }
        });

    }

    private void updatelist(View view)
    {

    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView roastername;
        private TextView roasterdate;
        private TextView roasterweight;
        private TextView roatserpretemp;
        private TextView roasterfirstcracktime;
        private TextView roasterfirstcracktemp;
        private TextView roastersecondcracktime;
        private TextView roastersecondcracktemp;

        private CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);

            cardview = (CardView)itemView.findViewById(R.id.cardview);

            //cardview.setBackgroundColor(Color.rgb(253,231,85));
            cardview.setBackgroundColor(customcolor.cardcontent);

            roastername = (TextView)itemView.findViewById(R.id.cardname);
            roasterdate = (TextView)itemView.findViewById(R.id.carddate);
            roasterweight = (TextView)itemView.findViewById(R.id.cardweight);
            roatserpretemp = (TextView)itemView.findViewById(R.id.cardpretemp);
            roasterfirstcracktime = (TextView)itemView.findViewById(R.id.cardfirstcracktime);
            roasterfirstcracktemp = (TextView)itemView.findViewById(R.id.cardfirstcracktemp);
            roastersecondcracktime = (TextView)itemView.findViewById(R.id.cardsecondcracktime);
            roastersecondcracktemp = (TextView)itemView.findViewById(R.id.cardsecondcracktemp);
        }
    }
}

package com.example.myapplication.dmstask.adpter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.myapplication.dmstask.R;
import com.example.myapplication.dmstask.data.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yara on 14-Jan-18.
 */

public class DataAdapter extends RecyclerView.Adapter<dataVH> {
    Context context;
    List<Response> list;


    public DataAdapter(Context context, List<Response> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public dataVH onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.rc_row, parent, false);
        return new dataVH(view);
    }

    @Override
    public void onBindViewHolder(dataVH holder, final int position) {
        final Response response = list.get(position);
        holder.name.setText(response.getName());
        holder.description.setText(response.getDescription());
        holder.user.setText(response.getOwner().getLogin());

        //check fork
        List<Boolean> forkList = new ArrayList<>();
        forkList.add(response.getFork());
        for (int i = 0; i < forkList.size(); i++) {
            if (forkList.get(i) == false) {
                Log.i("data", "Fork is false");
                holder.itemView.setBackgroundColor(Color.GREEN);
            } else {
                Log.i("data", "Fork is true");
                holder.itemView.setBackgroundColor(Color.WHITE);
            }
        }

        //add dailog
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.radiobutton_dialog);

                RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);
                RadioButton rb1=new RadioButton(context);
                rg.addView(rb1);
                rb1.setText("go to repository");
                rb1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //open repository link
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getHtmlUrl()));
                        context.startActivity(browserIntent);
                    }
                });
                RadioButton rb2=new RadioButton(context);
                rg.addView(rb2);
                rb2.setText("go to owner");
                rb2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getOwner().getHtmlUrl()));
                        context.startActivity(browserIntent);

                    }
                });
                dialog.show();

                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}

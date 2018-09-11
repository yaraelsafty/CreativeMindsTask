package com.example.myapplication.dmstask.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.dmstask.R;

/**
 * Created by Yara on 09-Sep-18.
 */

public class dataVH extends RecyclerView.ViewHolder  {
    TextView name,description,user;
    public dataVH(View itemView) {
        super(itemView);

        name=itemView.findViewById(R.id.tv_name);
        description=itemView.findViewById(R.id.tv_description);
        user=itemView.findViewById(R.id.tv_user);

    }
}

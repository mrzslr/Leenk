package com.salari.mohammadreza.mobile.leenk.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.salari.mohammadreza.mobile.leenk.R;

/**
 * Created by MohammadReza on 15/01/2016.
 */
public class View_Holder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView title;
    ImageView imageView;

    View_Holder(View itemView) {
        super(itemView);
        cv = (CardView) itemView.findViewById(R.id.card_url);
        title = (TextView) itemView.findViewById(R.id.txt_title);
        imageView = (ImageView) itemView.findViewById(R.id.image_url);

    }
}
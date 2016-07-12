package com.salari.mohammadreza.mobile.leenk.Adapter;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.salari.mohammadreza.mobile.leenk.Model.URL;
import com.salari.mohammadreza.mobile.leenk.R;

import java.util.Collections;
import java.util.List;

import hu.aut.utillib.circular.animation.CircularAnimationUtils;


public class UrlAdapter extends RecyclerView.Adapter<View_Holder> {

    List<URL> list = Collections.emptyList();
    Context context;
    String letter;
    ColorGenerator generator = ColorGenerator.MATERIAL;
    private int lastPosition = -1;
    // private Realm realm;

    public UrlAdapter(List<URL> list, Context context) {
        this.list = list;
        this.context = context;
        // realm = Realm.getDefaultInstance();
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_url, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {

        letter = String.valueOf(list.get(position).getTitle().charAt(0));

        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.title.setText(list.get(position).getTitle());
        final int rColor = generator.getRandomColor();
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/ubuntu_reg.ttf");
        //   final TextDrawable drawable = TextDrawable.builder() .buildRound(letter, rColor);
        final TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .useFont(tf)
                .width(60)  // width in px
                .height(60) // height in px
                .endConfig()
                .buildRect(letter, rColor);


        holder.imageView.setImageDrawable(drawable);
        //  setAnimation(holder.cv, position);
        //  reveal(holder.cv);
        //animate(holder);
        animate(holder.cv, position);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                RealmResults<URL> results = realm.where(URL.class).findAll();
                realm.beginTransaction();
                results.remove(position);
                // results.removeLast();
                realm.commitTransaction();
                */
/*
                Intent i = new Intent(context, ActivityDetails.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("title", list.get(position).getTitle());
                i.putExtra("short_url", list.get(position).getShortUrl());
                i.putExtra("long_url", list.get(position).getLongUrl());
                context.startActivity(i);
                */

                final Dialog dc = new Dialog(holder.cv.getContext());
                final View dialogView = View.inflate(context, R.layout.dialog_url, null);
                dc.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dc.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                //  dc.setTitle("title");
                dc.setContentView(dialogView);
                LinearLayout topPanel = (LinearLayout) dc.findViewById(R.id.topPanel);
                final LinearLayout parentPanel = (LinearLayout) dialogView.findViewById(R.id.items_reveal);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    topPanel.setBackgroundColor(rColor);
                }

                TextView txtDTitle = (TextView) dc.findViewById(R.id.txtDTitle);
                final TextView txtDSURL = (TextView) dc.findViewById(R.id.txtDSU);
                final TextView txtDLURL = (TextView) dc.findViewById(R.id.txtDLU);
                txtDTitle.setText(list.get(position).getTitle());
                txtDSURL.setText(list.get(position).getShortUrl());
                txtDLURL.setText(list.get(position).getLongUrl());
                ImageView btnCopyDSU = (ImageView) dc.findViewById(R.id.btnCopyShortUrl);
                ImageView btnCopyDLU = (ImageView) dc.findViewById(R.id.btnCopyLongUrl);
                Button btnOk = (Button) dc.findViewById(R.id.btnOk);

                btnOk.setBackgroundColor(rColor);

                dc.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {


                        // int cx = (dialogView.getLeft() + dialogView.getRight()) / 2;
                        // int cy = (dialogView.getTop() + dialogView.getBottom()) / 2;
                        int cx = dialogView.getRight();
                        int cy = (dialogView.getLeft() + dialogView.getRight());

                        int dx = Math.max(cx, dialogView.getWidth() - cx);
                        int dy = Math.max(cy, dialogView.getHeight() - cy);
                        float finalRadius = CircularAnimationUtils.hypo(dx, dy);


                        ObjectAnimator animator = CircularAnimationUtils.createCircularReveal(parentPanel, cx, cy, 0, finalRadius);
                        animator.setInterpolator(new AccelerateDecelerateInterpolator());
                        animator.setDuration(500);
                        animator.start();
                    }
                });


                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dc.dismiss();
                    }
                });
                btnCopyDSU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            ClipboardManager ClipMan = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipMan.setText(txtDSURL.getText());
                            Toast.makeText(context, "Copied",
                                    Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                btnCopyDLU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            ClipboardManager ClipMan = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipMan.setText(txtDLURL.getText());
                            Toast.makeText(context, "Copied",
                                    Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                dc.show();
                //  reveal(parentPanel);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position

    public void insert(int position, URL data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(URL data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    private void animate(View view, final int pos) {
        view.animate().cancel();
        view.setTranslationY(100);
        view.setAlpha(0);
        view.animate().alpha(1.0f).translationY(0).setDuration(300).setStartDelay(pos * 100);
    }


}

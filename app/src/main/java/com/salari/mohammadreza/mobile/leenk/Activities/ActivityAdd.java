package com.salari.mohammadreza.mobile.leenk.Activities;


import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;

import android.text.TextWatcher;

import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.github.jorgecastilloprz.FABProgressCircle;
import com.github.jorgecastilloprz.listeners.FABProgressListener;
import com.pddstudio.urlshortener.URLShortener;
import com.salari.mohammadreza.mobile.leenk.Model.URL;
import com.salari.mohammadreza.mobile.leenk.R;
import com.salari.mohammadreza.mobile.leenk.Utils.DatabaseHelper;


import java.util.ArrayList;

import hu.aut.utillib.circular.animation.CircularAnimationUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by MohammadReza on 16/01/2016.
 */
public class ActivityAdd extends AppCompatActivity implements FABProgressListener {
    private FABProgressCircle fabProgressCircle;
    LinearLayout mRevealView;
    boolean hidden = true;
    TextView txtShortUrl;
    EditText edtLongUrl;
    EditText edtTitle;
    boolean done = false;
    //  private Realm realm;
    ArrayList<URL> urlitems;
    Toolbar toolbar;
    TextInputLayout title_layout;
    TextInputLayout long_url_layout;
    private final int SPLASH_DISPLAY_LENGTH = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);
        toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        toolbar.setTitle("ADD NEW URL");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        fabProgressCircle = (FABProgressCircle) findViewById(R.id.fabProgressCircle);
        txtShortUrl = (TextView) findViewById(R.id.txtShortUrl);
        edtLongUrl = (EditText) findViewById(R.id.edtLongUrl);
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        mRevealView = (LinearLayout) findViewById(R.id.reveal_items);

        title_layout = (TextInputLayout) findViewById(R.id.TitleLayout);
        long_url_layout = (TextInputLayout) findViewById(R.id.LongUrlLayout);
        mRevealView.setVisibility(View.INVISIBLE);
        // realm = Realm.getDefaultInstance();
        urlitems = new ArrayList<URL>();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityAdd.this.finish();
            }
        });
        edtTitle.addTextChangedListener(new MyTextWatcher(edtTitle));
        edtLongUrl.addTextChangedListener(new MyTextWatcher(edtLongUrl));
        fabProgressCircle.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {
                                                     // fabProgressCircle.show();

                                                     if (edtTitle.getText().toString().trim().isEmpty()) {
                                                         edtTitle.setError("Please input Title");
                                                         requestFocus(edtTitle);
                                                         return;
                                                     }
                                                     if (edtLongUrl.getText().toString().trim().isEmpty()) {
                                                         edtLongUrl.setError("Please input your long URL");
                                                         requestFocus(edtLongUrl);
                                                         return;
                                                     }
                                                     if (!isNetworkAvailable(ActivityAdd.this)) {
                                                         Snackbar snackbar = Snackbar.make(view, "Check Your Internet Connection",
                                                                 Snackbar.LENGTH_LONG);
                                                         View snackBarView = snackbar.getView();

                                                         snackbar.show();
                                                         return;
                                                     }
                                                     String longUrl = edtLongUrl.getText().toString();
                                                     //  reveal(mRevealView);
                                                     if (!longUrl.startsWith("https://") && !longUrl.startsWith("http://")) {
                                                         longUrl = "http://" + longUrl;
                                                     }

                                                     final String finalLongUrl = longUrl;

                                                     URLShortener.shortUrl(longUrl, new URLShortener.LoadingCallback() {
                                                         @Override
                                                         public void startedLoading() {
                                                             fabProgressCircle.show();
                                                             fabProgressCircle.setEnabled(false);
                                                         }


                                                         @Override
                                                         public void finishedLoading(@Nullable String shortUrl) {
                                                             //make sure the string is not null
                                                             fabProgressCircle.beginFinalAnimation();

                                                             // if (shortUrl != null) txtShortUrl.setText(shortUrl) ;
//
                                                             //   else txtShortUrl.setText("Unable to generate Link!");

                                                             if (shortUrl != null) {
                                                                 txtShortUrl.setText(shortUrl);
                                                                 int cx = (mRevealView.getLeft() + mRevealView.getRight());
                                                                 //  int cy = (mRevealView.getTop() + mRevealView.getBottom()) / 2;
                                                                 int cy = mRevealView.getLeft();
                                                                 int dx = Math.max(cx, mRevealView.getWidth() - cx);
                                                                 int dy = Math.max(cy, mRevealView.getHeight() - cy);
                                                                 float finalRadius = CircularAnimationUtils.hypo(dx, dy);

                                                                 ObjectAnimator animator = CircularAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, finalRadius);
                                                                 animator.setInterpolator(new AccelerateDecelerateInterpolator());
                                                                 animator.setDuration(1500);
                                                                 animator.start();
                                                                 done = !done;
                                                                 runOnUiThread(new Runnable() {
                                                                     @Override
                                                                     public void run() {
                                                                         URL new_url = new URL();
                                                                         new_url.setTitle(edtTitle.getText().toString());
                                                                         new_url.setLongUrl(finalLongUrl);
                                                                         new_url.setShortUrl(txtShortUrl.getText().toString());
                                                                         addLeenk(new_url);
                                                                         //  addArticle(edtTitle.getText().toString(), finalLongUrl, txtShortUrl.getText().toString());
                                                                         MainActivity.flag_add = true;
                                                                         new Handler().postDelayed(new Runnable() {
                                                                             @Override
                                                                             public void run() {

                                                                                 finish();

                                                                             }

                                                                         }, SPLASH_DISPLAY_LENGTH);
                                                                     }
                                                                 });

                                                             } else {
                                                                 txtShortUrl.setText("Unable to generate Link!");
                                                             }
                                                             fabProgressCircle.setEnabled(true);
                                                         }
                                                     });

                                                 }
                                             }

        );


    }

    @Override
    public void onFABProgressAnimationEnd() {
        //    Toast.makeText(ActivityAdd.this, "onFABProgressAnimationEnd ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /*
        private void addArticle(String title, String long_url, String short_url) {
            realm.beginTransaction();
            urlitems = new ArrayList<>();
            URL mUrl = realm.createObject(URL.class);
            long time = System.currentTimeMillis();
            mUrl.setId(time);
            mUrl.setTitle(title);
            mUrl.setLongUrl(long_url);
            mUrl.setShortUrl(short_url);
            urlitems.add(mUrl);
            realm.commitTransaction();

            //   showToast("Added : " + title);
            // Toast.makeText(getApplicationContext(), "added !", Toast.LENGTH_SHORT).show();
        }
    */
// Adding new contact
    public void addLeenk(URL url) {

        DatabaseHelper dbHelper = new DatabaseHelper(ActivityAdd.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(dbHelper.KEY_TITLE, url.getTitle());
        values.put(dbHelper.KEY_LONG_URL, url.getLongUrl());
        values.put(dbHelper.KEY_SHORT_URL, url.getShortUrl());
        // Inserting Row
        db.insert(dbHelper.TABLE_LEENKS, null, values);
        db.close(); // Closing database connection
    }
d
    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private boolean validateTitle() {
        if (edtTitle.getText().toString().trim().isEmpty()) {
            title_layout.setError("Please input Title");
            requestFocus(edtTitle);
            return false;
        } else {
            title_layout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateLongUrl() {
        if (edtLongUrl.getText().toString().trim().isEmpty()) {
            long_url_layout.setError("Please input your long URL");
            requestFocus(edtLongUrl);
            return false;
        } else {
            long_url_layout.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edtTitle:
                    validateTitle();
                    break;
                case R.id.edtLongUrl:
                    validateLongUrl();
                    break;

            }
        }
    }
/*
    public void reveal(final LinearLayout mRevealView) {
        final boolean[] hid = {true};
        int cx = (mRevealView.getLeft() + mRevealView.getRight());
        //  int cy = (mRevealView.getTop() + mRevealView.getBottom()) / 2;
        int cy = mRevealView.getLeft();
        int dx = Math.max(cx, mRevealView.getWidth() - cx);
        int dy = Math.max(cy, mRevealView.getHeight() - cy);
        float finalRadius = (float) Math.hypot(dx, dy);
        int radius = Math.max(mRevealView.getWidth(), mRevealView.getHeight());

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {


            SupportAnimator animator =
                    ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, finalRadius);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(2000);

            SupportAnimator animator_reverse = animator.reverse();

            if (hid[0]) {
                mRevealView.setVisibility(View.VISIBLE);
                animator.start();
                hid[0] = false;
            } else {
                animator_reverse.addListener(new SupportAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationEnd() {
                        mRevealView.setVisibility(View.INVISIBLE);
                        hid[0] = true;

                    }

                    @Override
                    public void onAnimationCancel() {

                    }

                    @Override
                    public void onAnimationRepeat() {

                    }
                });
                animator_reverse.start();

            }
        } else {
            if (hid[0]) {
                Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, finalRadius);
                mRevealView.setVisibility(View.VISIBLE);
                anim.start();
                hid[0] = false;

            } else {
                Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, finalRadius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mRevealView.setVisibility(View.INVISIBLE);
                        hid[0] = true;
                    }
                });
                anim.start();
            }
        }
    }
*/


}






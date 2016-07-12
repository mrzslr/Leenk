package com.salari.mohammadreza.mobile.leenk.Activities;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import com.salari.mohammadreza.mobile.leenk.Adapter.UrlAdapter;
import com.salari.mohammadreza.mobile.leenk.Model.URL;
import com.salari.mohammadreza.mobile.leenk.R;
import com.salari.mohammadreza.mobile.leenk.Utils.DatabaseHelper;
import com.salari.mohammadreza.mobile.leenk.Views.Utils;


import java.util.ArrayList;


import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {
    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    boolean isUserFirstTime;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;
    RecyclerView recycle_scroll;
    //private Realm realm;
    ArrayList<URL> urlitems;
    UrlAdapter adapter;
    AppBarLayout appBarLayout;
    public static boolean flag_add = false;
    TextView txtEmpty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isUserFirstTime = Boolean.valueOf(Utils.readSharedSetting(MainActivity.this, PREF_USER_FIRST_TIME, "true"));

        Intent introIntent = new Intent(MainActivity.this, IntroActivity.class);
        introIntent.putExtra(PREF_USER_FIRST_TIME, isUserFirstTime);

        if (isUserFirstTime) {

            startActivity(introIntent);

        }

        setContentView(R.layout.activity_main);


        //
        toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        recycle_scroll = (RecyclerView) findViewById(R.id.recycle_scroll);
        // realm = Realm.getDefaultInstance();

        urlitems = new ArrayList<URL>();

        // List<URL> data = fill_with_data();
        //addArticle("Hello World");
        //  addArticle("Micheal Jackson");
        txtEmpty = (TextView) findViewById(R.id.txtEmpty);
        adapter = new UrlAdapter(urlitems, getApplication());
        recycle_scroll.setAdapter(adapter);
        recycle_scroll.setLayoutManager(new LinearLayoutManager(this));

        setSupportActionBar(toolbar);

        collapsingToolbar.setTitle(getString(R.string.app_name));
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_reg.ttf");
        collapsingToolbar.setExpandedTitleTypeface(tf);
        collapsingToolbar.setCollapsedTitleTypeface(tf);
        ImageView header = (ImageView) findViewById(R.id.header);
        getAllLeenks();
        if (urlitems.isEmpty()) {
            recycle_scroll.setVisibility(View.GONE);
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            recycle_scroll.setVisibility(View.VISIBLE);
            txtEmpty.setVisibility(View.GONE);
        }



    }


    public void stActivityAdd(View view) {
        startActivity(new Intent(MainActivity.this, ActivityAdd.class));
/*
        final Dialog dc = new Dialog(MainActivity.this);
        dc.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dc.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final View dialogView = View.inflate(MainActivity.this, R.layout.dialog_add, null);
        dc.setContentView(dialogView);
        fabProgressCircle = (FABProgressCircle) dc.findViewById(R.id.fabProgressCircle);
        txtShortUrl = (TextView) dc.findViewById(R.id.txtShortUrl);
        edtLongUrl = (EditText) dc.findViewById(R.id.edtLongUrl);
        edtTitle = (EditText) dc.findViewById(R.id.edtTitle);
        final String longUrl = edtLongUrl.getText().toString();
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


                                                     String longUrl = edtLongUrl.getText().toString();
                                                     //  reveal(mRevealView);
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

                                                     URLShortener.shortUrl(longUrl, new URLShortener.LoadingCallback() {
                                                         @Override
                                                         public void startedLoading() {
                                                             fabProgressCircle.show();
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
                                                                 done = !done;
                                                                 runOnUiThread(new Runnable() {
                                                                     @Override
                                                                     public void run() {
                                                                         addArticle(edtTitle.getText().toString(), edtLongUrl.getText().toString(), txtShortUrl.getText().toString());
                                                                       //  MainActivity.flag_add = true;
                                                                       //  finish();
                                                                     }
                                                                 });

                                                             } else {
                                                                 txtShortUrl.setText("Unable to generate Link!");
                                                             }
                                                         }
                                                     });

                                                 }
                                             }

        );
        dc.show();
           */
    }


    /*
        public List<URL> fill_with_data() {

            data = new ArrayList<>();

            data.add(new URL(1, "Batman vs Superman", R.mipmap.ic_launcher));
            data.add(new URL(2, "X-Men: Apocalypse", R.mipmap.ic_launcher));
            data.add(new URL(3, "Captain America: Civil War", R.mipmap.ic_launcher));
            data.add(new URL(4, "Kung Fu Panda 3", R.mipmap.ic_launcher));
            data.add(new URL(5, "Warcraft", R.mipmap.ic_launcher));
            data.add(new URL(6, "Alice in Wonderland", R.mipmap.ic_launcher));

            return data;
        }
    */
// Getting All Contacts
    public void getAllLeenks() {

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_LEENKS + " ORDER BY " + DatabaseHelper.KEY_ID + " DESC";
        DatabaseHelper dbhelper = new DatabaseHelper(MainActivity.this);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                URL url = new URL();
                url.setId(Integer.parseInt(cursor.getString(0)));
                url.setTitle(cursor.getString(1));
                url.setLongUrl(cursor.getString(2));
                url.setShortUrl(cursor.getString(3));
                // Adding contact to list
                urlitems.add(url);
            } while (cursor.moveToNext());
        }


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /*

    // FIND ALL LEENKS IN REALM

        private void findAllArticle() {


            RealmResults<URL> realmResults = realm.where(URL.class).findAll();
            realmResults.sort("id", Sort.DESCENDING);
            for (int i = 0; i < realmResults.size(); i++) {
                URL url = new URL();
                url.setTitle(realmResults.get(i).getTitle());
                android.util.Log.i("title : ", realmResults.get(i).getTitle());
                url.setLongUrl(realmResults.get(i).getLongUrl());
                url.setShortUrl(realmResults.get(i).getShortUrl());
                urlitems.add(url);
            }
            adapter.notifyDataSetChanged();

        }
    */
    @Override
    protected void onResume() {
        super.onResume();
        if (flag_add) {
            // realm = Realm.getDefaultInstance();
            urlitems.clear();
            //  urlitems = new ArrayList<URL>();

            getAllLeenks();
            if (urlitems.isEmpty()) {
                recycle_scroll.setVisibility(View.GONE);
                txtEmpty.setVisibility(View.VISIBLE);
            } else {
                recycle_scroll.setVisibility(View.VISIBLE);
                txtEmpty.setVisibility(View.GONE);
            }
            adapter.notifyDataSetChanged();
            flag_add = !flag_add;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_who) {

            Uri uri = Uri.parse("http://instagram.com/_u/mohammadreza2012");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

            likeIng.setPackage("com.instagram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/mohammadreza2012")));
            }
            return true;
        }
        if (id == R.id.action_about) {
            startActivity(new Intent(MainActivity.this, ActivityAbout.class));
        }

        return super.onOptionsItemSelected(item);
    }
}

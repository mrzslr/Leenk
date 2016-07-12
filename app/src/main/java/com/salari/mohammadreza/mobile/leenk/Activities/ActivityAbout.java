package com.salari.mohammadreza.mobile.leenk.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.salari.mohammadreza.mobile.leenk.R;

/**
 * Created by MohammadReza on 02/02/2016.
 */
public class ActivityAbout extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        toolbar.setTitle("What is Leenk");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityAbout.this.finish();
            }
        });
    }
}

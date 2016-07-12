package com.salari.mohammadreza.mobile.leenk.Activities;


import com.alirezaafkar.json.requester.Requester;
import com.salari.mohammadreza.mobile.leenk.R;

import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static com.pddstudio.urlshortener.Utils.API_KEY;

/**
 * Created by MohammadReza on 15/01/2016.
 */
public class Application extends android.app.Application {


    @Override
    public void onCreate() {
        super.onCreate();

        //  RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        // Realm.setDefaultConfiguration(config);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ubuntu_reg.ttf")
                .setFontAttrId(R.attr.fontPath).build());

        Map<String, String> header = new HashMap<>();
        header.put("charset", "utf-8");

        Requester.Config config = new Requester.Config(getApplicationContext());
        config.setHeader(header);
        Requester.init(config);


    }
}

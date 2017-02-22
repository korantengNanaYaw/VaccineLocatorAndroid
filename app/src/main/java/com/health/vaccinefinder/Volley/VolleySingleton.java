package com.health.vaccinefinder.Volley;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.health.vaccinefinder.Application.Application;

/**
 * Created by smsgh on 22/02/2017.
 */

public class VolleySingleton {
    private static VolleySingleton sInstance=null;
    private static RequestQueue mRequestQueue;

    private VolleySingleton(){
        mRequestQueue= Volley.newRequestQueue(Application.getAppContext());
    }


    public static VolleySingleton getsInstance(){

        if(sInstance==null){
            sInstance=new VolleySingleton();
        }
        return  sInstance;
    }

    public static RequestQueue getRequestQueue(){

        if (mRequestQueue == null) {
            Cache cache = new DiskBasedCache(Application.getAppContext().getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            // Don't forget to start the volley request queue
            mRequestQueue.start();
        }
        return mRequestQueue;

    }
}

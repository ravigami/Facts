package com.telstra.facts.http;

/**
 * Created by ravi.gami on 3/8/17.
 */

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class HttpManager {
    private static final String TAG = HttpManager.class.getSimpleName();
    public static final String GET_FACTS_API = "https://dl.dropboxusercontent.com/u/746330/facts.json";

    private RequestQueue mRequestQueue;
    private Context mContext;
    private static HttpManager mInstance;

    //////////////////////////////////////////////////////////////////
    private HttpManager(Context context) {
        //Relay on application wide context.
        mContext = context.getApplicationContext();
    }

    /**
     * Create Singleton object for HttpManager class
     *
     * @return HttpManager object value
     */
    public static synchronized HttpManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new HttpManager(context);
        }
        return mInstance;
    }

    /**
     * Request current volley queue to operates on requests.
     *
     * @return Volley request queue object
     */
    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }

        return mRequestQueue;
    }


    /**
     * Adds a generic request along with request tag.
     *
     * @param req
     * @param tag
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * Using tag cancel any specific request execution.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}

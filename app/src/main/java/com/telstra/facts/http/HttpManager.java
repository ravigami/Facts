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
    public static final String TAG = HttpManager.class.getSimpleName();
    public static final String GET_FACTS_API = "https://dl.dropboxusercontent.com/u/746330/facts.json";

    private RequestQueue mRequestQueue;
    private Context mContext;
    private static HttpManager mInstance;

    //////////////////////////////////////////////////////////////////
    private HttpManager(Context context) {
        // To make sure that this is Application Context
        // Otherwise the context will go away when activity is gone
        mContext = context.getApplicationContext();
    }

    /**
     * Create Singleton object for HttpManager class
     *
     * @param context
     * @return
     */
    public static synchronized HttpManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new HttpManager(context);
        } else {
            mInstance.mContext = context.getApplicationContext();
        }
        return mInstance;
    }

    /**
     * Request current volley queue to operates on requests.
     *
     * @return
     */
    public RequestQueue getRequestQueue() {
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
     * Adds a generic request.
     *
     * @param req
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Using tag cancel any specfic request execution.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}

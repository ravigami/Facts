package com.telstra.facts.http;

/**
 * Created by ravi.gami on 3/8/17.
 */

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.telstra.facts.commons.Constants;
import com.telstra.facts.commons.Util;
import com.telstra.facts.model.Error;
import com.telstra.facts.model.FactResponse;

import org.json.JSONObject;

import static com.android.volley.Request.Method;

public class ServiceManager {

    private static final String TAG = ServiceManager.class.getName();
    private static ServiceManager mInstance;

    /**
     * Create Singleton object for ServiceManager class
     *
     * @return
     */
    public static synchronized ServiceManager getInstance() {
        if (mInstance == null) {
            mInstance = new ServiceManager();
        }
        return mInstance;
    }

    /**
     * provided the no network error response.
     *
     * @param managerResponse
     */
    private void provideNoNetworkResponse(final ServiceManagerResponse managerResponse) {
        Error responseError = getErrorFromMessage(Constants.kNetworkErrorMessage);
        if (managerResponse != null)
            managerResponse.onError(null, responseError);
    }

    /**
     * provides the volley error message. It can be any network error, no connection error and timeout error.
     *
     * @param error
     * @param managerResponse
     */
    private void provideVolleyErrorResponse(VolleyError error, final ServiceManagerResponse managerResponse) {
        Log.d(TAG, "Error : " + error.getLocalizedMessage());
        Error responseError = getErrorFromMessage(error.getLocalizedMessage());

        if (managerResponse != null)
            managerResponse.onError(null, responseError);
    }

    /**
     * Prepare custom error object from error description message.
     *
     * @param errorDescription
     * @return
     */
    private Error getErrorFromMessage(String errorDescription) {
        Error error = new Error();
        error.setError(errorDescription);
        error.setStatus(500);
        error.setSuccess(false);
        return error;
    }

    /**
     * Get facts information.
     *
     * @param context
     * @param managerResponseCallback
     */
    public void getFactsInformation(final Context context, final ServiceManagerResponse managerResponseCallback) {
        if (Util.isNetworkAvailable(context)) {
            String requestTag = "get_facts_info";
            String apiURL = HttpManager.GET_FACTS_API;
            String requestBody = null;

            JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Method.GET, apiURL, requestBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Error error = null;
                    FactResponse faceResponse = null;
                    try {
                        if (response != null) {
                            faceResponse = parseFactResponse(response);
                        }
                    } catch (Exception e) {
                        error = getErrorFromMessage(e.getLocalizedMessage());
                    } finally {
                        if (managerResponseCallback != null)
                            managerResponseCallback.onResponse(true, faceResponse, error);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    provideVolleyErrorResponse(error, managerResponseCallback);
                }
            });

            /**
             * Adding request to request queue as we are using Volley,
             * it will take care of creating a background thread for us.
             */
            HttpManager.getInstance(context).addToRequestQueue(jsonObjRequest, requestTag);
        } else {
            provideNoNetworkResponse(managerResponseCallback);
        }
    }

    /**
     * Parses the fact response.
     *
     * @param response
     * @return
     */
    public FactResponse parseFactResponse(JSONObject response) {
        FactResponse factResponse = new FactResponse();
        try {
            factResponse = new Gson().fromJson(response.toString(), FactResponse.class);
        } catch (Exception e) {
            Log.d(TAG, "getFactResponse parsing exception " + e.getLocalizedMessage());
        }
        return factResponse;
    }
}
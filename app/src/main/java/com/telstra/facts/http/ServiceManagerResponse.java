package com.telstra.facts.http;

/**
 * Created by ravi.gami on 3/8/17.
 */

import com.telstra.facts.model.Error;

/**
 * Interface with callback to handle response success
 */
public interface ServiceManagerResponse {
    void onResponse(boolean success, Object response, Error error);
    void onError(Error error);
}

package com.telstra.facts;

import com.telstra.facts.http.HttpManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static junit.framework.Assert.assertEquals;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)

public class ConfigTest {

    /**
     * Test to verify URL value should be there.
     * @throws Exception
     */
    @Test
    public void testURLExists() throws Exception {
        Assert.assertNotNull(HttpManager.GET_FACTS_API);
    }

    /**
     * Test to verify URL is up and running.
     * @throws Exception
     */
    public void testURL() throws Exception {
        String strUrl = HttpManager.GET_FACTS_API;
        try {
            URL url = new URL(strUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.connect();
            assertEquals(HttpURLConnection.HTTP_OK, urlConn.getResponseCode());
        } catch (IOException e) {
            System.err.println("Error creating HTTP connection");
            e.printStackTrace();
            throw e;
        }
    }
}
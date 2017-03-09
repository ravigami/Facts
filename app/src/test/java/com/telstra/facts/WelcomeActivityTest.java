package com.telstra.facts;

import com.telstra.facts.gui.WelcomeActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)

public class WelcomeActivityTest {
    private WelcomeActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(WelcomeActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void shouldHaveCopyRightInformation() throws Exception {
        assertNotNull(activity.findViewById(R.id.copy_right_text_view));
    }
}
package org.dodgson.hello.hello;

import android.app.Activity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Created by omo on 6/14/14.
 */
@Config(emulateSdk=18)
@RunWith(RobolectricTestRunner.class)
public class HelloActivityTest {

    @Test
    public void testHello() {
        HelloActivity activity = Robolectric.buildActivity(HelloActivity.class).create().get();
        assertEquals(activity.getMyTitle(), "Hello, Android Studio!");
    }
}

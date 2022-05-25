package com.example.weatherforecast;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class MainActivityTest extends TestCase {
    @Test
    public void test_getLocation_loadsCoordinates() {
        MainActivity main = Mockito.mock(MainActivity.class);
        Assert.assertNotNull(main.currLat);
        Assert.assertNotNull(main.currLong);
    }

    @Test
    public void test_getLocation_validCoordinates() {
        MainActivity main = Mockito.mock(MainActivity.class);
        Assert.assertTrue(main.currLat < 90);
        Assert.assertTrue(main.currLat > -90);
        Assert.assertTrue(main.currLong < 180);
        Assert.assertTrue(main.currLong > -180);
    }
}
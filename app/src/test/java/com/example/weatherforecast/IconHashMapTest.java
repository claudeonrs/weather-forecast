package com.example.weatherforecast;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class IconHashMapTest extends TestCase {

    @Test
    public void test_getIconHashMap_returnsCorrect() {
        IconHashMap map = new IconHashMap();
        Map result = map.getIconHashMap();
        Assert.assertEquals(result.get("Partly Cloudy (Night)"), "☁");
        Assert.assertEquals(result.get("Partly Cloudy (Day)"), "\uD83C\uDF24");
        Assert.assertEquals(result.get("Cloudy"), "☁");

        Assert.assertEquals(result.get("Light Rain"), "\uD83C\uDF26");
        Assert.assertEquals(result.get("Showers"), "\uD83C\uDF27");
        Assert.assertEquals(result.get("Thundery Showers"), "insert emoji or smth");
    }
}
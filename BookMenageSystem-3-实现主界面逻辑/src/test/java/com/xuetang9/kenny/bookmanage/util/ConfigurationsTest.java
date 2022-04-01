package com.xuetang9.kenny.bookmanage.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigurationsTest {

    @Test
    public void get() {
        System.out.println(Configurations.get("db.driver"));
    }
}
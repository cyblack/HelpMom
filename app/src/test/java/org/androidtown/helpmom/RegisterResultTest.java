package org.androidtown.helpmom;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegisterResultTest {

    private RegisterResult ca = new RegisterResult();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getRoomName() {
        System.out.println(ca.getRoomName());

    }
}
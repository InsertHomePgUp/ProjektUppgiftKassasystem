package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrialTest {

    @Test
    public void addTest() {
        int s = Trial.seven();
        assertEquals(7, s);
    }
}
/kommentar
package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDanielTest {
    @Test
    void isDanielTest() {
        String actual = testDaniel.isDaniel();
        assertEquals("Daniel", actual);
    }
}

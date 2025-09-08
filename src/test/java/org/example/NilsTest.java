package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class NilsTest {

    @Test
    void treIsTre(){
        int tre = NilsClass.treIsTre();
        assertEquals(3, tre);
    }
}

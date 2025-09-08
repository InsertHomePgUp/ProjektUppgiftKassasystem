package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class NilsTest {

    @Test
    void treIsTreTest(){
        int tre = NilsClass.treIsTre();
        assertEquals(3, tre);
    }
}

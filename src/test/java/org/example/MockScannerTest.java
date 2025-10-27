//package org.example;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class MockScannerTest {
//
//    private MockScanner mockScanner;
//
//    @BeforeEach
//    void createScanner() {
//        mockScanner = new MockScanner();
//    }
//
//    @Test
//    void validBarcode() {
//        assertEquals("Beer", mockScanner.scanBarcode("1").getName());
//    }
//
//    @Test
//    void invalidBarcode() { //Barcode not in database
//        assertThrows(IllegalArgumentException.class, () -> mockScanner.scanBarcode("999"));
//    }
//
//    @Test
//    void emptyBarcode() {
//        assertThrows(IllegalArgumentException.class, () -> mockScanner.scanBarcode(""));
//    }
//
//    @Test
//    void nullbBarCode() {
//        assertThrows(IllegalArgumentException.class, () -> mockScanner.scanBarcode(null));
//    }
//
//
//}

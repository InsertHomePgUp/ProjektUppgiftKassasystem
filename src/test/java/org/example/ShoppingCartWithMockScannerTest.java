//package org.example;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ShoppingCartWithMockScannerTest {
//
//    private ShoppingCart shoppingCart;
//    private MockScanner mockScanner;
//
//    @BeforeEach
//    void createObjects() {
//        shoppingCart = new ShoppingCart();
//        mockScanner = new MockScanner();
//    }
//
//    @Test
//    void addItem() {
//        Item beer = mockScanner.scanBarcode("1"); //Valid "barcodes" in mock scanner are strings 1-6
//        shoppingCart.addItem(beer);
//        assertEquals(1, shoppingCart.viewCart().size());
//    }
//
//    @Test
//    void addMultipleItems() {
//        Item beer = mockScanner.scanBarcode("1");
//        Item redBull = mockScanner.scanBarcode("4");
//        shoppingCart.addItem(beer);
//        shoppingCart.addItem(redBull);
//        assertEquals("Beer", shoppingCart.viewCart().get(0).getName());
//        assertEquals("Red Bull", shoppingCart.viewCart().get(1).getName());
//    }
//
//    @Test
//    void addDuplicateItems() {
//        Item beer = mockScanner.scanBarcode("1");
//        shoppingCart.addItem(beer);
//        shoppingCart.addItem(beer);
//        assertEquals(2, shoppingCart.viewCart().size());
//    }
//
//    @Test
//    void removeItemFromCart() {
//        shoppingCart.addItem(mockScanner.scanBarcode("6"));
//        assertEquals(1, shoppingCart.viewCart().size());
//
//        shoppingCart.removeItem(mockScanner.scanBarcode("6"));
//        assertEquals(0, shoppingCart.viewCart().size());
//    }
//
//    @Test
//    void removingItemThatIsNotInCartThrowsException() {
//        Item beer = mockScanner.scanBarcode("1");
//        assertThrows(IllegalArgumentException.class, () -> shoppingCart.removeItem(beer));
//    }
//
//    @Test
//    void nullBarcodeThrowsException() {
//        assertThrows(IllegalArgumentException.class, () -> shoppingCart.addItem(mockScanner.scanBarcode(null)));
//    }
//
//    @Test
//    void emptyBarcodeThrowsException() {
//        assertThrows(IllegalArgumentException.class, () -> shoppingCart.addItem(mockScanner.scanBarcode("")));
//    }
//
//    @Test
//    void invalidBarcodeThrowsException() {
//        assertThrows(IllegalArgumentException.class, () -> shoppingCart.addItem(mockScanner.scanBarcode("999")));
//    }
//}

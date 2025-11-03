package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShoppingCartWithMockitoTest {

    private ShoppingCart shoppingCart;
    private Scanner mockScanner;
    private Item beer;
    private Item cider;


    @BeforeEach
    void createObjects() {
        mockScanner = mock(Scanner.class);
        shoppingCart = new ShoppingCart(mockScanner);
        ItemType alcohol = new ItemType("Alcohol", 15.0, new Money(SEK.instance, 100), 18);
        beer = new Item("Beer", alcohol, new Money(SEK.instance, 2000));
        cider = new Item("Cider", alcohol, new Money(SEK.instance, 2300));
    }

    @Test
    void addItem() {
        //Items skapas i BeforeEach

        //Sätter beteende
        when(mockScanner.scanBarcode("1")).thenReturn(beer);

        //Utför
        shoppingCart.scanAndAdd("1");

        //Kontroll
        assertEquals(1, shoppingCart.viewCart().size());
        assertEquals("Beer", shoppingCart.viewCart().get(0).getName());
        verify(mockScanner, times(1)).scanBarcode("1"); //Kontrollerar att scanBarcode endast anropats 1 gång
    }

    @Test
    void addMultipleItems() {
        when(mockScanner.scanBarcode(anyString())).thenAnswer(invocation -> {
            String code = invocation.getArgument(0);
            switch (code) {
                case "1": return beer;
                case "2": return cider;
                default: throw new IllegalArgumentException("Unknown barcode: " + code);
            }
        });

        shoppingCart.scanAndAdd("1");
        shoppingCart.scanAndAdd("2");

        assertEquals(2, shoppingCart.viewCart().size());
        assertEquals("Beer", shoppingCart.viewCart().get(0).getName());
        assertEquals("Cider", shoppingCart.viewCart().get(1).getName());
        verify(mockScanner, times(2)).scanBarcode(anyString());
    }

    @Test
    void addDuplicateItems() {
        when(mockScanner.scanBarcode("1")).thenReturn(beer);
        shoppingCart.scanAndAdd("1");
        shoppingCart.scanAndAdd("1");

        assertEquals(2, shoppingCart.viewCart().size());
        assertEquals("Beer", shoppingCart.viewCart().get(0).getName());
        assertEquals("Beer", shoppingCart.viewCart().get(1).getName());
        verify(mockScanner, times(2)).scanBarcode("1");
    }

    @Test
    void removeItemFromCart() {
        when(mockScanner.scanBarcode("1")).thenReturn(beer);
        shoppingCart.scanAndAdd("1");

        shoppingCart.scanAndRemove("1");

        assertEquals(0, shoppingCart.viewCart().size());
    }

    @Test
    void removingItemThatIsNotInCartThrowsException() {
        when(mockScanner.scanBarcode("1")).thenReturn(beer);

        assertThrows(IllegalArgumentException.class, () -> shoppingCart.scanAndRemove("1"));

        verify(mockScanner, times(1)).scanBarcode("1");
    }

    @Test
    void nullBarcodeThrowsException() {
        doThrow(new IllegalArgumentException("Barcode cannot be null")).when(mockScanner).scanBarcode(null);

        assertThrows(IllegalArgumentException.class, () -> shoppingCart.scanAndAdd(null));

        verify(mockScanner, times(1)).scanBarcode(null);
    }

    @Test
    void emptyBarcodeThrowsException() {
        when(mockScanner.scanBarcode("")).thenThrow(new IllegalArgumentException("Barcode cannot be empty"));

        assertThrows(IllegalArgumentException.class, () -> shoppingCart.scanAndAdd(""));

        verify(mockScanner, times(1)).scanBarcode("");
    }

    @Test
    void invalidBarcodeThrowsException() {
        doThrow(new IllegalArgumentException("Barcode not in database")).when(mockScanner).scanBarcode("Invalid barcode");

        assertThrows(IllegalArgumentException.class, () -> shoppingCart.scanAndAdd("Invalid barcode"));

        verify(mockScanner, times(1)).scanBarcode("Invalid barcode");
    }

    @Test
    void scannerFailsThenSucceeds() {
        when(mockScanner.scanBarcode("1")).thenThrow(new IllegalStateException("Scanner disconnected")).thenReturn(beer);

        assertThrows(IllegalStateException.class, () -> shoppingCart.scanAndAdd("1"));

        shoppingCart.scanAndAdd("1");

        verify(mockScanner, times(2)).scanBarcode("1");
    }

    @Test
    void verifyNoExtraMockInteractions() {
        when(mockScanner.scanBarcode("1")).thenReturn(beer);
        shoppingCart.scanAndAdd("1");

        verify(mockScanner).scanBarcode("1");
        verifyNoMoreInteractions(mockScanner);
    }
}

package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShoppingCartWithMockitoTest {

    private ShoppingCart shoppingCart;
    private Scanner mockScanner;


    @BeforeEach
    void createObjects() {
        mockScanner = mock(Scanner.class);
        shoppingCart = new ShoppingCart(mockScanner);
    }

    @Test
    void addItem() {
        //Skapar items
        ItemType alcohol = new ItemType("Alcohol", 15.0, new Money(SEK.instance, 100), 18);
        Item beer = new Item("Beer", alcohol, new Money(SEK.instance, 2000));

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
        //Skapar items
        ItemType alcohol = new ItemType("Alcohol", 15.0, new Money(SEK.instance, 100), 18);
        Item beer = new Item("Beer", alcohol, new Money(SEK.instance, 2000));
        Item cider = new Item("Cider", alcohol, new Money(SEK.instance, 2300));

        //Sätter beteende
        when(mockScanner.scanBarcode("1")).thenReturn(beer);
        when(mockScanner.scanBarcode("2")).thenReturn(cider);

        //Utför
        shoppingCart.scanAndAdd("1");
        shoppingCart.scanAndAdd("2");

        //Kontroll
        assertEquals(2, shoppingCart.viewCart().size());
        assertEquals("Beer", shoppingCart.viewCart().get(0).getName());
        assertEquals("Cider", shoppingCart.viewCart().get(1).getName());
        verify(mockScanner, times(2)).scanBarcode(anyString());
    }

    @Test
    void addDuplicateItems() {
        //Skapar items
        ItemType alcohol = new ItemType("Alcohol", 15.0, new Money(SEK.instance, 100), 18);
        Item beer = new Item("Beer", alcohol, new Money(SEK.instance, 2000));

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
        //Skapar items
        ItemType alcohol = new ItemType("Alcohol", 15.0, new Money(SEK.instance, 100), 18);
        Item beer = new Item("Beer", alcohol, new Money(SEK.instance, 2000));

        when(mockScanner.scanBarcode("1")).thenReturn(beer);
        shoppingCart.scanAndAdd("1");

        shoppingCart.scanAndRemove("1");

        assertEquals(0, shoppingCart.viewCart().size());

    }

    @Test
    void removingItemThatIsNotInCartThrowsException() {
        ItemType alcohol = new ItemType("Alcohol", 15.0, new Money(SEK.instance, 100), 18);
        Item beer = new Item("Beer", alcohol, new Money(SEK.instance, 2000));

        when(mockScanner.scanBarcode("1")).thenReturn(beer);

        assertThrows(IllegalArgumentException.class, () -> shoppingCart.scanAndRemove("1"));

        verify(mockScanner, times(1)).scanBarcode("1");


    }

    @Test
    void nullBarcodeThrowsException() {
        when(mockScanner.scanBarcode(null)).thenThrow(new IllegalArgumentException("Barcode cannot be null"));

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
        when(mockScanner.scanBarcode("Invalid barcode")).thenThrow(new IllegalArgumentException("Barcode not in database"));

        assertThrows(IllegalArgumentException.class, () -> shoppingCart.scanAndAdd("Invalid barcode"));

        verify(mockScanner, times(1)).scanBarcode("Invalid barcode");
    }
}

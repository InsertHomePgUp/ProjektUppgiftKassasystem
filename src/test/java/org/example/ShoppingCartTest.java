package org.example;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {

    Currency currency;
    Money deposit;

    @BeforeEach
    void createCurrency(){
        currency = new Currency("Svenska kronor", "kr",
                100000, 50000, 20000, 10000, 5000, 2000, 1000, 500, 200, 100);
        deposit = new Money(currency, 200);
    }

    @Test
    public void addItemsTest(){
        ShoppingCart s = new ShoppingCart();

        Money cheesePrice = new Money(currency, 8000);
        Money tomatoPrice = new Money(currency, 625);
        Money hamPrice = new Money(currency, 2950);

        Item tomato = new Item("tomato",
                new ItemType("vegetable", 0.25, deposit, 0),
                tomatoPrice);
        Item cheese = new Item("cheese",
                new ItemType("dairy", 0.25, deposit, 0),
                cheesePrice);
        Item ham = new Item("ham",
                new ItemType("chark", 0.25, deposit, 0),
                hamPrice);

        s.addItem(tomato);
        s.addItem(cheese);
        s.addItem(ham);
        assertEquals("[tomato, vegetable, 6,25 kr, cheese, dairy, 80,00 kr, ham, chark, 29,50 kr]", s.viewCart().toString());
    }

    @Test
    public void removeItemsTest(){
        ShoppingCart s = new ShoppingCart();

        Money cheesePrice = new Money(currency, 8000);
        Money tomatoPrice = new Money(currency, 625);
        Money hamPrice = new Money(currency, 625);

        Item tomato = new Item("tomato",
                new ItemType("vegetable", 0.25, deposit, 0),
                tomatoPrice);
        Item cheese = new Item("cheese",
                new ItemType("dairy", 0.25, deposit, 0),
                cheesePrice);
        Item ham = new Item("ham",
                new ItemType("chark", 0.25, deposit, 0),
                hamPrice);

        s.addItem(tomato);
        s.addItem(cheese);
        s.addItem(ham);
        s.removeItem(ham);
        assertEquals("[tomato, vegetable, 6,25 kr, cheese, dairy, 80,00 kr]", s.viewCart().toString());
    }

    @Test
    public void removeItemsInEmptyCart(){
        ShoppingCart s = new ShoppingCart();
        Money hamPrice = new Money(currency, 625);
        Item ham = new Item("ham",
                new ItemType("chark", 0.25, 0, 0),
                hamPrice);
        assertThrows(IllegalArgumentException.class, () -> {
            s.removeItem(ham);
        });
    }
}

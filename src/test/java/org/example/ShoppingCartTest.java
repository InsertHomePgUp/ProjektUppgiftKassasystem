package org.example;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {

    @Test
    public void addItemsTest(){
        ShoppingCart s = new ShoppingCart();
        s.addItem("tomato");
        s.addItem("cheese");
        s.addItem("ham");
        assertEquals("[cheese, ham, tomato]", s.viewCart().toString());
    }

    @Test
    public void removeItemsTest(){
        ShoppingCart s = new ShoppingCart();
        s.addItem("tomato");
        s.addItem("cheese");
        s.addItem("ham");
        s.removeItem("ham");
        assertEquals("[cheese, tomato]", s.viewCart().toString());
    }
}

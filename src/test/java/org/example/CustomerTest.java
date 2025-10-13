package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
    void ConstructorTest() {
        Customer customer = new Customer("Namn Namnson",
                8907028631, 0700143541, "Namn@Namnson.com");
        assertEquals("Namn Namnson", customer.getName());
        assertEquals(8907028631, customer.getPersonalIdentityNumber());
        assertEquals(0700143541, customer.getPhoneNumber());
        assertEquals("Namn@Namnson.com", customer.getEmail());
    }
}

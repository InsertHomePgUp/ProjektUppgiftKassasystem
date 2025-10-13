package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer("Jane Doe", "8907028631", "0700143541", "Jane@Doe.com");
    }

    @Nested
    @DisplayName("Constructor")
    class ConstructorTests {
        @Test
        void ConstructorTest() {
            assertEquals("Jane Doe", customer.getName());
            assertEquals("8907028631", customer.getPersonalIdentityNumber());
            assertEquals("0700143541", customer.getPhoneNumber());
            assertEquals("Jane@Doe.com", customer.getEmail());
            assertFalse(customer.getMembershipStatus());
        }
        //testa om tomma parametrar ger felmeddelande
        @Test
        void EmptyNameParameterTest() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Customer(" ", "8907028631", "0700143541", "Jane@Doe.com"));
        }
        @Test
        void EmptyPIDParameterTest() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Customer("Jane Doe", " ", "0700143541", "Jane@Doe.com"));
        }
        @Test
        void EmptyPhoneNumberParameterTest() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Customer("Jane Doe", "8907028631", " ", "Jane@Doe.com"));
        }
        @Test
        void EmptyEmailParameterTest() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Customer("Jane Doe", "8907028631", "0700143541", " "));
        }

        //null i parametrar
        @Test
        void NullNameParameterTest() {} {
            assertThrows(NullPointerException.class,
                    () -> new Customer(null, "8907028631", "0700143541", "Jane@Doe.com"));
        }
        @Test
        void NullPIDParameterTest() {} {
            assertThrows(NullPointerException.class,
                    () -> new Customer("Jane Doe", null, "0700143541", "Jane@Doe.com"));
        }
        @Test
        void NullPhoneNumberParameterTest() {} {
            assertThrows(NullPointerException.class,
                    () -> new Customer("Jane Doe", "8907028631", null, "Jane@Doe.com"));
        }
        @Test
        void NullEmailParameterTest() {} {
            assertThrows(NullPointerException.class,
                    () -> new Customer("Jane Doe", "8907028631", "0700143541", null));
        }
    }

    @Nested
    @DisplayName("Item List Tests")
    class ItemListTests {
        //testa om man kan ändra i den returnerade listan
        //testa att lägga till en sak i listan
        //testa att det inte finns dubs
    }


    //testa toString


    @Test
    void toStringTest() {
        assertEquals("Name: Jane Doe, PID: 8907028631, Phone Number: 0700143541, Email: Jane@Doe.com", customer.toString());
    }

    @Test
    void setToHasMembership() {
        customer.setMembershipStatus(true);
        assertTrue(customer.getMembershipStatus());
    }

}

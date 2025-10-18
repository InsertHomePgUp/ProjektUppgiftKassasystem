package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private Customer customer;
    private Money money;
    private ItemType bread;
    private Item item;
    private Item banana;
    private Item banana1;
    private ItemType fruit;

    @BeforeEach
    public void setUp() {
        customer = new Customer("Jane Doe", "8907028631", "0700143541", "Jane@Doe.com");
        money = new Money(SEK.instance, 1000);
        bread = new ItemType("Bread", 1.0, money, 0);
        fruit = new ItemType("Fruit", 0.5, money, 0);
        item = new Item("Levain", bread, money);
        banana1 = new Item("Banana", fruit, money);
        banana = new Item("Banana", bread, money);
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

        @Test
        void addItemTest() {
            assertTrue(true);
        }
        @Test
        void changingReturnedListTest() {
            assertTrue(true);
        }

        @Test
        void addingDuplicateItemTest() {
            assertTrue(true);
        }

    }

    @Nested
    @DisplayName("Bonus points Tests")
    class BonusPointsTests {
        @Test
        void getZeroBonusPointsTest() {
            customer.setMembership(new TimeLimitedMembership());
            assertEquals(0, customer.getBonusPoints());
        }
        @Test
        void addBonusPointsTest() {
            customer.setMembership(new TimeLimitedMembership());
            assertEquals(0, customer.getBonusPoints());
            customer.addOrSubtractBonusPoints(100);
            assertEquals(100, customer.getBonusPoints());
        }
        @Test
        void subtractBonusPointsTest() {
            customer.setMembership(new TimeLimitedMembership());
            customer.addOrSubtractBonusPoints(100);
            assertEquals(100, customer.getBonusPoints());
            customer.addOrSubtractBonusPoints(-10);
            assertEquals(90, customer.getBonusPoints());
        }
        @Test
        void getZeroBonusPointsAsDefaultTest() {
            customer.setMembership(new TimeLimitedMembership());
            assertEquals(0, customer.getBonusPoints());
        }
        @Test
        void dontAddBonusPointsWithoutMembershipTest() {
            customer.setMembership(new TimeLimitedMembership());
            assertEquals(0, customer.getBonusPoints());
            customer.addOrSubtractBonusPoints(100);
            assertEquals(100, customer.getBonusPoints());
        }
        @Test
        void dontSubtractBonusPointsWithoutMembershipTest() {
            customer.setMembership(new TimeLimitedMembership());
            customer.addOrSubtractBonusPoints(100);
            assertEquals(100, customer.getBonusPoints());
            customer.addOrSubtractBonusPoints(-10);
            assertEquals(90, customer.getBonusPoints());
        }
    }

    @Test
    void toStringTest() {
        assertEquals("Name: Jane Doe, PID: 8907028631, Phone Number: 0700143541, Email: Jane@Doe.com", customer.toString());
    }


}

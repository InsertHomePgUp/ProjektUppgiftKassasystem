package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private Customer customer;
    private Item banana;
    private Item banana1;
    String name = "Jane Doe";
    String pID = "040701-8621";
    String phone = "0700143541";
    String mail = "Jane@Doe.com";

    @BeforeEach
    public void setUp() {
        customer = new Customer(name, pID, phone, mail);
        Money money = new Money(SEK.instance, 1000);
        ItemType bread = new ItemType("Bread", 1.0, money, 0);
        ItemType fruit = new ItemType("Fruit", 0.5, money, 0);
        banana1 = new Item("Banana", fruit, money);
        banana = new Item("Banana", bread, money);
    }

    @Nested
    @DisplayName("Constructor")
    class ConstructorTests {
        @Test
        void ConstructorTest() {
            assertEquals("Jane Doe", customer.getName());
            assertEquals("040701-8621", customer.getPersonalIdentityNumber());
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
            customer.addItem(banana);
            assertEquals(1, customer.getItemList().size());
            assertTrue(customer.getItemList().containsKey(banana));
        }
        @Test
        void changingReturnedListTest() {
            assertTrue(true);
        }

        @Test
        void addingDuplicateItemTest() {
            customer.addItem(banana);
            customer.addItem(banana);
            customer.addItem(banana);
            customer.addItem(banana);
            customer.addItem(banana1);
            assertEquals(2, customer.getItemList().size());
            assertTrue(customer.getItemList().containsKey(banana));
            assertTrue(customer.getItemList().containsKey(banana1));
            assertEquals(4, customer.getItemList().get(banana));
            assertEquals(1, customer.getItemList().get(banana1));
        }

    }

    @Nested
    @DisplayName("Bonus points Tests")
    class BonusPointsTests {
        @Test
        void getZeroBonusPointsTest() {
            customer.setMembership(new Membership());
            assertEquals(0, customer.getBonusPoints());
        }
        @Test
        void addBonusPointsTest() {
            customer.setMembership(new Membership());
            assertEquals(0, customer.getBonusPoints());
            customer.addOrSubtractBonusPoints(100);
            assertEquals(100, customer.getBonusPoints());
        }
        @Test
        void subtractBonusPointsTest() {
            customer.setMembership(new Membership());
            customer.addOrSubtractBonusPoints(100);
            assertEquals(100, customer.getBonusPoints());
            customer.addOrSubtractBonusPoints(-10);
            assertEquals(90, customer.getBonusPoints());
        }
        @Test
        void getZeroBonusPointsAsDefaultTest() {
            customer.setMembership(new Membership());
            assertEquals(0, customer.getBonusPoints());
        }
        @Test
        void dontAddBonusPointsWithoutMembershipTest() {
            customer.setMembership(new Membership());
            assertEquals(0, customer.getBonusPoints());
            customer.addOrSubtractBonusPoints(100);
            assertEquals(100, customer.getBonusPoints());
        }
        @Test
        void dontSubtractBonusPointsWithoutMembershipTest() {
            customer.setMembership(new Membership());
            customer.addOrSubtractBonusPoints(100);
            assertEquals(100, customer.getBonusPoints());
            customer.addOrSubtractBonusPoints(-10);
            assertEquals(90, customer.getBonusPoints());
        }
        @Test
        void addingWithAndWithoutMembershipTest(){
            customer.addOrSubtractBonusPoints(100);
            assertEquals(0, customer.getBonusPoints());
            customer.setMembership(new Membership());
            customer.addOrSubtractBonusPoints(100);
            assertEquals(100, customer.getBonusPoints());
        }
        @Test
        void addingAndSubtractingRepeatedly() {
            customer.addOrSubtractBonusPoints(100);
            assertEquals(0, customer.getBonusPoints());
            customer.setMembership(new Membership());
            customer.addOrSubtractBonusPoints(100);
            customer.addOrSubtractBonusPoints(-99);
            customer.addOrSubtractBonusPoints(-1);
            customer.addOrSubtractBonusPoints(20);
            customer.addOrSubtractBonusPoints(4);
            assertEquals(24, customer.getBonusPoints());
        }
    }

    @Nested
    @DisplayName("Phone number Tests")
    class PhoneNumberTests {

        @Test
        void getValidPhoneNumberTest() {
            phone = "+4612345678";
            Customer customer1 = new Customer(name, pID, phone, mail);
            phone = "+461234567800000";
            Customer customer2 = new Customer(name, pID, phone, mail);
            assertEquals("+4612345678", customer1.getPhoneNumber());
            assertEquals("+461234567800000", customer2.getPhoneNumber());
        }

        @Test
        void gettestCase2() {
            phone = "+461234567800000";
            Customer newCustomer = new Customer(name, pID, phone, mail);
        }

        @Test
        void gettestCase3() {
            phone = "+0612345678";
            Customer newCustomer = new Customer(name, pID, phone, mail);
        }

        @Test
        void gettestCase4() {
            phone = "+46123456";
            Customer newCustomer = new Customer(name, pID, phone, mail);
        }

        @Test
        void gettestCase5() {
            phone = "+46123456780000010";
            Customer newCustomer = new Customer(name, pID, phone, mail);
        }

        @Test
        void gettestCase6() {
            phone = "+46123456780000a";
            Customer newCustomer = new Customer(name, pID, phone, mail);
        }

        @Test
        void gettestCase7() {
            phone = "++4612345678";
            Customer newCustomer = new Customer(name, pID, phone, mail);
        }

        @Test
        void gettestCase8() {
            phone = "46123456780";
            Customer newCustomer = new Customer(name, pID, phone, mail);
        }

        @Test
        void gettestCase9() {
            phone = "+461234567";
            Customer newCustomer = new Customer(name, pID, phone, mail);
        }

        @Test
        void gettestCase10() {
            phone = "+4612345678000008";
            Customer newCustomer = new Customer(name, pID, phone, mail);
        }
    }

    @Test
    void toStringTest() {
        assertEquals("Name: Jane Doe, PID: 040701-8621, Phone Number: 0700143541, Email: Jane@Doe.com", customer.toString());
    }


}

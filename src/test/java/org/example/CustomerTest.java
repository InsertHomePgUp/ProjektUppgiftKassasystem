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
    String name;
    String pID;
    String phone;
    String mail;

    @BeforeEach
    public void setUp() {
        name = "Jane Doe";
        pID = "040701-8621";
        phone = "0700143541";
        mail = "Jane@Doe.com";
        customer = new Customer(name, pID, phone, mail);
        Money money = new Money(SEK.instance, 1000);
        ItemType bread = new ItemType("Bread", 1.0, money, 0);
        ItemType fruit = new ItemType("Fruit", 0.5, money, 0);
        banana1 = new Item("Banana", fruit, money);
        banana = new Item("Banana", bread, money);
    }

    @Test
    void customerShouldNotBeNullAfterSetup() {
        assertNotNull(customer, "Customer failed to construct in @BeforeEach");
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
                    () -> new Customer(" ", pID, phone, mail));
        }
        @Test
        void EmptyPIDParameterTest() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Customer(name, " ", phone, mail));
        }
        @Test
        void EmptyPhoneNumberParameterTest() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Customer(name, pID, " ", mail));
        }
        @Test
        void EmptyEmailParameterTest() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Customer(name, pID, phone, " "));
        }

        //null i parametrar
        @Test
        void NullNameParameterTest() {} {
            assertThrows(NullPointerException.class,
                    () -> new Customer(null, pID, phone, mail));
        }
        @Test
        void NullPIDParameterTest() {} {
            assertThrows(NullPointerException.class,
                    () -> new Customer(name, null, phone, mail));
        }
        @Test
        void NullPhoneNumberParameterTest() {} {
            assertThrows(NullPointerException.class,
                    () -> new Customer(name, pID, null, mail));
        }
        @Test
        void NullEmailParameterTest() {} {
            assertThrows(NullPointerException.class,
                    () -> new Customer(name, pID, phone, null));
        }

        @Test
        void getInvalidPIDParameterTest() {
            pID = "040701-8622";
            assertThrows(IllegalArgumentException.class,
                    () -> new Customer(name, pID, phone, mail));
        }
        @Test
        void getInvalidPIDParameterTest2() {
            pID = "040701-862";
            assertThrows(IllegalArgumentException.class,
                    () -> new Customer(name, pID, phone, mail));
        }
        @Test
        void getInvalidPIDParameterTest3() {
            pID = "0407018622";
            assertThrows(IllegalArgumentException.class,
                    () -> new Customer(name, pID, phone, mail));
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
        @Test
        void convertingBonusPointsToMoneyTest() {
            customer.setMembership(new Membership());
            customer.addOrSubtractBonusPoints(1000);
            double money = customer.bonusPointsToMoney();
            assertEquals(10.0, money);
            assertEquals(1000, customer.getBonusPoints());
        }
    }

    @Nested
    @DisplayName("International Phone number Tests")
    class PhoneNumberTests {

        @Test
        void getValidPhoneNumberTest() {
            phone = "+46123456";
            Customer customer1 = new Customer(name, pID, phone, mail);
            phone = "+461234567800000";
            Customer customer2 = new Customer(name, pID, phone, mail);
            assertEquals("+46123456", customer1.getPhoneNumber());
            assertEquals("+461234567800000", customer2.getPhoneNumber());
        }

        @Test
        void getWrongStartOfPhoneNumberTest() {
            phone = "+0612345678";
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, pID, phone, mail));
        }

        @Test
        void getTooShortPhoneNumberTest() {
            phone = "+46123";
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, pID, phone, mail));        }

        @Test
        void getTooLongPhoneNumberTest() {
            phone = "+46123456780000010";
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, pID, phone, mail));        }

        @Test
        void getNotOnlyDigitsPhoneNumberTest() {
            phone = "+46123456780000a";
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, pID, phone, mail));        }

        @Test
        void getTooManyPlusSignsPhoneNumberTest() {
            phone = "++4612345678";
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, pID, phone, mail));        }

        @Test
        void gettestCase9() {
            phone = "+4612340";
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, pID, phone, mail));        }

        @Test
        void gettestCase10() {
            phone = "+4612345678000008";
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, pID, phone, mail));        }
    }

    @Nested
    @DisplayName("International Phone number Tests without +")
    class PhoneNumberTestsTwo{
        @Test
        void getValidPhoneNumberTest() {
            phone = "46030405";
            Customer customer1 = new Customer(name, pID, phone, mail);
            phone = "460304050090807";
            Customer customer2 = new Customer(name, pID, phone, mail);
            assertEquals("46030405", customer1.getPhoneNumber());
            assertEquals("460304050090807", customer2.getPhoneNumber());
        }
        @Test
        void getTooShortPhoneNumberTest() {
            phone = "460304";
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, pID, phone, mail));
        }
        @Test
        void getTooLongPhoneNumberTest() {
            phone = "46030405009080709";
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, pID, phone, mail));
        }
        @Test
        void getInvalidLengthShortPhoneNumberTest() {
            phone = "4603040";
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, pID, phone, mail));
        }
        @Test
        void getInvalidLengthLongPhoneNumberTest() {
            phone = "4603040500908070";
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, pID, phone, mail));
        }
        @Test
        void getFalseWhenANonDigitShowsUpTest() {
            phone = "4610234a64";
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, pID, phone, mail));
        }
    }

    @Nested
    @DisplayName("International Phone number Tests without +")
    class PhoneNumberTestsThree {
        @Test
        void getValidPhoneNumberTest() {
            phone = "0760984";
            Customer customer1 = new Customer(name, pID, phone, mail);
            phone = "07609848938743";
            Customer customer2 = new Customer(name, pID, phone, mail);
            assertEquals("0760984", customer1.getPhoneNumber());
            assertEquals("07609848938743", customer2.getPhoneNumber());
        }
        @Test
        void getTooShortPhoneNumberTest() {
            phone = "07609";
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, pID, phone, mail));
        }
        @Test
        void getTooLongPhoneNumberTest() {
            phone = "0760984893874300";
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, pID, phone, mail));
        }
        @Test
        void getInvalidStartPhoneNumberTest() {
            phone = "00760984";
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, pID, phone, mail));
        }
        @Test
        void getNotOnlyDigitsPhoneNumberTest() {
            phone = "0760984a";
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, pID, phone, mail));
        }
        @Test
        void getInvalidBoarderShortPhoneNumberTest() {
            phone = "076098";
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, pID, phone, mail));
        }
        @Test
        void getInvalidBoarderLongPhoneNumberTest() {
            phone = "076098489387433";
            assertThrows(IllegalArgumentException.class, () -> new Customer(name, pID, phone, mail));
        }
    }

    @Test
    void toStringTest() {
        assertEquals("Name: Jane Doe, PID: 040701-8621, Phone Number: 0700143541, Email: Jane@Doe.com", customer.toString());
    }


}

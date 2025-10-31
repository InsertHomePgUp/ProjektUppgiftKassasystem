package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class MembershipTest {
    private Customer customer;
    private Item banana;

    @BeforeEach
    public void setUp() {
        customer = new Customer("Jane Doe", "040701-8621", "0700143541", "Jane@Doe.com");
        Money money = new Money(SEK.instance, 100);
        ItemType fruit = new ItemType("Fruit", 0.5, money, 0);
        banana = new Item("Banana", fruit, money);
    }


    @Nested
    @DisplayName("Membershiptests in customer class")
    class MembershipTestsInCustomerClass {

        @Test
        void testIfDefaultIsFalse() {
            assertFalse(customer.isMember());
        }

        @Test
        void testIfMembershipCanBeAdded() {
            Membership membership = new Membership();
            customer.setMembership(membership);
            assertTrue(customer.isMember());
        }
    }
    @Nested
    @DisplayName("Membership method tests")
    class NoMembershipTest {

        @Test
        void isActive_shouldBeFalse() {
            NoMembership m = new NoMembership();
            assertFalse(m.isActive());
        }

        @Test
        void description_shouldBeConstant() {
            NoMembership m = new NoMembership();
            assertEquals("No membership.", m.description());
        }

        @Test
        void setDescription_shouldNotChangeDescription() {
            NoMembership m = new NoMembership();
            m.setDescription("Gold"); // no-op i implementationen
            assertEquals("No membership.", m.description());
        }

        @Test
        void getDiscounts_shouldBeNull() {
            NoMembership m = new NoMembership();
            HashMap<Item, Double> discounts = m.getDiscounts();
            assertNull(discounts); // enligt din implementation
        }

        @Test
        void addDiscountRate_shouldReturnFalse_evenForValidInput() {
            NoMembership m = new NoMembership();
            assertFalse(m.addDiscountRate(null, 0.15)); // spelar ingen roll: returnerar alltid false
            assertFalse(m.addDiscountRate(null, 0.0));
            assertFalse(m.addDiscountRate(null, 1.0));
        }
    }
    @Nested
    @DisplayName("Membership method tests")
    class AMembershipTest {

        @Test
        void isActive_shouldBeTrue() {
            Membership m = new Membership();
            assertTrue(m.isActive());
        }

        @Test
        void description_isConstant_and_setDescriptionIsNoOp() {
            Membership m = new Membership();
            assertEquals("Time limited.", m.description());
            m.setDescription("Gold 2025"); // no-op by design
            assertEquals("Time limited.", m.description());
        }

        @Test
        void addDiscountRate_storesEntry_andReturnsTrue() {
            Membership m = new Membership();

            boolean added = m.addDiscountRate(banana, 0.15);
            assertTrue(added);

            HashMap<Item, Double> map = m.getDiscounts();
            assertEquals(1, map.size());
            assertEquals(0.15, map.get(banana), 1e-9);
        }

        @Test
        void addDiscountRate_overwritesExistingEntry_forSameItem() {
            Membership m = new Membership();

            m.addDiscountRate(banana, 0.10);
            m.addDiscountRate(banana, 0.25);  // overwrite
            assertEquals(1, m.getDiscounts().size());
            assertEquals(0.25, m.getDiscounts().get(banana), 1e-9);
        }

        @Test
        void getDiscounts_returnsBackedMap_mutationsAffectInternalState() {
            Membership m = new Membership();
            m.addDiscountRate(banana, 0.10);

            HashMap<Item, Double> sameMap = m.getDiscounts();
            sameMap.clear(); // mutate the returned map

            // Because Membership returns the internal map, clearing the returned map
            // clears the membership's discounts too:
            assertTrue(m.getDiscounts().isEmpty());
        }

        @Test
        void acceptsNullItem_andAnyRate_currentBehavior() {
            Membership m = new Membership();

            // current implementation allows null key and any rate
            assertTrue(m.addDiscountRate(null, -0.5));
            assertTrue(m.addDiscountRate(null, 1.5));

            assertEquals(1, m.getDiscounts().size());
            assertEquals(1.5, m.getDiscounts().get(null), 1e-9); // last write wins
        }
    }
}

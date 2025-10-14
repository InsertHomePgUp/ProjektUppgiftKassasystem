package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MembershipTest {
    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer("Jane Doe", "8907028631", "0700143541", "Jane@Doe.com");
    }

    @Nested
    @DisplayName("Membershiptests in customerclass")
    class MembershipTestsInCustomerclass {

        @Test
        void testIfDefaultIsFalse() {
            assertFalse(customer.isMember());
        }

        @Test
        void testIfMembershipCanBeAdded() {
            Membership membership = new TimeLimitedMembership();
            customer.setMembership(membership);
            assertTrue(customer.isMember());
        }
    }
}

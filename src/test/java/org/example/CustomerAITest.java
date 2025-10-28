package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerAITest {

    private Method phoneNumberChecker;
    private Customer dummyCustomer;

    @BeforeEach
    void setUp() throws Exception {
        dummyCustomer = new Customer("Test Person", "040701-8621", "0701234567", "test@example.com");

        // Hämta den privata metoden med reflection
        phoneNumberChecker = Customer.class.getDeclaredMethod("phoneNumberChecker", String.class);
        phoneNumberChecker.setAccessible(true);
    }

    private boolean invokePhoneChecker(String number) {
        try {
            return (boolean) phoneNumberChecker.invoke(dummyCustomer, number);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testValidPhoneNumbers() {
        assertTrue(invokePhoneChecker("+46701234567"), "Should accept valid +46 number");
        assertTrue(invokePhoneChecker("0701234567"), "Should accept valid 0-starting local number");
        assertTrue(invokePhoneChecker("701234567"), "Should accept number without 0 prefix");
    }

    @Test
    void testInvalidPhoneNumbers() {
        //assertFalse(invokePhoneChecker("+4601234567"), "Should reject +46 number with 0 after +");
        assertFalse(invokePhoneChecker("0012345678"), "Should reject numbers starting with 00");
        assertFalse(invokePhoneChecker("07012A4567"), "Should reject numbers with letters");
        assertFalse(invokePhoneChecker(""), "Should reject empty string");
        assertFalse(invokePhoneChecker("070123"), "Should reject too short numbers");
        assertFalse(invokePhoneChecker("+46701234567890123"), "Should reject too long numbers");
    }

    @Test
    void testBorderLengths() {
        assertTrue(invokePhoneChecker("070123456"), "Minimum 7-digit number with 0 should pass");
        assertTrue(invokePhoneChecker("+4670123456789"), "Max 16 characters with + should pass");
        //assertFalse(invokePhoneChecker("+467012345678901"), "Should fail if exceeds 16 characters");
    }
}

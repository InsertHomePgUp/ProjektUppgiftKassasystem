package org.example;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    public Currency createSEK(){
        Currency SEK = new Currency("Svenska kronor", "kr",
                100000, 50000, 20000, 10000, 5000, 2000, 1000, 500, 200, 100);
        return SEK;
    }

    public Customer createCustomerJohnSmith(){
        return new Customer("John Smith", "000701-1299", "0701234567", "abc@123.se");
    }

    public List<Item> createItems(){
        List<Item> items = new ArrayList<>();
        Currency SEK = createSEK();

        Money deposit = new Money(SEK, 100);

        ItemType vegetable = new ItemType("Vegetable", 0.25, deposit, 0);
        Money tomatoPrice = new Money(SEK, 625);
        Item tomato = new Item("Tomato", vegetable, tomatoPrice);
        items.add(tomato);
        Money cucumberPrice = new Money(SEK, 1000);
        Item cucumber = new Item("Cucumber", vegetable, cucumberPrice);
        items.add(cucumber);

        ItemType dairy = new ItemType("Dairy", 0.25, deposit, 0);
        Money milkPrice = new Money(SEK, 1500);
        Item milk = new Item("Milk", dairy, milkPrice);
        items.add(milk);
        Money cheesePrice = new Money(SEK, 8000);
        Item cheese = new Item("Cheese", dairy, cheesePrice);
        items.add(cheese);

        return items;
    }

    public List<Deductor> createDeductors(){
        List<Deductor> deductors = new ArrayList<Deductor>();

        Deductor Presentkort50 = new Deductor(50, "Presentkort");
        deductors.add(Presentkort50);

        Deductor Rabatt20 = new Deductor(20, "Rabatt");
        deductors.add(Rabatt20);

        return deductors;
    }

    @Test
    public void createTransaction(){
        Transaction t = new Transaction(createItems(), createDeductors(), createCustomerJohnSmith());
    }

    @Test
    public void createReceipt(){
        Transaction t = new Transaction(createItems(), createDeductors(), createCustomerJohnSmith());
        t.payWithCard();
        t.getReceipt();
    }

    @Test
    public void correctTotalWithoutDeductors(){
        Currency SEK = createSEK();
        List<Deductor> emptyList = new ArrayList<Deductor>();
        Transaction t = new Transaction(createItems(), emptyList, createCustomerJohnSmith());
        assertEquals(111.25, t.getTotalPrice());
    }
    @Test
    public void correctTotalWithDeductors(){
        Currency SEK = createSEK();
        Transaction t = new Transaction(createItems(), createDeductors(), createCustomerJohnSmith());
        assertEquals(49, t.getTotalPrice());
    }

    @Test
    public void receiptContainsAll(){
        Transaction t = new Transaction(createItems(), createDeductors(), createCustomerJohnSmith());
        t.payWithCard();
        assertEquals("Tomato   6,25 kr\n" +
                "Cucumber   10,00 kr\n" +
                "Milk   15,00 kr\n" +
                "Cheese   80,00 kr\n" +
                "Presentkort   50.0\n" +
                "Rabatt   20.0\n" +
                "Totalt:   49.0", t.getReceipt().toString());
    }

    @Test
    public void paidWithCashExactAmount(){
        ArrayList<Integer> moneyToAdd = new ArrayList<Integer>();
        moneyToAdd.add(2);
        moneyToAdd.add(2);
        moneyToAdd.add(5);
        moneyToAdd.add(20);
        moneyToAdd.add(20);
        CashRegister register = new CashRegister(moneyToAdd);
        Transaction t = new Transaction(createItems(), createDeductors(), createCustomerJohnSmith());
        assertEquals(new ArrayList<>().toString() , t.payWithCash(register, moneyToAdd).toString());
    }

    @Test
    public void paidWithCashHigherAmount(){
        ArrayList<Integer> moneyToAdd = new ArrayList<Integer>();
        moneyToAdd.add(1);
        moneyToAdd.add(1);
        moneyToAdd.add(1);
        moneyToAdd.add(2);
        moneyToAdd.add(10);

        ArrayList<Integer> customerPaysWith = new ArrayList<>();
        customerPaysWith.add(50);

        ArrayList<Integer> expectedChange = new ArrayList<>();
        expectedChange.add(1);

        CashRegister register = new CashRegister(moneyToAdd);
        Transaction t = new Transaction(createItems(), createDeductors(), createCustomerJohnSmith());

        assertEquals(expectedChange.toString() , t.payWithCash(register, customerPaysWith).toString());
    }

    @Test
    public void receiptWithoutPaying(){
        Currency SEK = createSEK();
        Transaction t = new Transaction(createItems(), createDeductors(), createCustomerJohnSmith());
        assertThrows(IllegalStateException.class, t::getReceipt);
    }

    @Test
    public void correctValueWithBonuscheck100(){
        List<Deductor> deductors = new ArrayList<Deductor>();
        Deductor Bonuscheck100 = new Deductor(100, "Bonuscheck");
        deductors.add(Bonuscheck100);
        Currency SEK = createSEK();
        Transaction t = new Transaction(createItems(), deductors, createCustomerJohnSmith());
        assertEquals(11.25, t.getTotalPrice());
    }

    //tillståndsmaskin tester
    @Test
    public void noDeductorPayWithCashCustomerIsMember(){

        ArrayList <Item> tempShoppingCart = new ArrayList<>();
        Customer customer = new Customer("Marko Olio", "650816-1236", "+46708817385", "markoolio@live.se");
        customer.setMembership(new Membership());
        List<Integer> denominators = new ArrayList<>(List.of(
                1, 1, 2, 2, 5, 5, 10, 10, 20, 20, 50, 50, 100, 100, 200, 200, 500, 500, 1000, 1000
        ));

        CashRegister cr = new CashRegister(denominators);

        Currency SEK = createSEK();

        Money deposit = new Money(SEK, 100);

        //totalpris = 15763 I Minor UNit

        ItemType vegetable = new ItemType("Vegetable", 0.25, deposit, 0);
        Money tomatoPrice = new Money(SEK, 625);
        Item tomato = new Item("Tomato", vegetable, tomatoPrice);
        tempShoppingCart.add(tomato);
        Money cucumberPrice = new Money(SEK, 1099);
        Item cucumber = new Item("Cucumber", vegetable, cucumberPrice);
        tempShoppingCart.add(cucumber);
        Money onionPrice = new Money(SEK, 755);
        Item onion = new Item("Onion", vegetable, onionPrice);
        tempShoppingCart.add(onion);

        ItemType dairy = new ItemType("Dairy", 0.25, deposit, 0);
        Money milkPrice = new Money(SEK, 1599);
        Item milk = new Item("Milk", dairy, milkPrice);
        tempShoppingCart.add(milk);
        Money cheesePrice = new Money(SEK, 8095);
        Item cheese = new Item("Cheese", dairy, cheesePrice);
        tempShoppingCart.add(cheese);
        Money yoghurtPrice = new Money(SEK, 3590);
        Item yoghurt = new Item("Yoghurt", dairy, yoghurtPrice);
        tempShoppingCart.add(yoghurt);

        ArrayList<Deductor> noDeductors = new ArrayList<>();

        Transaction t = new Transaction(tempShoppingCart, noDeductors, customer);

        ArrayList<Integer> customerDenominatorsPaidWith = new ArrayList<>(List.of(200));
        List<Integer> change = t.payWithCash(cr, customerDenominatorsPaidWith);
        t.applyDeductors();

        TreeMap<Integer, Integer> expectedInventory = new TreeMap<Integer, Integer>();
        expectedInventory.put(1, 2);
        expectedInventory.put(2, 1);
        expectedInventory.put(5, 2);
        expectedInventory.put(10, 2);
        expectedInventory.put(50, 2);
        expectedInventory.put(100, 2);
        expectedInventory.put(200, 3);
        expectedInventory.put(500, 2);
        expectedInventory.put(1000, 2);

        ArrayList<Integer> expectedChange = new ArrayList<>(List.of(20, 20, 2));

        StringBuilder receipt = t.getReceipt();

        assertAll("Kollar flera saker",
                () -> assertEquals(157.63, t.getTotalPrice(), "fel pris"),
                () -> assertEquals(expectedInventory, cr.getInventory(), "fel mängd i kassan"),
                () -> assertEquals(1, customer.getBonusPoints(), "Fel bonuspoäng i kunds medlemskap"),
                () -> assertEquals(expectedChange, change, "Kund har fått fel växel"),
                () -> assertNotNull(receipt, "Inget kvitto finns")
        );
    }

    @Test
    public void deductorTotalPriceLessThanZeroNonMember(){
        ArrayList <Item> tempShoppingCart = new ArrayList<>();
        Customer customer = new Customer("Marko Olio", "650816-1236", "+46708817385", "markoolio@live.se");
        List<Integer> denominators = new ArrayList<>(List.of(
                1, 1, 2, 2, 5, 5, 10, 10, 20, 20, 50, 50, 100, 100, 200, 200, 500, 500, 1000, 1000
        ));

        CashRegister cr = new CashRegister(denominators);

        Currency SEK = createSEK();

        Money deposit = new Money(SEK, 100);

        //totalpris = 15763 I Minor UNit

        ItemType vegetable = new ItemType("Vegetable", 0.25, deposit, 0);
        Money tomatoPrice = new Money(SEK, 625);
        Item tomato = new Item("Tomato", vegetable, tomatoPrice);
        tempShoppingCart.add(tomato);
        Money cucumberPrice = new Money(SEK, 1099);
        Item cucumber = new Item("Cucumber", vegetable, cucumberPrice);
        tempShoppingCart.add(cucumber);
        Money onionPrice = new Money(SEK, 755);
        Item onion = new Item("Onion", vegetable, onionPrice);
        tempShoppingCart.add(onion);

        ItemType dairy = new ItemType("Dairy", 0.25, deposit, 0);
        Money milkPrice = new Money(SEK, 1599);
        Item milk = new Item("Milk", dairy, milkPrice);
        tempShoppingCart.add(milk);
        Money cheesePrice = new Money(SEK, 8095);
        Item cheese = new Item("Cheese", dairy, cheesePrice);
        tempShoppingCart.add(cheese);
        Money yoghurtPrice = new Money(SEK, 3590);
        Item yoghurt = new Item("Yoghurt", dairy, yoghurtPrice);
        tempShoppingCart.add(yoghurt);

        ArrayList<Deductor> deductors = new ArrayList<>();
        Deductor presentkort200 = new Deductor(200, "Presentkort");
        deductors.add(presentkort200);

        Transaction t = new Transaction(tempShoppingCart, deductors, customer);

        t.payWithCard();
        t.applyDeductors();

        TreeMap<Integer, Integer> expectedInventory = new TreeMap<Integer, Integer>();
        expectedInventory.put(1, 2);
        expectedInventory.put(2, 2);
        expectedInventory.put(5, 2);
        expectedInventory.put(10, 2);
        expectedInventory.put(20, 2);
        expectedInventory.put(50, 2);
        expectedInventory.put(100, 2);
        expectedInventory.put(200, 2);
        expectedInventory.put(500, 2);
        expectedInventory.put(1000, 2);

        StringBuilder receipt = t.getReceipt();

        assertAll("Kollar flera saker",
                () -> assertEquals(expectedInventory, cr.getInventory(), "fel mängd i kassan"),
                () -> assertEquals(0, customer.getBonusPoints(), "Fel bonuspoäng i kunds medlemskap"),
                () -> assertEquals(42.37, presentkort200.getAmount(), 0.000001, "Presentkort ej korrekt uppdaterat"),
                () -> assertTrue(t.isPaid(), "Betalning ej genomförd"),
                () -> assertNotNull(receipt, "Inget kvitto finns")
        );
    }

    @Test
    public void deductorPayWithCardNonMemberWantsToBecomeMember(){
        ArrayList <Item> tempShoppingCart = new ArrayList<>();
        Customer customer = new Customer("Marko Olio", "650816-1236", "+46708817385", "markoolio@live.se");
        List<Integer> denominators = new ArrayList<>(List.of(
                1, 1, 2, 2, 5, 5, 10, 10, 20, 20, 50, 50, 100, 100, 200, 200, 500, 500, 1000, 1000
        ));

        CashRegister cr = new CashRegister(denominators);

        Currency SEK = createSEK();

        Money deposit = new Money(SEK, 100);

        //totalpris = 15763 I Minor UNit
        //med deductor 10763

        ItemType vegetable = new ItemType("Vegetable", 0.25, deposit, 0);
        Money tomatoPrice = new Money(SEK, 625);
        Item tomato = new Item("Tomato", vegetable, tomatoPrice);
        tempShoppingCart.add(tomato);
        Money cucumberPrice = new Money(SEK, 1099);
        Item cucumber = new Item("Cucumber", vegetable, cucumberPrice);
        tempShoppingCart.add(cucumber);
        Money onionPrice = new Money(SEK, 755);
        Item onion = new Item("Onion", vegetable, onionPrice);
        tempShoppingCart.add(onion);

        ItemType dairy = new ItemType("Dairy", 0.25, deposit, 0);
        Money milkPrice = new Money(SEK, 1599);
        Item milk = new Item("Milk", dairy, milkPrice);
        tempShoppingCart.add(milk);
        Money cheesePrice = new Money(SEK, 8095);
        Item cheese = new Item("Cheese", dairy, cheesePrice);
        tempShoppingCart.add(cheese);
        Money yoghurtPrice = new Money(SEK, 3590);
        Item yoghurt = new Item("Yoghurt", dairy, yoghurtPrice);
        tempShoppingCart.add(yoghurt);

        ArrayList<Deductor> deductors = new ArrayList<>();
        Deductor presentkort50 = new Deductor(50, "Presentkort");
        deductors.add(presentkort50);

        Transaction t = new Transaction(tempShoppingCart, deductors, customer);

        t.payWithCard();
        t.makeCustomerMember();
        StringBuilder receipt = t.getReceipt();
        t.applyDeductors();

        TreeMap<Integer, Integer> expectedInventory = new TreeMap<Integer, Integer>();
        expectedInventory.put(1, 2);
        expectedInventory.put(2, 2);
        expectedInventory.put(5, 2);
        expectedInventory.put(10, 2);
        expectedInventory.put(20, 2);
        expectedInventory.put(50, 2);
        expectedInventory.put(100, 2);
        expectedInventory.put(200, 2);
        expectedInventory.put(500, 2);
        expectedInventory.put(1000, 2);


        assertAll("Kollar flera saker",
                () -> assertEquals(expectedInventory, cr.getInventory(), "fel mängd i kassan"),
                () -> assertEquals(1, customer.getBonusPoints(), "Fel bonuspoäng i kunds medlemskap"),
                () -> assertEquals(0, presentkort50.getAmount(), "Presentkort ej korrekt uppdaterat"),
                () -> assertTrue(customer.isMember(), "Kund har ej blivit medlem"),
                () -> assertTrue(t.isPaid(), "Betalning ej genomförd"),
                () -> assertNotNull(receipt, "Inget kvitto finns")
        );
    }

    @Test
    public void invalidDeductorTypeThrowsException() {
        List<Item> items = createItems();
        List<Deductor> deductors = new ArrayList<>();
        deductors.add(new Deductor(10, "OgiltigTyp"));
        Transaction t = new Transaction(items, deductors, createCustomerJohnSmith());
        assertThrows(IllegalArgumentException.class, t::getTotalPrice);
    }

    @Test
    public void payWithCashInsufficientThrowsException() {
        List<Item> items = createItems();
        List<Deductor> deductors = new ArrayList<>();
        Transaction t = new Transaction(items, deductors, createCustomerJohnSmith());
        CashRegister cr = new CashRegister(new ArrayList<>(List.of(100, 50, 20)));
        List<Integer> paid = new ArrayList<>(List.of(10, 10)); // för lite
        assertThrows(IllegalArgumentException.class, () -> t.payWithCash(cr, paid));
    }

    @Test
    public void applyDeductorsReducesTotalCorrectly() {
        List<Item> items = createItems();
        List<Deductor> deductors = new ArrayList<>();
        Deductor rabatt = new Deductor(10, "Rabatt");
        deductors.add(rabatt);
        Transaction t = new Transaction(items, deductors, createCustomerJohnSmith());
        t.applyDeductors();
        double expected = 111.25 - (111.25 * 0.10); // 10% rabatt
        assertEquals(expected, t.getTotalPrice(), 0.0001);
    }

    @Test
    public void payWithCardMarksAsPaidAndUpdatesBonusPoints() {
        Customer c = createCustomerJohnSmith();
        c.setMembership(new Membership());
        Transaction t = new Transaction(createItems(), new ArrayList<>(), c);
        t.payWithCard();
        assertTrue(t.isPaid());
        assertEquals((long)t.getTotalPrice() / 100, c.getBonusPoints());
    }

    @Test
    public void totalPriceCannotGoBelowZero() {
        List<Item> items = createItems();
        List<Deductor> deductors = new ArrayList<>();
        deductors.add(new Deductor(10000, "Bonuscheck"));
        Transaction t = new Transaction(items, deductors, createCustomerJohnSmith());
        double total = t.getTotalPrice();
        assertEquals(0, total);
    }

    @Test
    public void cantGiveChange(){
        List<Item> items = createItems();
        List<Deductor> deductors = new ArrayList<>();
        Transaction t = new Transaction(items, deductors, createCustomerJohnSmith());
        CashRegister cr = new CashRegister(new ArrayList<>(List.of(100, 50, 20)));
        assertThrows(IllegalStateException.class, () -> t.payWithCash(cr, new ArrayList<>(List.of(1000))));
    }

    @Test
    public void bonuscheckDeductorAdjustsBonusPointsCorrectlyWhenAmountExceedsTotal() {
        Currency SEK = createSEK();
        Money cheapItemPrice = new Money(SEK, 500); // 5 kr
        ItemType miscType = new ItemType("Misc", 0, new Money(SEK, 0), 0);
        Item cheapItem = new Item("CheapItem", miscType, cheapItemPrice);
        List<Item> items = List.of(cheapItem);

        Deductor bigBonuscheck = new Deductor(100, "Bonuscheck"); // 100 kr
        List<Deductor> deductors = List.of(bigBonuscheck);

        Customer customer = createCustomerJohnSmith();
        customer.setMembership(new Membership());

        Transaction t = new Transaction(items, deductors, customer);

        t.applyDeductors();

        assertEquals(0, t.getTotalPrice());

        assertEquals(0, customer.getBonusPoints(), "Bonuspoints should not go negative beyond total price");
    }

    @Test
    public void applyDeductorsThrowsExceptionForInvalidDeductor() {
        List<Item> items = createItems();

        Deductor invalidDeductor = new Deductor(10, "OgiltigTyp");
        List<Deductor> deductors = List.of(invalidDeductor);

        Transaction t = new Transaction(items, deductors, createCustomerJohnSmith());

        assertThrows(IllegalArgumentException.class, t::applyDeductors);
    }

    @Test
    public void bonuscheckDeductorSubtractsFullAmountWhenTotalExceedsDeductor() {
        Currency SEK = createSEK();

        Money expensiveItemPrice = new Money(SEK, 2000); // 20 kr
        ItemType miscType = new ItemType("Misc", 0, new Money(SEK, 0), 0);
        Item expensiveItem = new Item("ExpensiveItem", miscType, expensiveItemPrice);
        List<Item> items = List.of(expensiveItem);

        Deductor bonuscheck = new Deductor(10, "Bonuscheck"); // 10 kr
        List<Deductor> deductors = List.of(bonuscheck);

        Customer customer = createCustomerJohnSmith();
        customer.setMembership(new Membership());

        Transaction t = new Transaction(items, deductors, customer);

        t.applyDeductors();

        assertEquals(10.0, t.getTotalPrice(), 0.001);

        assertEquals(0, customer.getBonusPoints(), "Bonuspoints should subtract full deductor amount");
    }
}

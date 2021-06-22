package org.tdd.eshop.cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {

    private CartItem item;

    @BeforeEach
    void setup() {
        item = new Product("test", 99.95, 19);
    }

    @Test
    void whenPriceWithoutTax_thenPriceOk() {
        assertEquals(83.99, item.getNetPrice());
    }

    @Test
    void whenPriceWithTax_thenTaxAmountOk() {
        assertEquals(15.96, item.getTaxAmount());
    }

}
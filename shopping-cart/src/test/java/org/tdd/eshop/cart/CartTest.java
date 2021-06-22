package org.tdd.eshop.cart;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    @Test
    @Order(100)
    void whenAddItemToCart_thenOk() {
        Cart cart1 = new Cart(1);
        Product genuTrain = new Product("GenuTrain Kniebandage", 99.95, 19);
        Product runSocks = new Product("Run Perfomance Socks", 29.98, 19);
        cart1.addToCart(genuTrain);
        cart1.addToCart(runSocks);
        assertEquals(2, cart1.getContent().size());
    }

    @Test
    void whenAddItemTwice_thenItemInCartOnce() {
        Cart cart1 = new Cart(1);
        Product genuTrain = new Product("GenuTrain Kniebandage", 99.95, 19);
        cart1.addToCart(genuTrain);
        cart1.addToCart(genuTrain);
        assertEquals(1, cart1.getContent().size());
    }

    @Test
    @Order(101)
    void whenRemoveItemFromCart_thenOk() {
        Cart cart1 = new Cart(1);
        Product genuTrain = new Product("GenuTrain Kniebandage", 99.95, 19);
        Product runSocks = new Product("Run Perfomance Socks", 29.98, 19);
        cart1.addToCart(genuTrain);
        cart1.addToCart(runSocks);
        cart1.removeFromCart(genuTrain);
        assertEquals(1, cart1.getContent().size());
        assertFalse(cart1.getContent().contains(genuTrain));
        assertTrue(cart1.getContent().contains(runSocks));
    }
}

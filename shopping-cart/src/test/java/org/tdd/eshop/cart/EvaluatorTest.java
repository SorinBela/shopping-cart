package org.tdd.eshop.cart;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvaluatorTest {

    @Test
    void givenItems_whenCostOfAllItems_thenCostOfAllItems_Ok() {
        Cart cart = new Cart(1);
        cart.addToCart(new Product("GenuTrain Kniebandage", 99.95, 19));
        cart.addToCart(new Product("Run Performance Socks", 29.98, 19));
        assertEquals(129.93, cart.evaluator().getCostOfAllItems());
    }

    @Test
    void givenItems_whenTaxAmount_thenTotalTaxAmount_Ok() {
        Cart cart = new Cart(1);
        cart.addToCart(new Product("GenuTrain Kniebandage", 99.95, 19));
        cart.addToCart(new Product("Run Performance Socks", 29.98, 19));
        assertEquals(20.75, cart.evaluator().getTaxAmount());
    }

    @Test
    void givenItems_whenTaxAmountSeven_thenTotalTaxAmount_Ok() {
        Cart cart = new Cart(1);
        Product book = new Product("Book of Clean Code", 22.95, 7);
        cart.addToCart(book);
        assertEquals(21.45, cart.evaluator().getNetCostOfAllItems());
        assertEquals(1.50, cart.evaluator().getTaxAmount());
    }

    @Test
    void whenAddingItems_thenTotalAmountCorrect_Ok() {
        Cart cart = new Cart(1);
        assertEquals(0.00, cart.evaluator().getCostOfAllItems());
        cart.addToCart(new Product("GenuTrain Kniebandage", 99.95, 19));
        assertEquals(99.95, cart.evaluator().getCostOfAllItems());
        cart.addToCart(new Product("Run Performance Socks", 29.98, 19));
        assertEquals(129.93, cart.evaluator().getCostOfAllItems());
    }

    @Test
    void whenTotalPriceLessThanFifty_thenShippingCostApplied_Ok() {
        Cart cart = new Cart(1);
        cart.addToCart(new Product("Run Performance Socks", 29.98, 19));
        assertEquals(3.95, cart.evaluator().getShippingCost());
    }

    @Test
    void whenTotalPriceGreaterThanFifty_thenNoShippingCostsApplied_Ok() {
        Cart cart = new Cart(1);
        cart.addToCart(new Product("GenuTrain Kniebandage", 99.95, 19));
        assertEquals(0.00, cart.evaluator().getShippingCost());
    }

    @Test
    void whenCartModified_thenShippingCostModified_Ok() {
        Cart cart = new Cart(1);
        Product toBeRemoved = new Product("GenuTrain Kniebandage", 99.95, 19);
        cart.addToCart(toBeRemoved);
        cart.addToCart(new Product("Run Performance Socks", 29.98, 19));
        assertEquals(0.00, cart.evaluator().getShippingCost());
        cart.removeFromCart(toBeRemoved);
        assertEquals(3.95, cart.evaluator().getShippingCost());
    }
}

/*
1. Rabatt
2. MWst
 */
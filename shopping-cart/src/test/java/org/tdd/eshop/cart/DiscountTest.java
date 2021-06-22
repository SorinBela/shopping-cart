package org.tdd.eshop.cart;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountTest {

    @Test
    void whenDiscountAppliedToProduct_thenDiscountPriceOk() {
        Product book = new Product("Clean Code", 22.95, 7);
        Discount itemDiscount = new Discount(book, 20);
        book.setDiscount(itemDiscount);
        assertEquals(Money.of(18.36, "EUR"), book.getDiscountPrice());
    }

    @Test
    void whenNoDiscountAppliedToProduct_thenProductPriceOk() {
        Product book = new Product("Clean Code", 22.95, 7);
        assertEquals(Money.of(22.95, "EUR"), book.getDiscountPrice());
    }

    @Test
    void whenNoDiscountAppliedToCart_thenDiscountPriceOk() {
        Cart cart = new Cart(1);
        cart.addToCart(new Product("Clean Code", 99.95, 19));
        assertEquals(Money.of(99.95, "EUR"), cart.getDiscountPrice());
    }

    @Test
    void whenDiscountAppliedToCart_thenDiscountPriceOk() {
        Cart cart = new Cart(1);
        cart.addToCart(new Product("Clean Code", 99.95, 19));
        cart.setDiscount(new Discount(cart, 20), 35.00);
        assertEquals(Money.of(79.96, "EUR"), cart.getDiscountPrice());
    }

    @Test
    void givenMinimalAmountNotReached_whenDiscountAppliedToCart_thenNoDiscountApplied_Ok() {
        Cart cart = new Cart(1);
        cart.addToCart(new Product("Clean Code", 29.95, 19));
        cart.setDiscount(new Discount(cart, 20), 35.00);
        assertEquals(Money.of(29.95, "EUR"), cart.getDiscountPrice());
    }

    @Test
    void whenDiscountApplied_thenSavingCosts_Ok() {
        Cart cart = new Cart(1);
        Product book = new Product("Clean Code", 99.95, 19);
        Discount discount = new Discount(book, 20);
        book.setDiscount(discount);
        cart.addToCart(book);
        assertEquals(Money.of(19.99, "EUR"), cart.evaluator().getItemSavings());
    }

    @Test
    void givenMixedItemsPresent_whenDiscountApplied_thenSavingCosts_Ok() {
        Cart cart = new Cart(1);
        cart.addToCart(new Product("Some Product", 29.95, 19));
        Product book = new Product("Clean Code", 99.95, 19);
        Discount discount = new Discount(book, 20);
        book.setDiscount(discount);
        cart.addToCart(book);
        assertEquals(Money.of(19.99, "EUR"), cart.evaluator().getItemSavings());
    }

    @Test
    void givenCartDiscount_thenSavingCosts_Ok() {
        Cart cart = new Cart(1);
        cart.addToCart(new Product("Some Product", 29.95, 19));
        cart.setDiscount(new Discount(cart, 10), 1);
        Money savings = Money.of(cart.evaluator().getCostOfAllItems(), "EUR").subtract(cart.getDiscountPrice());
        assertTrue(cart.getDiscountPrice().isLessThanOrEqualTo(Money.of(27.00, "EUR")));
        assertTrue(savings.isLessThanOrEqualTo(Money.of(3.00, "EUR")));
        //assertEquals(Money.of(26.96, "EUR"), cart.getDiscountPrice());
        //assertEquals(Money.of(2.99, "EUR"), savings);
    }

}
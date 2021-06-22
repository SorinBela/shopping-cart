package org.tdd.eshop.cart;

import lombok.Getter;
import org.javamoney.moneta.Money;

import java.util.ArrayList;
import java.util.List;

public class Cart implements Discountable {

    /**
     * Implikationen einer Liste:
     *   - Duplikate können eingefügt werden.
     *   - equals() und hashCode() sollte für Produkte / Items sinnvoll implementiert sein.
     *      - UUID verfügbar? Artikel vs Produkt? etc.
     *
     */
    private List<CartItem> cartItemList = new ArrayList<>();
    int id;

    /**
     * Auslagern aller relevanten Funktionen in ein extra Objekt, um ggf. Änderungen
     * in Use-Cases angehen zu können, ohne den Container anfassen zu müssen.
     */
    private Evaluator evaluator;

    /**
     * Discount-Objekt, das den Discount für den Warenkorb insgesamt angibt.
     * Wechselwirkungen mit Discounts für Produkte / Items sind noch nicht abgesichert.
     */
    @Getter
    private Discount discount;
    double minimalCost = 0;
    
    public Cart(int id) {
        this.id = id;
        this.evaluator = new Evaluator(this);
        this.discount = new Discount(this);
    }

    public void addToCart(CartItem item) {
        if(!cartItemList.contains(item)) { // Duplikate vermeiden.
            cartItemList.add(item);
        }
    }

    public void removeFromCart(CartItem item) {
        cartItemList.remove(item);
    }

    public Evaluator evaluator() {
        return this.evaluator;
    }

    /**
     * Der Discount für einen {@link Cart} ist mit einem Minimal-Warenwert versehen.
     * Ich würde lieber einen generellen {@code Constraint}-Weg wählen, um ein breiteres
     * Angebot an Angeboten abbilden zu können.
     *
     * @param discount - der anzuwendende Discount.
     * @param minimalValueOfItemsInCart - der Minimalwert.
     */
    public void setDiscount(Discount discount, double minimalValueOfItemsInCart) {
        this.discount = discount;
        this.minimalCost = minimalValueOfItemsInCart;
    }

    public List<CartItem> getContent() {
        return this.cartItemList;
    }

    @Override
    public String toString() {
        StringBuilder cartDescription = new StringBuilder();
        cartDescription.append(String.format("Warenkorb %d:%n", id));
        for (CartItem cartItem : cartItemList) {
            cartDescription.append(cartItem);
        }
        return cartDescription.toString();
    }

    @Override
    public Money getDiscountPrice() {
        Money totalPrice = Money.of(this.evaluator().getCostOfAllItems(), "EUR");
        if(DiscountType.TOTAL == this.getDiscount().getDiscountType()
                && this.minimalCost < totalPrice.getNumber().doubleValueExact()
        ) {
            double percentage = 1 - this.getDiscount().getDiscountValue() / 100.0;
            return totalPrice.multiply(percentage);
        } else {
            return totalPrice;
        }
    }
}

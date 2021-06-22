package org.tdd.eshop.cart;

import org.javamoney.moneta.Money;

/**
 * Extra-Klasse für die Berechnung verschiedener Warenkorb-Größen. Derzeit werden Preise
 * leider über {@code double} verwaltet und ohne Caching.
 *      - Caching: Speichern des letzten Wertes und Rückgabe, sofern der Inhalt von {@code shoppingCart}
 *          nicht mittels add() oder remove() verändert wurde.
 *      - double: Java primitive Rundungsfehler. Besser wäre BigDecimal geeignet + ein Objekt, das die
 *          Preis-Formatierung, z.B. auf 2 Stellen handhabt.
 */
public class Evaluator {

    private final Cart shoppingCart;

    private Evaluator() {
        this.shoppingCart = null;
        throw new IllegalArgumentException();
    }

    public Evaluator(Cart cart) {
        this.shoppingCart = cart;
    }

    public double getNetCostOfAllItems() {
        double cost=0;
        for(CartItem item : this.shoppingCart.getContent()) {
            cost += item.getNetPrice();
        }
        return cost;
    }

    public double getCostOfAllItems() {
        double cost = 0;

        for(CartItem item : this.shoppingCart.getContent()) {
            cost += item.getGrossPriceAmount();
        }

        return cost;
    }

    public double getTaxAmount() {
        double newTaxAmount = 0;

        for(CartItem item : this.shoppingCart.getContent()) {
            newTaxAmount += item.getTaxAmount();
        }

        return newTaxAmount;
    }

    public double getShippingCost() {
        if(this.getCostOfAllItems() <= 50.00) {
            return 3.95;
        } else {
            return 0.00;
        }
    }

    public double getTotalCost() {
        return this.getCostOfAllItems() + this.getShippingCost() - this.getItemSavings().getNumber().doubleValueExact();
    }

    public Money getItemSavings() {
        Money savings = Money.of(0.0, "EUR");
        for(CartItem item : this.shoppingCart.getContent()) {
            savings = savings
                    .add(item.getGrossPrice().subtract(item.getDiscountPrice()));
        }
        return savings;
    }

}

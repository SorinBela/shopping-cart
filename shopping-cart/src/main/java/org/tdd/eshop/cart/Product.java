package org.tdd.eshop.cart;

import lombok.EqualsAndHashCode;
import org.javamoney.moneta.Money;

@EqualsAndHashCode(callSuper = true)
public class Product extends CartItem {

    public Product(String articleName, double price, double tax) {
        this(articleName, Money.of(price, "EUR"), tax);
    }

    public Product(String articleName, Money priceAndCurrency, double tax) {
        super(articleName, priceAndCurrency, tax);
    }

    @Override
    public String toString() {
        return String.format("%25s:  %.2f€ (%s%% MwSt.)%n", articleName, grossPrice.getNumber().doubleValueExact(), tax);
    }

    @Override
    public Money getDiscountPrice() {
        Money actualPriceAfterDiscount = this.getGrossPrice();
        Discount itemDiscount = this.getDiscount();
        switch(itemDiscount.getDiscountType()) {
            case ITEM:
                double percent = 1.0 - (itemDiscount.getDiscountValue() / 100.0);
                actualPriceAfterDiscount = actualPriceAfterDiscount.multiply(percent);
                break;
            default: break;
        }
        return actualPriceAfterDiscount;
    }
}

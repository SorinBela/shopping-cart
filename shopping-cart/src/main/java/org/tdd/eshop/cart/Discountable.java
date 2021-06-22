package org.tdd.eshop.cart;

import org.javamoney.moneta.Money;

public interface Discountable {

    Money getDiscountPrice();

}
package org.tdd.eshop.cart;

import lombok.Getter;

@Getter
public class Discount {

    private Discountable item;
    private DiscountType discountType;
    private double discountValue;

    public Discount(Discountable item) {
        this.item = item;
        this.discountType = DiscountType.NONE;
        this.discountValue = 0;
    }

    public Discount(CartItem item, double value) {
        this.item = item;
        this.discountType = DiscountType.ITEM;
        this.discountValue = value;
    }

    public Discount(Cart cart, double valueOff) {
        this.item = cart;
        this.discountType = DiscountType.TOTAL;
        this.discountValue = valueOff;
    }

}

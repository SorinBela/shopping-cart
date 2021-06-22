package org.tdd.eshop.cart;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.javamoney.moneta.Money;

/**
 * Abstrakte CartItem-Klasse für Objekte, die in einen Warenkorb eingefügt werden und verwaltet
 * werden können. {@code abstract}, weil ein generisches Item nicht valide ist (für diesen Fall),
 * sondern durch Ableitungen wie etwa {@link Product} näher spezifiziert werden müssen.
 */
@Data
@EqualsAndHashCode()
public abstract class CartItem implements Discountable {
    
    protected String articleName;
    protected Money grossPrice;
    protected double tax;

    protected Discount discount;

    private CartItem() {}

    protected CartItem(String articleName, double price, double tax) {
        this(articleName, Money.of(price, "EUR"), tax);
    }

    protected CartItem(String articleName, Money grossPrice, double tax) {
        this.articleName = articleName;
        this.grossPrice = grossPrice;
        this.tax = tax;
        this.discount = new Discount(this);
    }

    public Discount getDiscount() {
        return this.discount;
    }

    public void clearDiscount() {
        this.discount = new Discount(this);
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    /**
     * Dies gibt den Netto-Preis eines Items zurück. Allerdings sollte statt {@code double}
     * lieber eine API oder {@link java.math.BigDecimal} verwendet werden, einfach um Gleitkomma-
     * Fehler durch den Primitiv-Typ zu verhindern.
     *
     * Wäre also der erste und größte Refaktor-Faktor.
     *
     * @return the Price as double
     */
    public double getNetPrice() {
        double price = this.grossPrice.getNumber().doubleValueExact();
        double taxModifier = 1 + (this.tax / 100.0);
        price = price / taxModifier;
        return Math.round(price * 100.0) / 100.0; // zu früh!
    }

    public double getTaxAmount() {
        double priceWithoutTax = this.getNetPrice();
        return Math.round((priceWithoutTax * (this.tax/100.0))*100.0) / 100.0;
    }

    public double getGrossPriceAmount() {
        return this.getGrossPrice().getNumber().doubleValueExact();
    }

}

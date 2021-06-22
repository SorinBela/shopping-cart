package org.tdd.eshop.cart;

/**
 * Convenience-Klasse um Output flexibel zu bauen.
 */
public class CartDescriptor {

    private Cart cart;
    private StringBuilder cartDescription = new StringBuilder();

    public CartDescriptor(Cart cart) {
        this.cart = cart;
        this.cartDescription = new StringBuilder();
    }

    public CartDescriptor shippingCost() {
        this.cartDescription.append("Versandkosten:")
                .append(String.format("%18.2f€", cart.evaluator().getShippingCost())).append(System.lineSeparator());
        return this;
    }

    public CartDescriptor tax() {
        cartDescription.append("Davon MwSt:")
                .append(String.format("%21.2f€", cart.evaluator().getTaxAmount())).append(System.lineSeparator());
        return this;
    }

    public CartDescriptor content() {
        cartDescription.append(String.format("Warenkorb %d:%n", cart.id));
        for (CartItem cartItem : cart.getContent()) {
            cartDescription.append(cartItem);
        }
        return this;
    }

    public CartDescriptor itemCost() {
        cartDescription.append("Bruttopreis:")
                .append(String.format("%21.2f€", cart.evaluator().getCostOfAllItems())).append(System.lineSeparator());
        return this;
    }

    public CartDescriptor total() {
        cartDescription.append("Zu zahlender Betrag:")
                .append(String.format("%13.2f€", cart.evaluator().getTotalCost())).append(System.lineSeparator());

        return this;
    }

    public CartDescriptor itemSavings() {
        cartDescription.append("Gesparter Betrag durch Produkt-Rabatt:")
                .append(String.format("%13.2f€",
                        cart.evaluator().getItemSavings().getNumber().doubleValueExact()))
                .append(System.lineSeparator());
        return this;
    }

    public String toString() {
        return this.print();
    }

    public String print() {
        String str = this.cartDescription.toString();
        this.cartDescription.setLength(0);
        return str;
    }
}

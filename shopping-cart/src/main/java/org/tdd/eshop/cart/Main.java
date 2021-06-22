package org.tdd.eshop.cart;

public class Main {

    public static void main(String[] args) {

        Cart cart1 = new Cart(1);
        Product genuTrain = new Product("GenuTrain Kniebandage", 99.95, 19);
        Product runSocks = new Product("Run Perfomance Socks", 29.98, 19);

        cart1.addToCart(genuTrain);
        cart1.addToCart(runSocks);

        Cart cart2 = new Cart(2);
        Product book = new Product("Clean Code", 22.95, 7);
        Product pralines = new Product("Pralinen", 9.95, 19);
        cart2.addToCart(book);
        cart2.addToCart(pralines);

        printAllAssignments(cart1, genuTrain);

        CartDescriptor descriptor = new CartDescriptor(cart2);
        System.out.println(descriptor.content().itemCost().tax().itemSavings().shippingCost().total().print());

    }

    public static void printAllAssignments(Cart cart, Product mutable) {
        assignment_TotalCostOfCart(cart);
        mutable.setDiscount(new Discount(mutable, 10));
        assignment_DiscountOnItem(cart);
        mutable.clearDiscount();
        assignment_ShippingCosts(cart, mutable);
    }

    public static void assignment_TotalCostOfCart(Cart cart) {

        System.out.println("1.   Berechnen Sie die Gesamtkosten des Warenkorb 1," +
                " sowie die den Anteil der Mehrwertsteuer ohne Berücksichtigung der" +
                " Rabattaktion und der Versandkosten.");


        CartDescriptor output = new CartDescriptor(cart);
        System.out.println(output.itemCost().tax().print());
    }

    public static void assignment_DiscountOnItem(Cart cart) {

        System.out.println("2.   Wenden Sie die Rabattaktion für den Warenkorb 1 an. Bestimmen Sie erneut " +
                "den Anteil der Mehrwertsteuer und die Gesamtkosten, sowie die erzielte Ersparnis in Euro.");

        CartDescriptor output = new CartDescriptor(cart);
        System.out.println(output.itemCost().tax().itemSavings().total().print());
    }

    public static void assignment_ShippingCosts(Cart cart, Product removable) {
        System.out.println("3.   Berücksichtigen Sie nun bei der Berechnung der Gesamtkosten die ausgewiesenen " +
                "Versandkosten und zeigen Sie, dass bei Herausnahme des Artikels \"GenuTrain Kniebandage\" aus " +
                "dem Warenkorb diese bei den Gesamtkosten beachtet werden.");

        CartDescriptor output = new CartDescriptor(cart);
        System.out.println(output.itemCost().tax().shippingCost().print());

        System.out.println("Entferne \"" + removable.getArticleName() +"\"");

        cart.removeFromCart(removable);
        System.out.println(output.itemCost().tax().shippingCost().print());

    }
}

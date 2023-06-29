package my.component.services.impl;

import my.component.model.Gift;
import my.component.services.MyBean;
import my.component.services.PaymentSystem;

public class CashPaymentSystem extends MyBean implements PaymentSystem {
    @Override
    public void pay(Gift gift) {
        System.out.println(String.format("Pay %.2f for %s by CASH", gift.getPrice(),gift.getName()));
    }
}

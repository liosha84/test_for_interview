package my.component.services;

import my.component.annotations.Component;
import my.component.model.Gift;
import my.component.model.Person;


import java.lang.reflect.InvocationTargetException;

public class GiftPresenter extends MyBean{

    @Component
    private GiftChooserHelper giftChooserHelper;
    @Component
    private PaymentSystem paymentSystem;
    @Component
    private DeliverySystem deliverySystem;

    public GiftPresenter() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    }

    public void present(Person person){

        Gift gift = giftChooserHelper.choose(person);

        System.out.println(String.format("Gift has been chosen: %s",gift.getName()));
        paymentSystem.pay(gift);
        deliverySystem.deliver(gift, person);

    }
}

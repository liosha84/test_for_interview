package my.component.services.impl;

import my.component.model.Gift;
import my.component.model.Person;
import my.component.services.DeliverySystem;
import my.component.services.MyBean;

public class PostDeliverySystem extends MyBean implements DeliverySystem {
    @Override
    public void deliver(Gift gift, Person person) {
        System.out.println(String.format("Delivery to %s", person.getName()));
    }
}

package my.component.services;

import my.component.model.Gift;
import my.component.model.Person;

public interface DeliverySystem {
    void deliver(Gift gift, Person person);
}

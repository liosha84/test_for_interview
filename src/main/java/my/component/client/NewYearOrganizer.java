package my.component.client;

import my.component.annotations.Component;
import my.component.model.Person;
import my.component.services.GiftPresenter;
import my.component.services.MyBean;

import java.lang.reflect.InvocationTargetException;

public class NewYearOrganizer extends MyBean {

    @Component
    private GiftPresenter giftPresenter;

    public NewYearOrganizer() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    }

    public void prepareToCelebration(){
        Person person = new Person("Jack");
        giftPresenter.present(person);
    }
}

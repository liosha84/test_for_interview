package my.component.services.impl;

import my.component.annotations.Autowire;
import my.component.annotations.Component;
import my.component.annotations.Qualifier;
import my.component.model.Gift;
import my.component.model.Person;
import my.component.services.GiftChooserHelper;
import my.component.services.Recommendator;
import my.component.services.MyBean;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;


public class SmartGiftChooserHelper extends MyBean implements GiftChooserHelper {

    @Component
    private Recommendator recommendator;

    // Load payment system from JavaConfiguration class
    public SmartGiftChooserHelper() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    }

    // This constructor has been added for example of using @Qualifier
    @Autowire
    public SmartGiftChooserHelper(@Qualifier(GoofyRecommendator.class) Recommendator recommendator) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.recommendator = recommendator;
    }

    @Override
    public Gift choose(Person person) {
        recommendator.recommend();
        Gift gift = new Gift("Sun watch", new BigDecimal(100));

        System.out.println(String.format("Choose the gift %s with price %.2f", gift.getName(),gift.getPrice()));
        return gift;
    }

}

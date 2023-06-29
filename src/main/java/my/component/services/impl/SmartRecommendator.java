package my.component.services.impl;

import my.component.services.Recommendator;
import my.component.services.MyBean;

public class SmartRecommendator extends MyBean implements Recommendator {

    @Override
    public void recommend() {
        System.out.println("Smart recommendation.");
    }

}

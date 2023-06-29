package my.container;

import my.component.client.NewYearOrganizer;
import my.container.context.ApplicationContext;
import my.container.factories.BeanFactory;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public ApplicationContext run(){
        ApplicationContext applicationContext = new ApplicationContext();
        BeanFactory beanFactory = new BeanFactory(applicationContext);
        applicationContext.setBeanFactory(beanFactory);

        return applicationContext;
    }

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        Main application = new Main();
        ApplicationContext context = application.run();

        NewYearOrganizer newYearOrganizer = context.getBean(NewYearOrganizer.class);
        newYearOrganizer.prepareToCelebration();

    }

}
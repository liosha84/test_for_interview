package my.container.factories;


import my.component.annotations.Autowire;
import my.component.annotations.Component;
import my.component.annotations.Qualifier;
import my.container.config.Configuration;
import my.container.config.JavaConfiguration;
import my.container.configurators.BeanConfigurator;
import my.container.configurators.JavaBeanConfigurator;
import my.container.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Collectors;


public class BeanFactory {

    private final Configuration configuration;
    private final BeanConfigurator beanConfigurator;
    private ApplicationContext applicationContext;

    public BeanFactory(ApplicationContext applicationContext){
        this.configuration = new JavaConfiguration();
        this.beanConfigurator = new JavaBeanConfigurator(configuration.getPackageToScan(), configuration.getInterfaceToImplementations());
        this.applicationContext = applicationContext;
    }

    public <T> T getBean(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<? extends T> implementationClass = clazz;
        if(implementationClass.isInterface()){
            implementationClass = beanConfigurator.getImplementationClass(implementationClass);

        }
        //T bean = implementationClass.getDeclaredConstructor().newInstance();

        T bean = null;

        for(Constructor constructor: Arrays.stream(implementationClass.getDeclaredConstructors()).filter(constructor -> constructor.isAnnotationPresent(Autowire.class)).collect(Collectors.toList())){

            Class param = null;
            Annotation[][] annotations = constructor.getParameterAnnotations();
            for(Annotation[] annotation1 : annotations){
                for(Annotation annotation : annotation1){
                    if(annotation instanceof Qualifier){
                        Qualifier qualifier = (Qualifier) annotation;
                        param = qualifier.value();
                    }
                }
            }
            if(param == null)
                throw new NullPointerException();
            // Invoke applicationContext.getBean(param) because use @Autowire constructor instead of JavaConfiguration
            // if we do not use @Autowire constructor bean implementation get from JavaConfiguration
            bean = (T)constructor.newInstance(this.applicationContext.getBean(param));
        }

        if(bean == null)
            bean = implementationClass.getDeclaredConstructor().newInstance();

        for(Field field : Arrays.stream(implementationClass.getDeclaredFields()).filter(field -> field.isAnnotationPresent(Component.class)).collect(Collectors.toList())){
            field.setAccessible(true);
            //if(field.get(bean) == null)
                field.set(bean, applicationContext.getBean(field.getType()));
        }
        return bean;
    }

    public BeanConfigurator getBeanConfigurator(){
        return beanConfigurator;
    }
}

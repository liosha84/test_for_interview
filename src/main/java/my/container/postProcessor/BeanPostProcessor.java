package my.container.postProcessor;

import java.lang.reflect.InvocationTargetException;

public interface BeanPostProcessor {

    void process(Object bean) throws InvocationTargetException, IllegalAccessException;
}

import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ClassLoaderTest {

    public static final String JARS_DIR = System.getProperty("user.dir") + "\\build\\libs";


    private static URL[] getContainerURL() throws MalformedURLException {
        File pluginsDir = new File(JARS_DIR);
        URL[] retVal = null;
        for (File jar : pluginsDir.listFiles()){
            retVal = new URL[]{jar.toURL()};
            continue;
        }
        return retVal;
    }

    public ClassLoaderTest() throws MalformedURLException {
    }


    @Test
    public void initContainerTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException, MalformedURLException {
        URLClassLoader urlClassLoader = new URLClassLoader(getContainerURL());
        Class<?> classManuallyLoaded = urlClassLoader.loadClass("my.container.context.ApplicationContext");

        Object container = classManuallyLoaded.newInstance();


        assertTrue( container != null,"Container has not been initialized.");
    }

    @Test
    public void initBeanFactoryTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, MalformedURLException {
        URLClassLoader urlClassLoader = new URLClassLoader(getContainerURL());
        Class<?> containerManuallyLoaded = urlClassLoader.loadClass("my.container.context.ApplicationContext");

        Object container = containerManuallyLoaded.newInstance();

        Class<?> beanFactoryManuallyLoaded = urlClassLoader.loadClass("my.container.factories.BeanFactory");
        Constructor constructor = beanFactoryManuallyLoaded.getDeclaredConstructor(containerManuallyLoaded);

        Object beanFactory = constructor.newInstance(container);

        assertTrue( beanFactory != null,"BeanFactory has not been initialized.");
    }

    @Test
    public void containerSetBeanFactoryTest() throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, MalformedURLException {
        URLClassLoader urlClassLoader = new URLClassLoader(getContainerURL());
        Class<?> containerManuallyLoaded = urlClassLoader.loadClass("my.container.context.ApplicationContext");
        Object container = containerManuallyLoaded.newInstance();

        Class<?> beanFactoryManuallyLoaded = urlClassLoader.loadClass("my.container.factories.BeanFactory");
        Constructor constructor = beanFactoryManuallyLoaded.getDeclaredConstructor(containerManuallyLoaded);

        Object beanFactory = constructor.newInstance(container);

        Method methodToString = containerManuallyLoaded.getMethod("setBeanFactory", beanFactoryManuallyLoaded);

        Object obj = methodToString.invoke(container, beanFactory);

        assertEquals(null,obj);
    }

    @Test
    public void containerGetBeanTest() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, MalformedURLException {
        URLClassLoader urlClassLoader = new URLClassLoader(getContainerURL());

        Class<?> containerManuallyLoaded = urlClassLoader.loadClass("my.container.context.ApplicationContext");

        Object container = containerManuallyLoaded.newInstance();

        Class<?> beanFactoryManuallyLoaded = urlClassLoader.loadClass("my.container.factories.BeanFactory");
        Constructor constructor = beanFactoryManuallyLoaded.getDeclaredConstructor(containerManuallyLoaded);

        Object beanFactory = constructor.newInstance(container);

        Method setBeanFactory = containerManuallyLoaded.getMethod("setBeanFactory", beanFactoryManuallyLoaded);

        setBeanFactory.invoke(container, beanFactory);

        Class<?> newYerOrganizer = urlClassLoader.loadClass("my.component.client.NewYearOrganizer");

        Set<Method> methodList = Arrays.stream(containerManuallyLoaded.getDeclaredMethods()).filter(method -> method.getName().equals("getBean")).collect(Collectors.toSet());
        Method getBean = (Method) methodList.toArray()[0];

        Object obj = getBean.invoke(container, newYerOrganizer);

        assertTrue( obj != null,"Component NewYerOrganizer has not been initialized.");
    }
}

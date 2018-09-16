import exceptions.MyException;

import java.util.ArrayList;
import java.util.List;

public interface IoCContext extends AutoCloseable{

    void registerBean(Class<?> beanClazz);
    <T> T getBean(Class<T> resolveClazz) throws MyException;
    <T> void registerBean(Class<? super T> resolveClazz, Class<T> beanClazz);
}

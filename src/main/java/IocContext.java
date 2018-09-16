import exceptions.MyException;

import java.util.ArrayList;
import java.util.List;

public interface IocContext extends AutoCloseable{
    List<String> fieldInitializations = new ArrayList<>();
    List<String> closeMethods = new ArrayList<>();

    void registerBean(Class<?> beanClazz);
    <T> T getBean(Class<T> resolveClazz) throws MyException;
    <T> void registerBean(Class<? super T> resolveClazz, Class<T> beanClazz);
}

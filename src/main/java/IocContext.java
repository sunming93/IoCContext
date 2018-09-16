import exceptions.MyException;

public interface IocContext extends AutoCloseable{
    void registerBean(Class<?> beanClazz);
    <T> T getBean(Class<T> resolveClazz) throws MyException;
    <T> void registerBean(Class<? super T> resolveClazz, Class<T> beanClazz);
}

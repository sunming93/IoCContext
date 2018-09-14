public interface IocContext {
    void registerBean(Class<?> beanClazz);
    <T> T getBean(Class<T> resolveClazz);
}

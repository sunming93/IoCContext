public class IocContextImpl implements IocContext {
    private Class clazz;

    @Override
    public void registerBean(Class<?> beanClazz) {
        clazz = beanClazz;
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) {
        return clazz.newInstance();
    }
}

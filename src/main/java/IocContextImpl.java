public class IocContextImpl implements IocContext {
    private Class clazz;

    @Override
    public void registerBean(Class<?> beanClazz) {
        clazz = beanClazz;
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) {
        if(resolveClazz == null){
            throw new IllegalArgumentException("beanClazz is mandatory.");
        }

        try {
            return resolveClazz.newInstance();
        }catch (Exception e){
            throw new IllegalArgumentException(resolveClazz.getName()+" is abstract.");
        }
    }
}

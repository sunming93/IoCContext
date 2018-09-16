import exceptions.MyException;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class IocContextImpl implements IocContext {
    private Map<Class, Class> clazz = new HashMap<>();
    private boolean forbidRegister;

    @Override
    public void registerBean(Class<?> beanClazz) {
//        registerBean(beanClazz, beanClazz);
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) throws MyException {
        if(resolveClazz == null){
            throw new IllegalArgumentException("resolveClass is null.");
        }

        if (!clazz.containsKey(resolveClazz)){
            throw new IllegalStateException("the class has not been registered.");
        }

        forbidRegister = true;

        try {
            return (T)clazz.get(resolveClazz).newInstance();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> void registerBean(Class<? super T> resolveClazz, Class<T> beanClazz) {
        if(forbidRegister){
            throw new IllegalStateException("cannot be registered.");
        }

        if(beanClazz == null){
            throw new IllegalArgumentException("beanClazz is mandatory.");
        }

        if(Modifier.isAbstract(beanClazz.getModifiers())){
            throw new IllegalArgumentException(beanClazz.getName()+" is abstract.");
        }

        try {
            beanClazz.getConstructor(new Class[0]);
        }catch (NoSuchMethodException e){
            throw new IllegalArgumentException(beanClazz.getName()+" has no default constructor.");
        }

        if(clazz.containsKey(beanClazz)){
            return;
        }

        clazz.put(resolveClazz, beanClazz);
    }
}

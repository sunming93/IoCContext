import exceptions.MyException;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class IocContextImpl implements IocContext {
    private List<Class> clazz = new ArrayList<>();
    private boolean forbidRegister;

    @Override
    public void registerBean(Class<?> beanClazz) {
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

        if(clazz.contains(beanClazz)){
            return;
        }

        clazz.add(beanClazz);
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) throws MyException {
        if(resolveClazz == null){
            throw new IllegalArgumentException("resolveClass is null.");
        }

        if (!clazz.contains(resolveClazz)){
            throw new IllegalStateException("the class has not been registered.");
        }

        forbidRegister = true;

        try {
            return resolveClazz.newInstance();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> void registerBean(Class<? super T> resolveClazz, Class<T> beanClazz) {
    }
}

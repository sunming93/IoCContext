import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class IocContextImpl implements IocContext {
    private List<Class> clazz = new ArrayList<>();

    @Override
    public void registerBean(Class<?> beanClazz) {
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
    public <T> T getBean(Class<T> resolveClazz) {
        return null;
    }
}

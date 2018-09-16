import beans.CreateOnTheFly;
import exceptions.MyException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IocContextImpl implements IocContext {
    private Map<Class, Class> clazz = new HashMap<>();
    private boolean forbidRegister;
    private List<Object> resolvedClazzes = new ArrayList<>();

    @Override
    public void registerBean(Class<?> beanClazz) {
        registerBeanHelper(beanClazz);
    }

    public <T> void registerBeanHelper(Class<T> beanClazz){
        registerBean(beanClazz, beanClazz);
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

        T newInstance = null;

        try {
            newInstance = (T) clazz.get(resolveClazz).newInstance();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }

        List<Class> inheritances = new ArrayList<>();
        Class currentClass = resolveClazz;
        inheritances.add(currentClass);
        while ((currentClass = currentClass.getSuperclass()) != null){
            inheritances.add(currentClass);
        }

        for (int i = inheritances.size()-2; i >= 0 ; i--) {
            Class tempClass = inheritances.get(i);
            Field[] fields = tempClass.getDeclaredFields();

            for(Field field : fields){
                Annotation[] annotations = field.getAnnotations();

                for(Annotation annotation : annotations){
                    if(annotation.annotationType() == CreateOnTheFly.class){
                        Object fieldInstance = null;
                        Class<?> type = field.getType();
                        if(clazz.get(type) == null){
                            throw new IllegalStateException("beans.MyDependency has not been registered.");
                        }else {
                            try {
                                fieldInstance = type.newInstance();
                                fieldInitializations.add("ClassName: "+tempClass.getName()+", MethodName: "+field.getName());
                            }catch (InstantiationException e){
                                e.printStackTrace();
                            }catch (IllegalAccessException e){
                                e.printStackTrace();
                            }
                        }

                        if(i == 0){
                            try {
                                Field newField = newInstance.getClass().getDeclaredField(field.getName());

                                newField.setAccessible(true);
                                newField.set(newInstance, fieldInstance);
                                newField.setAccessible(false);

                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        resolvedClazzes.add(newInstance);
        return newInstance;
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
        clazz.put(beanClazz, beanClazz);
    }

    @Override
    public void close() throws Exception {
        for (int i = resolvedClazzes.size()-1; i >=0 ; i--) {
            Object currentInstance = resolvedClazzes.get(i);

            if(currentInstance instanceof AutoCloseable){
                try {
                    ((AutoCloseable)currentInstance).close();
                    closeMethods.add(currentInstance.getClass().getName());
                }finally {
                    continue;
                }
            }
        }
    }
}

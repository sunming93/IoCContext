import beans.InvalidBean;
import beans.MyBean;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IocContextImplTest {
    @Test
    void should_throw_exception_when_create_instance_for_null() {
        IocContext context = new IocContextImpl();
        context.registerBean(MyBean.class);

        assertThrows(IllegalArgumentException.class,() -> context.getBean(null),"beanClazz is mandatory.");
    }

    @Test
    void should_throw_exception_if_instance_cannot_be_created() {
        IocContext context = new IocContextImpl();
        context.registerBean(MyBean.class);

        assertThrows(IllegalArgumentException.class,() -> context.getBean(InvalidBean.class),"beanClazz is mandatory.");
    }

    @Test
    void can_be_created_if_conditions_is_satisfied() {
        IocContext context = new IocContextImpl();
        context.registerBean(MyBean.class);
        MyBean myBeanInstance = context.getBean(MyBean.class);

        assertEquals(MyBean.class,myBeanInstance.getClass());
    }
}
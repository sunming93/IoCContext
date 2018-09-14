import beans.AbstractBean;
import beans.NoDefaultBean;
import beans.MyBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IocContextImplTest {
    private IocContext context;

    @BeforeEach
    void setUp() {
        context = new IocContextImpl();
    }

    @Test
    void should_throw_exception_when_create_instance_for_null() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> context.registerBean(null));
        assertEquals("beanClazz is mandatory.",exception.getMessage());
    }

    @Test
    void should_throw_exception_if_instance_cannot_be_created() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> context.registerBean(AbstractBean.class));
        assertEquals("beans.AbstractBean is abstract.",exception.getMessage());
    }


    @Test
    void should_throw_exception_if_register_a_class_without_default_constructor() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> context.registerBean(NoDefaultBean.class));
        assertEquals("beans.NoDefaultBean has no default constructor.",exception.getMessage());
    }

    @Test
    void should_return_directly_if_beanClazz_has_bean_registered() {
        context.registerBean(MyBean.class);
        context.registerBean(MyBean.class);
    }

    @Test
    void can_be_created_if_conditions_is_satisfied() {
        IocContext context = new IocContextImpl();
        context.registerBean(MyBean.class);
        MyBean myBeanInstance = context.getBean(MyBean.class);

        assertEquals(MyBean.class,myBeanInstance.getClass());
    }
}
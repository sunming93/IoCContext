import beans.AbstractBean;
import beans.ExceptionBean;
import beans.NoDefaultBean;
import beans.MyBean;
import exceptions.MyException;
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
    void should_return_directly_if_beanClazz_has_been_registered() {
        context.registerBean(MyBean.class);
        context.registerBean(MyBean.class);
    }

    @Test
    void should_throw_exception_if_resolveClazz_is_null() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> context.getBean(null));
        assertEquals("resolveClass is null.",exception.getMessage());
    }

    @Test
    void should_throw_exception_if_resolveClazz_has_not_been_registered() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> context.getBean(MyBean.class));
        assertEquals("the class has not been registered.",exception.getMessage());
    }

    @Test
    void should_catch_the_exception_if_resolveClass_throws_an_exception() {
        context.registerBean(ExceptionBean.class);

        Throwable exception = assertThrows(MyException.class, () -> context.getBean(ExceptionBean.class));
        assertEquals("the constructor throws an exception.",exception.getMessage());
    }
}

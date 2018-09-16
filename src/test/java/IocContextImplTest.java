import beans.*;
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
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> context.registerBean(null));
        assertEquals("beanClazz is mandatory.",exception.getMessage());
    }

    @Test
    void should_throw_exception_if_instance_cannot_be_created() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> context.registerBean(AbstractBean.class));
        assertEquals("beans.AbstractBean is abstract.",exception.getMessage());
    }


    @Test
    void should_throw_exception_if_register_a_class_without_default_constructor() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> context.registerBean(NoDefaultBean.class));
        assertEquals("beans.NoDefaultBean has no default constructor.",exception.getMessage());
    }

    @Test
    void should_return_directly_if_beanClazz_has_been_registered() {
        context.registerBean(MyBean.class);
        assertDoesNotThrow(() -> context.registerBean(MyBean.class));
    }

    @Test
    void should_throw_exception_if_resolveClazz_is_null() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> context.getBean(null));
        assertEquals("resolveClass is null.",exception.getMessage());
    }

    @Test
    void should_throw_exception_if_resolveClazz_has_not_been_registered() {
        Throwable exception = assertThrows(IllegalStateException.class,
                () -> context.getBean(MyBean.class));
        assertEquals("the class has not been registered.",exception.getMessage());
    }

    @Test
    void should_catch_the_exception_if_resolveClass_throws_an_exception() {
        context.registerBean(ExceptionBean.class);

        Throwable exception = assertThrows(MyException.class,
                () -> context.getBean(ExceptionBean.class));
        assertEquals("the constructor throws an exception.",exception.getMessage());
    }

    @Test
    void cannot_be_registered_if_getBean() throws MyException{
        context.registerBean(MyBean.class);
        context.getBean(MyBean.class);

        Throwable exception = assertThrows(IllegalStateException.class,
                () -> context.registerBean(AnotherBean.class));
        assertEquals("cannot be registered.",exception.getMessage());
    }

    @Test
    void can_be_get_if_registered() throws MyException{
        context.registerBean(MyBean.class);
        MyBean oneInstance = context.getBean(MyBean.class);

        assertEquals(MyBean.class,oneInstance.getClass());
    }

    @Test
    void can_be_get_if_registered_two_class() throws MyException{
        context.registerBean(MyBean.class);
        context.registerBean(AnotherBean.class);

        MyBean myBean = context.getBean(MyBean.class);
        AnotherBean anotherBean = context.getBean(AnotherBean.class);

        assertEquals(MyBean.class,myBean.getClass());
        assertEquals(AnotherBean.class,anotherBean.getClass());
    }

    @Test
    void should_get_different_instance_if_get_two_times() throws MyException{
        context.registerBean(MyBean.class);

        MyBean oneInstance = context.getBean(MyBean.class);
        MyBean anotherInstance = context.getBean(MyBean.class);

        assertNotSame(oneInstance,anotherInstance);
    }

}

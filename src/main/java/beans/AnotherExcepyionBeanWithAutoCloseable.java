package beans;

import exceptions.MyException;

public class AnotherExcepyionBeanWithAutoCloseable implements AutoCloseable{

    @Override
    public void close() throws Exception {
        throw new IllegalStateException("AnotherExceptionBeanWithAutoCloseable throws an exception in the close.");
    }
}

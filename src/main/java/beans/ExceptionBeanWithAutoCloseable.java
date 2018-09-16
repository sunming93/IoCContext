package beans;

import exceptions.MyException;

public class ExceptionBeanWithAutoCloseable implements AutoCloseable{
    @Override
    public void close() throws Exception {
        throw new MyException("ExceptionBeanWithAutoCloseable throws an exception in the close.");
    }
}

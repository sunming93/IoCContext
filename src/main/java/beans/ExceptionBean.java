package beans;

import exceptions.MyException;

import java.lang.reflect.MalformedParameterizedTypeException;

public class ExceptionBean {
    public ExceptionBean() throws MyException{
        throw new MyException("the constructor throws an exception.");
    }
}

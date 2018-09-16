package beans;

public class MyBean extends MyBeanBase implements MyBeanInterface{
    @CreateOnTheFly
    private MyDependency myDependency;
}

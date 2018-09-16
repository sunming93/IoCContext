package beans;

public class MyBeanGrandsonWithDependency extends MyBeanSonWithDependency {
    @CreateOnTheFly
    private MyDependency myDependencyInTheGrandson;
}

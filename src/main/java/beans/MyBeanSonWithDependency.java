package beans;

public class MyBeanSonWithDependency extends MyBeanWithDependency {

    @CreateOnTheFly
    private MyDependency myDependencyInTheSon;

    public MyDependency getMyDependencyInTheSon() {
        return myDependencyInTheSon;
    }
}

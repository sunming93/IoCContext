package beans;

public class MyBeanSonWithDependency extends MyBeanWithDependency {

    @CreateOnTheFly
    private MyDependency myDependencyInTheSon;

    public MyDependency getMyDependencyInTheSon() {
        return myDependencyInTheSon;
    }

    public void setMyDependencyInTheSon(MyDependency myDependencyInTheSon) {
        this.myDependencyInTheSon = myDependencyInTheSon;
        logger.add("Mydependency in the myBeanSon");
    }
}

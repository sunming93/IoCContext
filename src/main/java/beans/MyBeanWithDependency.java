package beans;

import java.util.ArrayList;
import java.util.List;

public class MyBeanWithDependency {
    List<String> logger = new ArrayList<>();

    @CreateOnTheFly
    private MyDependency myDependency;

    public MyDependency getMyDependency() {
        return myDependency;
    }

    public void setMyDependency(MyDependency myDependency) {
        this.myDependency = myDependency;
        logger.add("Mydependency in the myBean");
    }

    public List<String> getLogger() {
        return logger;
    }
}

package beans;

import com.sun.istack.internal.NotNull;

public class MyBeanWithDependency {

    @CreateOnTheFly
    private MyDependency myDependency;

    public MyDependency getMyDependency() {
        return myDependency;
    }

    public void setMyDependency(MyDependency myDependency) {
        this.myDependency = myDependency;
    }
}

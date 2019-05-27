import java.util.Observable;
import java.util.Observer;

public class SampleCheckInterface {

    public static void main(String[] args) {
        Class observer = Observer.class;
        Class observable = Observable.class;
        verifyInterface(observer);
        verifyInterface(observable);
    }

    static void verifyInterface(Class c) {
        String name = c.getName();
        if (c.isInterface()) {
            System.out.println(name + " is an interface.");
        } else {
            System.out.println(name + " is a class.");
        }
    }
}

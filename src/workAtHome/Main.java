package workAtHome;

import java.io.*;
import java.lang.reflect.*;
import java.util.Scanner;

interface MyInterface {
    void f();
}

class A {
    private MyInterface m;
    public void g() {
        m.f();
    }
}

class B implements MyInterface {

    @Override
    public void f() {
        System.out.println("Class B has been injected!");
    }
}
class C implements MyInterface{

    @Override
    public void f() {
        System.out.println("Class C has been injected!");
    }
}

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException, InvocationTargetException {
        String fileName = "Config.txt";
        String dataToWrite = "workAtHome.A:MyInterface:workAtHome.B";
        String dataFromConfigFile;
        String[] wordsFromFile;
        String mainClassName;
        String interfaceName;
        String injectedClassName;

        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
            FileWriter nFile = new FileWriter(fileName, false);
            nFile.write(dataToWrite);
            nFile.close();
        } else {
            System.out.println("File already exists");
        }
        FileReader fr = new FileReader(fileName);
        Scanner scan = new Scanner(fr);
        dataFromConfigFile = scan.nextLine();

        fr.close();
        wordsFromFile = dataFromConfigFile.split(":");
        mainClassName = wordsFromFile[0];
        interfaceName = wordsFromFile[1];
        injectedClassName = wordsFromFile[2];

        Class newAClass = Class.forName(mainClassName);
        Field[] fields = newAClass.getDeclaredFields();
        Field aClassFieldToInject = fields[0];

        Object newAObject = newAClass.newInstance();

        Field fieldToInject = newAClass.getDeclaredField(aClassFieldToInject.getName());
        fieldToInject.setAccessible(true);

        Class injectedClass = Class.forName(injectedClassName);
        Object newInjectedObject = injectedClass.newInstance();

        fieldToInject.set(newAObject, newInjectedObject);
        Method[] methods = newAClass.getDeclaredMethods();
        Method method = methods[0];
        method.invoke(newAObject);

    }
}

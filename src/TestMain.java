import core.tags.HTML;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

class Tag {

    protected  final  static String type = "a";

    public static void getType() throws NoSuchFieldException, IllegalAccessException {
        System.out.println("TAG");
    }


    public void print() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        System.out.println(getClass().getDeclaredField("type").get(this));
    }
}

class Tag1 extends Tag {


    protected final static String type = "b";

    public static void getType() throws NoSuchFieldException, IllegalAccessException {
        System.out.println("TAG1");
    }
}

public class TestMain {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Tag tag = new Tag();
        Tag tag1 = new Tag1();
        tag.print();
        ;
        tag1.print();
    }
}

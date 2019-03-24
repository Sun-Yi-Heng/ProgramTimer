import java.util.ArrayList;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Object o = new Object();
        synchronized (o) {
            try {
                o.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ArrayList<String> array = new ArrayList<String>();
        Scanner scn = new Scanner(System.in);
        String line;
        String strings = "";
        while (!"".equals(line = scn.nextLine())) {
            array.add(line);
            strings += line + " ";
        }
        for (String str : array) {
            System.out.println(str);
        }
        scn.close();
        System.out.println(strings);
    }

}

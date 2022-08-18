package listToMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;


public class Utils {
  static List<String> loadLines(String file) throws IOException {
        try ( var br = new BufferedReader(new InputStreamReader(
                Players.class.getClassLoader().getResourceAsStream(file)))) {

            return br.lines().toList();
        }
    }
      static void equal(Object o1, Object o2) {
        if (!o1.toString().equals(o2.toString())) {
            throw new RuntimeException("Not equal");
        }
    }

    static void notEequal(Object o1, Object o2) {
        if (o1.toString().equals(o2.toString())) {
            throw new RuntimeException("Not equal");
        }
    }

    static void print(String name, Map m) {
        System.out.println(">" + name);
        print(m);
    }

    static void print(Map m) {

        m.forEach((k1, v1) -> print(k1, v1, 0));
        System.out.println("-".repeat(30));
    }

    static void print(Object k, Object v, int tabs) { // switch expressions TODO
        if (v instanceof Map m) {
            System.out.println("\t".repeat(tabs) + k);
            m.forEach((k1, v1) -> print(k1, v1, tabs + 1));
        } else {
            System.out.println("\t".repeat(tabs) + k + "\t=>" + v);
        }
    }
}

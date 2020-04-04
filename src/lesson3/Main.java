package lesson3;

import javax.management.Query;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        List<String> l = new ArrayList<>(Arrays.asList("A","B","C","A","D","A","G","S","G","B","A","U"));

        Set<String> distinct = new HashSet<>();
        distinct.addAll(l);
        System.out.println(distinct);

       // l.stream().distinct().forEach(s -> System.out.print(s));

        System.out.println();
        HashMap<String, Integer> map = new HashMap<>();
        HashSet<String> set = new HashSet<>();
        for (String s : l) {
            map.computeIfPresent(s, (key, val) -> ++val);
            map.putIfAbsent(s, 1);
        }
        System.out.println(map);


        Phonebook phoneBook = new Phonebook();
        phoneBook.add("Mr Anderson", "7906", "7908", "7907");
        phoneBook.add("Mr Anderson", "7907");
        phoneBook.add("Mr Anderson", "7909");
        phoneBook.add("Ivanov");
        System.out.println(phoneBook.getLastnames());
        System.out.println(phoneBook.getPhone("Mr Anderson"));
        System.out.println(phoneBook.getPhone("Ivanov"));
    }
}

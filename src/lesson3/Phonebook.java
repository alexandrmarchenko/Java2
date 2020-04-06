package lesson3;

import java.util.*;

public class Phonebook {
    private HashMap<String, Set<String>> book = new HashMap<>();

    public String getLastnames() {
        return book.keySet().toString();
    }

    public String getPhone(String lastname){
        if(!book.containsKey(lastname)) {
            return String.format("Нет данных по фамилии %s", lastname);
        }
        String phoneNumbers =  book.get(lastname).toString();
        return String.format("%s список номеров:%s",lastname, phoneNumbers);
    }

    public void add(String lastname, String... phoneNumbers) {
        Set<String> newPhoneSet = new HashSet<>(Arrays.asList(phoneNumbers));
        if (book.containsKey(lastname)) {
            newPhoneSet.addAll(book.get(lastname));
        }
        book.put(lastname, newPhoneSet);

        //book.putIfAbsent(lastname, asList(phoneNumbers));
        //Не получилось computeIfPresent сделать
        //book.computeIfPresent(lastname, (k, v) -> v.addAll(Arrays.asList(phoneNumbers)));
    }

}

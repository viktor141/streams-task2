import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        countOfMinors(persons.stream());
        System.out.println();
        namesOfConscripts(persons.stream());
        System.out.println();
        potentiallyEmployablePeople(persons.stream());
    }

    private static void countOfMinors(Stream<Person> personStream) {
        System.out.println("Людей младше 18: " + personStream.filter(person -> person.getAge() < 18).count());
    }

    private static void namesOfConscripts(Stream<Person> personStream) {
        System.out.println("Фамилии призывников: " + personStream
                .filter(person -> person.getSex().equals(Sex.MAN) && (person.getAge() >= 18 && person.getAge() <= 27))
                .map(Person::getFamily).collect(Collectors.toList()));
    }

    private static void potentiallyEmployablePeople(Stream<Person> personStream) {
        System.out.println("Отсортированный по фамилии список потенциально работоспособных людей с высшим образованием: " + personStream
                .filter(person ->
                        person.getEducation().equals(Education.HIGHER) &&
                                ((person.getSex().equals(Sex.WOMAN) && person.getAge() >= 18 && person.getAge() <= 60)
                                        || (person.getSex().equals(Sex.MAN) && person.getAge() >= 18 && person.getAge() <= 65)))
                .sorted(Comparator.comparing(Person::getFamily)).collect(Collectors.toList()));
    }

}
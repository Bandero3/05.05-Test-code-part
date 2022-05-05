import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String USER_FILE = "users.json";
    private List<Person> persons = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        Scanner scanner = new Scanner(System.in);

        main.readFile();
        main.selectAction(scanner);

    }

    private void menu() {
        System.out.println("""
                [1]. Sukurti vartotoja
                [2]. Parodyti vartotoju sarasa
                [3]. Uždaryti Programa
                """);
    }

    private void selectAction(Scanner scanner) throws IOException {
        String action;

        do {
            menu();
            action = scanner.nextLine();
            switch (action) {
                case "1" -> createUser(scanner);
                case "2" -> persons.stream().forEach(System.out::println);
                case "3" -> {
                    writeFile();
                    System.out.println("Programa baige darba");
                }
                default -> System.out.println("Tokio veiksmo nera");
            }

        } while (!action.equals("3"));
    }

    private void createUser(Scanner scanner) {

        String fullName = getUniqueName(scanner);
        long personCode = getPersonCode(scanner);

        String[] name = fullName.split(" ");
        persons.add(new Person(name[0], name[1], personCode));
        System.out.printf("Sekmingai sukurete vartotoja %s %s\n", name[0], name[1]);


    }

    private long getPersonCode(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Iveskite vartotojo asmens koda");
                String personCode = scanner.nextLine();
                System.out.println(personCode.length());
                if (personCode.length() != 11) {
                    System.out.println("Asmens kodas turi susideti is 11 skaiciu");
                    continue;
                }
                long personCodeInt = Long.parseLong(personCode);

                return personCodeInt;
            } catch (NumberFormatException e) {
                System.out.println("Blogai ivestas asmens kodas, bandykite vėl");
            }
        }
    }

    private String getUniqueName(Scanner scanner) {
        while (true) {
            System.out.println("Iveskite vartotojo Varda ir Pavarde:");
            String newUserName = scanner.nextLine();
            String[] name = newUserName.split(" ");

            if (name.length != 2) {
                System.out.println("Blogai ivestas vardas arba pavarde");
                continue;
            }
            Person existingPerson = persons.stream()
                    .filter(n -> n.getName().equals(name[0]) && n.getSurname().equals(name[1]))
                    .findFirst()
                    .orElse(null);

            if (existingPerson != null) {
                System.out.printf("Vartotojas %s jau existuoja\n", newUserName);
                continue;
            }
            return newUserName;

        }

    }

    private void readFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = createFile();
        if (file.length() != 0) {
            persons = mapper.readValue(file, new TypeReference<>() {
            });
        }
    }

    private void writeFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        File file = createFile();
        if (!persons.isEmpty()) {
            mapper.writeValue(file, persons);
        }
    }

    private File createFile() throws IOException {
        File file = new File(USER_FILE);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

}

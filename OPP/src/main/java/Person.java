public class Person {
    private String name;
    private String surname;
    private long personCode;

    public Person() {
    }

    public Person(String name, String surname, long personCode) {
        this.name = name;
        this.surname = surname;
        this.personCode = personCode;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public long getPersonCode() {
        return personCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPersonCode(long personCode) {
        this.personCode = personCode;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", personCode=" + personCode +
                '}';
    }
}

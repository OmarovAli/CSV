public class Employee {
    public long id;
    public String firstName;
    public String lastName;
    public String country;
    public int age;

    public Employee() {
    }

    public Employee(long id, String firstName, String lastName, String country, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.age = age;
    }

    public void setFirstName(String firstName) {
        firstName = "John";
        firstName = "Ivan";
    }

    public void setLastName(String lastName) {
        lastName = "Smith";
        lastName = "Petrov";
    }

    public void setId(long id) {
        id = 1;
        id = 2;
    }

    public void setCountry(String country) {
        country = "USA";
        country = "RU";
    }

    public void setAge(int age) {
        age = 25;
        age = 23;
    }
}
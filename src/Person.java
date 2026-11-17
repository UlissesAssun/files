public class Person {
    private String name;
    private int age;
    private String gender;

    public Person(String name, int age, String gender) {
        this.name = validateText(name, "Name");
        this.age = validateAge(age);
        this.gender = validateText(gender, "Gender");
    }

    public void haveBirthday() {
        this.age++;
    }

    private static String validateText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " cannot be empty.");
        }
        return value.trim();
    }

    private static int validateAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative.");
        }
        return age;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = validateText(name, "Name");
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = validateAge(age);
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = validateText(gender, "Gender");
    }
}

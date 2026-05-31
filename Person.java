package model;

public class Person implements Displayable {
    protected int id;
    protected String name;
    protected String email;

    public Person(int id, String name, String email) {
        this.id = (id > 0) ? id : 0;
        this.name = "Unknown";
        this.email = "pending@gmail.com";
        setName(name);
        setEmail(email);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    public boolean setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
            return true;
        }
        return false;
    }

    public boolean setEmail(String email) {
        if (email != null && email.trim().contains("@")) {
            this.email = email.trim();
            return true;
        }
        return false;
    }

    @Override
    public void displayInfo() {
        System.out.println("+---------------------------+");
        System.out.printf("| PERSON ID: %-20d |\n", id);
        System.out.printf("| Name: %-25s |\n", name);
        System.out.printf("| Email: %-24s |\n", email);
        System.out.println("+---------------------------+");
    }
}

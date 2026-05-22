package model;

public abstract class Person implements Displayable {
    protected int id;
    protected String name;
    protected String email;

    public Person(int id, String name, String email) {
        this.id = (id > 0) ? id : 0;
        this.name = (name != null && !name.trim().isEmpty()) ? name : "Unknown";
        this.email = (email != null && email.contains("@")) ? email : "pending@gmail.com";
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public abstract void displayInfo(); 
}
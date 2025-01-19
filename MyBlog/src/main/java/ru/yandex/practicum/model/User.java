package ru.yandex.practicum.model;

public class User {

    private Long id;
    private String firstName;
    private String LastName;
    private int age;
    private boolean active;

    public User() {}

    public User(Long id, String firstName, String lastName, int age, boolean active) {
        this.id = id;
        this.firstName = firstName;
        LastName = lastName;
        this.age = age;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

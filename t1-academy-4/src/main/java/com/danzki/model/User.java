package com.danzki.model;

public class User {
    private Long id;
    private String userName;

    public User() {
    }

    public User(Long id, String name) {
        this.id = id;
        this.userName = name;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + userName + '\'' +
                '}';
    }
}

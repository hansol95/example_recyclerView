package com.example.example_recyclerview;

public class Person {
    Long Id;
    String name;
    String phone;

    public Person(String name, String phone){
        this.name = name;
        this.phone = phone;
    }

    public Person() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }
}

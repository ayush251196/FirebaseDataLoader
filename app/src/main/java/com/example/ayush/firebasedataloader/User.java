package com.example.ayush.firebasedataloader;

class User {
    private String name;
    private int age;

    public User(){
        name="not assigned";
        age=0;
    }
    public User(String name,int age){
        this.name=name;
        this.age=age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

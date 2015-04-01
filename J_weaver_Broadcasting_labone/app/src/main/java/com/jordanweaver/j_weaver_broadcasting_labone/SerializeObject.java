package com.jordanweaver.j_weaver_broadcasting_labone;

import java.io.Serializable;

/**
 * Created by jordanweaver on 3/31/15.
 */
public class SerializeObject implements Serializable {

    String firstName;
    String lastName;
    int age;

    public SerializeObject(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }


    @Override
    public String toString() {
        return firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

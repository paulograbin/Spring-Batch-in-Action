package com.example.batchprocessing;

import java.util.Date;

public class Person {

    public String firstName;
    public String lastName;
    public Date creationTime;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "Person{" + firstName + " " + lastName + '}';
    }
}

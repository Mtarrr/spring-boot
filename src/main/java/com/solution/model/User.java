package com.solution.model;

import java.util.Objects;

public class User {
    private String name;
    private String surname;
    private String patronymic;
    private int age;
    private int salary;
    private String email;
    private String workplace;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && salary == user.salary && name.equals(user.name)
                && surname.equals(user.surname) && patronymic.equals(user.patronymic)
                && email.equals(user.email) && workplace.equals(user.workplace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, patronymic, age, salary, email, workplace);
    }

    @Override
    public String toString() {
        return "User[name=" + name + ", sn=" + surname + ", patr=" + patronymic
                + ", age=" + age + ", salary=" + salary + ", email=" + email
                + ", wp=" + workplace + "]";
    }
}
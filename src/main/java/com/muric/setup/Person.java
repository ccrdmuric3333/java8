package com.muric.setup;

import java.util.Random;

public class Person {
    public enum Gender {Male, Female}
    private String[] firstMasculine = {"Vasily", "Peter", "John", "Paul", "Jeff", "Michael", "Martin", "Nikita", "Aaron", "Aleksey"};
    private String[] fistFeminine = {"Anna", "Regina", "Margarita", "Helen", "Rachel", "Jessica", "Emma", "Julia", "Caroline", "Diana"};
    private String[] lastNames = {"Smith", "Stewart", "Price", "Brown", "King", "Ross", "Moore", "Scott", "Perry", "Thompson"};

    private int age;
    private String firstName;
    private String lastName;
    private Gender gender;

    public Person(){
        Random random = new Random();
        int index = random.nextInt(10);

        this.gender = index % 2 == 0 ? Gender.Male : Gender.Female;
        this.firstName = gender == Gender.Male ? firstMasculine[index] : fistFeminine[index];
        index = random.nextInt(10);
        this.lastName = lastNames[index];
        this.age = random.nextInt(120);
    }

    @Override
    public String toString(){
        return firstName + " " + lastName + ", " + gender.toString() + " of age " + age;
    }

    public int getAge() {
        return age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }
}

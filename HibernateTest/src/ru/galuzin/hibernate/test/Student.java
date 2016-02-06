package ru.galuzin.hibernate.test;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Galuzin on 21.01.2016.
 */
@Entity
@Table(name="student")
public class Student {
    private Long id;
    private String name;
    private Long age;

    public Student(){
        name = null;
    }

    public Student(Student s){
        name = s.getName();
    }



    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Long getId(){
        return id;
    }

    @Column(name="name")
    public String getName() {
        return name;
    }

    @Column(name="age")
    public Long getAge() {
        return age;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

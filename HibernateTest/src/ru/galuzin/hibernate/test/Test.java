package ru.galuzin.hibernate.test;

//import org.hibernate.annotations.GenericGenerator;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by User on 30.01.2016.
 */
@Entity
@Table(name="test")
public class Test {
    private Long tid;
    private String tname;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment",strategy = "increment")
    @Column(name = "tid")
    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }
    @Column(name="tname")
    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }
}

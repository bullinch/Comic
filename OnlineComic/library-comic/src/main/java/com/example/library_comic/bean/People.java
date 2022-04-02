package com.example.library_comic.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class People {

    @Id(autoincrement = true)
    private Long id;

    private int age;
    private String name;
    private String work;

    public People(int age, String name, String work) {
        this.age = age;
        this.name = name;
        this.work = work;
    }

    @Generated(hash = 1829054843)
    public People(Long id, int age, String name, String work) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.work = work;
    }

    @Generated(hash = 1406030881)
    public People() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWork() {
        return this.work;
    }

    public void setWork(String work) {
        this.work = work;
    }
}

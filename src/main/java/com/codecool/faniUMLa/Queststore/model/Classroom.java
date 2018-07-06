package com.codecool.faniUMLa.Queststore.model;

public class Classroom {

    private int class_id;
    private String class_name;

    public int getClass_id() {
        return class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public Classroom(int class_id, String class_name) {
        this.class_id = class_id;
        this.class_name = class_name;

    }
}

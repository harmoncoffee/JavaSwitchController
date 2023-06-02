package com.pharmondev.computercontrol.models;

public class Program {
    private String name;
    private String javaClassName;

    // Getters and setters

    public Program() {}

    public Program(String name, String javaClassName) {
        this.name = name;
        this.javaClassName = javaClassName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJavaClassName() {
        return javaClassName;
    }

    public void setJavaClassName(String javaClassName) {
        this.javaClassName = javaClassName;
    }

    // Optional: Override toString() for convenient printing
    @Override
    public String toString() {
        return "Program{"
                + "name='"
                + name
                + '\''
                + ", javaClassName='"
                + javaClassName
                + '\''
                + '}';
    }
}

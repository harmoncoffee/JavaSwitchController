package com.pharmondev.computercontrol.models;

import java.util.List;

public class Game {
    private String name;
    private List<Program> programs;

    // Getters and setters

    public Game() {}

    public Game(String name, List<Program> programs) {
        this.name = name;
        this.programs = programs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    // Optional: Override toString() for convenient printing
    @Override
    public String toString() {
        return "Game{" + "name='" + name + '\'' + ", programs=" + programs + '}';
    }
}

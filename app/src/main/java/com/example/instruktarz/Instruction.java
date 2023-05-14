package com.example.instruktarz;

import java.util.ArrayList;
import java.util.List;

public class Instruction {
    private String name;
    private List<String> steps;

    public Instruction(String name) {
        this.name = name;
        this.steps = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public void addStep(String step) {
        this.steps.add(step);
    }
}

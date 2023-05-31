package com.example.instruktarz;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Instruction {
    private String name;
    private List<String> steps;

    public Instruction(String name, ArrayList<String> steps) {
        this.name = name;
        this.steps = steps;
    }
    public Instruction(String name) {
        this.name = name;
        this.steps = new ArrayList<>();
    }
    public Instruction() {
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

    @Override
    public String toString() {
        String string = "";
        string = string + "[(" + name + ")" + "(" + Integer.toString(steps.size()) + ")";
        for (int i = 0; i < steps.size(); i++) {
            string = string + "{" + steps.get(i) + "}";
        }
        string = string + "]";
        return string;
    }

    public String shareToString() {
        String string = "";
        string = string + name + "\n";
        for (int i = 0; i < steps.size(); i++){
            string = string + "Krok " + Integer.toString(i+1) + ":\n" + steps.get(i) + "\n";
        }

        return string;
    }

    public static Instruction convertStringToInstruction(String inst){
        Instruction instruction = new Instruction();
        try{
            if(inst.contains("[")){
                instruction.name = inst.substring(inst.indexOf("(") + 1, inst.indexOf(")"));
                inst = inst.substring(inst.indexOf(")") + 1);
                Integer stepCount = Integer.parseInt(inst.substring(inst.indexOf("(") + 1, inst.indexOf(")")));
                inst = inst.substring(inst.indexOf(")") + 1);
                for(int i = 0; i < stepCount; i++){
                    instruction.steps.add(inst.substring(inst.indexOf("{") + 1, inst.indexOf("}")));
                    inst = inst.substring(inst.indexOf("}") + 1);
                }
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return instruction;
    }
}

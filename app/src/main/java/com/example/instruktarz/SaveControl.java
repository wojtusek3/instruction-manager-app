package com.example.instruktarz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SaveControl {

    public static void writeToFile(String fileName, File path, ArrayList<Instruction> content){
        try{
            FileOutputStream writer = new FileOutputStream(new File(path, fileName));
            for (Instruction inst : content){
                writer.write(inst.toString().getBytes(StandardCharsets.UTF_8));
            }
            writer.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public static String readFromFile(String fileName, File path){
        File readFrom = new File(path, fileName);
        byte[] content = new byte[(int) readFrom.length()];
        try{
            FileInputStream stream = new FileInputStream(readFrom);
            stream.read(content);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return new String(content);
    }
    public static ArrayList<Instruction> getInstructions(File AppContextFilePath){
        ArrayList<Instruction> instructions = new ArrayList<>();
        String instructionsString = SaveControl.readFromFile("file.txt", AppContextFilePath);
        while(instructionsString.indexOf("]") > 0){
            instructions.add(Instruction.convertStringToInstruction(instructionsString.substring(instructionsString.indexOf("["), instructionsString.indexOf("]"))));
            instructionsString = instructionsString.substring(instructionsString.indexOf("]") + 1);
        }
        return instructions;
    }
}

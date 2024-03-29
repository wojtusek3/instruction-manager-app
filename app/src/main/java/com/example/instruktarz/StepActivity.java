package com.example.instruktarz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class StepActivity extends AppCompatActivity {
    Integer currentStepID;
    String instructionName;
    String[] instructionSteps;

    TextView instructionNameTextView, stepCounterTextView, stepContentTextView;
    ImageButton nextImageButton, previousImageButton, editInstructionImageButton, deleteInstructionImageButton, closeInstructionImageButton, shareInstructionImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        //przypisz widoki
        instructionNameTextView = findViewById(R.id.instructionNameTextView);
        stepCounterTextView = findViewById(R.id.stepCounterTextView);
        stepContentTextView = findViewById(R.id.stepContentTextView);
        nextImageButton = findViewById(R.id.nextStepImageButton);
        previousImageButton = findViewById(R.id.previousStepImageButton);
        editInstructionImageButton = findViewById(R.id.editInstructionImageButton);
        deleteInstructionImageButton = findViewById(R.id.deleteInstructionImageButton);
        shareInstructionImageButton = findViewById(R.id.shareInstructionImageButton);
        closeInstructionImageButton = findViewById(R.id.closeInstructionImageButton);
        closeInstructionImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                //usuń historię aktywności
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            }
        });

        //pobierz dane z intencji
        currentStepID = getIntent().getIntExtra("CurrentStepID",0);
        if(getIntent().hasExtra("InstructionName")){
            instructionName = getIntent().getStringExtra("InstructionName");
            instructionNameTextView.setText(instructionName);
        }
        if(getIntent().hasExtra("InstructionSteps"))
        {
            //jeśli znajdziesz, przypisz tekst
            instructionSteps = getIntent().getStringArrayExtra("InstructionSteps");
            stepCounterTextView.setText("Krok " + Integer.toString(currentStepID + 1) + " z " + Integer.toString(instructionSteps.length));
            stepContentTextView.setText(instructionSteps[currentStepID]);

            //ustaw onClick dla przycisków
            nextImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentStepID + 1 < instructionSteps.length)
                    {
                        currentStepID++;
                        stepCounterTextView.setText("Krok " + Integer.toString(currentStepID + 1) + " z " + Integer.toString(instructionSteps.length));
                        stepContentTextView.setText(instructionSteps[currentStepID]);
                    }
                }
            });

            previousImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentStepID - 1 >= 0)
                    {
                        currentStepID--;
                        stepCounterTextView.setText("Krok " + Integer.toString(currentStepID + 1) + " z " + Integer.toString(instructionSteps.length));
                        stepContentTextView.setText(instructionSteps[currentStepID]);
                    }
                }
            });
            editInstructionImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), EditStepActivity.class);
                    intent.putExtra("InstructionName", instructionName);
                    intent.putExtra("InstructionSteps", instructionSteps);
                    v.getContext().startActivity(intent);
                }
            });
            deleteInstructionImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Czy na pewno chcesz usunąć instrukcję \"" + instructionName + "\"?");

                    builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ArrayList<Instruction> instructions = SaveControl.getInstructions(getApplicationContext().getFilesDir());
                            for (int i = 0; i < instructions.size(); i++) {
                                if(instructions.get(i).getName().equals(instructionName)){
                                    instructions.remove(i);
                                    Toast.makeText(v.getContext(), "Usunięto instrukcję \"" + instructionName + "\"", Toast.LENGTH_SHORT).show();
                                    SaveControl.writeToFile("file.txt", getApplicationContext().getFilesDir(), instructions);

                                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                                    //usuń historię aktywności
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    v.getContext().startActivity(intent);
                                    break;
                                }
                            }
                        }
                    });

                    builder.setNegativeButton("Nie", null);
                    builder.show();
                }
            });
            shareInstructionImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Instruction sharedInstruction = new Instruction(instructionName);
                    Collections.addAll(sharedInstruction.getSteps(), instructionSteps);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Udostępnij instrukcję");
                    intent.putExtra(Intent.EXTRA_TEXT, sharedInstruction.shareToString());
                    v.getContext().startActivity(Intent.createChooser(intent, "Udostępnij poprzez..."));
                }
            });
        }
    }
}
package com.example.instruktarz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class EditStepActivity extends AppCompatActivity {
    Integer currentStepID;
    String instructionName;
    ArrayList<String> instructionSteps;

    TextView stepCounterTextView;
    ImageButton nextStepImageButton, previousStepImageButton, deleteStepImageButton, saveInstructionImageButton;
    EditText stepEditText, instructionNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_step);
        currentStepID = 0;

        //przypisz widoki i onClicki
        instructionNameEditText = findViewById(R.id.instructionNameEditText);
        stepCounterTextView = findViewById(R.id.stepCounterTextView);
        stepEditText = findViewById(R.id.stepEditText);

        //przejdź na następny krok, jeśli nie ma: dodaj
        nextStepImageButton = findViewById(R.id.nextStepImageButton);
        nextStepImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentStepID+1 >= instructionSteps.size())
                    instructionSteps.add("");
                instructionSteps.set(currentStepID, stepEditText.getText().toString());
                currentStepID++;
                stepCounterTextView.setText("Krok " + Integer.toString(currentStepID + 1) + " z " + Integer.toString(instructionSteps.size()));
                stepEditText.setText(instructionSteps.get(currentStepID));
            }
        });

        //przejdź na poprzedni krok
        previousStepImageButton = findViewById(R.id.previousStepImageButton);
        previousStepImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentStepID-1 >= 0){
                    instructionSteps.set(currentStepID, stepEditText.getText().toString());
                    currentStepID--;
                    stepCounterTextView.setText("Krok " + Integer.toString(currentStepID + 1) + " z " + Integer.toString(instructionSteps.size()));
                    stepEditText.setText(instructionSteps.get(currentStepID));
                }

            }
        });

        //usuń zaznaczony krok
        deleteStepImageButton = findViewById(R.id.deleteStepImageButton);
        deleteStepImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instructionSteps.remove(instructionSteps.get(currentStepID));
                if(currentStepID >= instructionSteps.size()){
                    if(instructionSteps.size() > 0)
                        currentStepID--;
                    else
                        instructionSteps.add("");
                }
                stepCounterTextView.setText("Krok " + Integer.toString(currentStepID + 1) + " z " + Integer.toString(instructionSteps.size()));
                stepEditText.setText(instructionSteps.get(currentStepID));
            }
        });

        //zapisz instrukcję
        saveInstructionImageButton = findViewById(R.id.saveInstructionImageButton);
        saveInstructionImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //pobierz dane z intencji
        instructionSteps = new ArrayList<>();
        if(getIntent().hasExtra("InstructionName"))
        {
            instructionName = getIntent().getStringExtra("InstructionName");
            instructionNameEditText.setText(instructionName);
        }
        if(getIntent().hasExtra("InstructionSteps"))
        {
            String[] steps = getIntent().getStringArrayExtra("InstructionSteps");
            for (String step : steps)
            {
                instructionSteps.add(step);
            }
        }
        else
        {
            instructionSteps.add("");
        }
        stepCounterTextView.setText("Krok " + Integer.toString(currentStepID + 1) + " z " + Integer.toString(instructionSteps.size()));
        stepEditText.setText(instructionSteps.get(currentStepID));
    }
}
package com.example.instruktarz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class StepActivity extends AppCompatActivity {
    Integer currentStepID;
    String instructionName;
    String[] instructionSteps;

    TextView instructionNameTextView, stepCounterTextView, stepContentTextView;
    ImageButton nextImageButton, previousImageButton, editInstructionImageButton, deleteInstructionImageButton;

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
        }
    }
}
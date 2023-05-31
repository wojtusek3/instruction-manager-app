package com.example.instruktarz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class EditStepActivity extends AppCompatActivity {
    Integer currentStepID;
    String instructionName;
    ArrayList<String> instructionSteps;
    ArrayList<Instruction> instructions;

    TextView stepCounterTextView;
    ImageButton nextStepImageButton, previousStepImageButton, deleteStepImageButton, saveInstructionImageButton, closeInstructionImageButton;
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

        //przycisk - przejdź na następny krok; jeśli nie ma to dodaj
        nextStepImageButton = findViewById(R.id.nextStepImageButton);
        nextStepImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentStepID+1 >= instructionSteps.size())
                    instructionSteps.add("");
                instructionSteps.set(currentStepID, stepEditText.getText().toString().trim());
                currentStepID++;
                stepCounterTextView.setText("Krok " + Integer.toString(currentStepID + 1) + " z " + Integer.toString(instructionSteps.size()));
                stepEditText.setText(instructionSteps.get(currentStepID));
            }
        });

        //przycisk - przejdź na poprzedni krok; jeśli nie ma to dodaj
        previousStepImageButton = findViewById(R.id.previousStepImageButton);
        previousStepImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instructionSteps.set(currentStepID, stepEditText.getText().toString().trim());
                if(currentStepID == 0){
                    instructionSteps.add("");
                    //przenieś instrukcje do tyłu
                    for(int i=instructionSteps.size()-1;i>0;i--){
                        instructionSteps.set(i, instructionSteps.get(i-1));
                    }
                    instructionSteps.set(0, "");
                }
                else currentStepID--;
                stepCounterTextView.setText("Krok " + Integer.toString(currentStepID + 1) + " z " + Integer.toString(instructionSteps.size()));
                stepEditText.setText(instructionSteps.get(currentStepID));
            }
        });

        //przycisk - usuń zaznaczony krok
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

        //przycisk - zapisz instrukcję
        saveInstructionImageButton = findViewById(R.id.saveInstructionImageButton);
        saveInstructionImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //zapisz dane
                instructionName = instructionNameEditText.getText().toString().trim();
                instructionSteps.set(currentStepID, stepEditText.getText().toString().trim());

                if(!instructionName.equals("")){

                    //usuń wszystkie puste kroki
                    for(int i = 0; i < instructionSteps.size(); i++) {
                        if(instructionSteps.get(i).equals("")){
                            instructionSteps.remove(i);
                            i--;
                        }
                    }
                    //jeśli nie ma kroków, nie idź dalej
                    if(instructionSteps.size() == 0) {
                        Toast.makeText(v.getContext(), "Instrukcja nie zawiera zawartości!", Toast.LENGTH_SHORT).show();

                        currentStepID = 0;
                        instructionSteps.add("");
                        stepCounterTextView.setText("Krok " + Integer.toString(currentStepID + 1) + " z " + Integer.toString(instructionSteps.size()));
                        stepEditText.setText(instructionSteps.get(currentStepID));

                        return;
                    }

                    //zaktualizuj lub ustaw na ostani wypełniony krok, żeby uniknąć błędów
                    if(currentStepID > instructionSteps.size()-1) currentStepID = instructionSteps.size()-1;
                    stepCounterTextView.setText("Krok " + Integer.toString(currentStepID + 1) + " z " + Integer.toString(instructionSteps.size()));
                    stepEditText.setText(instructionSteps.get(currentStepID));

                    //builder do okienka potwierdzającego zapisanie instrukcji
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setPositiveButton("Potwierdź", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //zapisz instrukcje
                            SaveControl.writeToFile("file.txt", getApplicationContext().getFilesDir(), instructions);
                            Toast.makeText(v.getContext(), "Pomyślnie zapisano instrukcję \"" + instructionName + "\"", Toast.LENGTH_SHORT).show();

                            //intencja do głównego widoku
                            Intent intent = new Intent(v.getContext(), MainActivity.class);
                            //usuń historię aktywności
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            v.getContext().startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Anuluj", null);

                    //pobierz instrukcje z pliku
                    instructions = SaveControl.getInstructions(getApplicationContext().getFilesDir());
                    boolean found = false;
                    //zmień kroki jeśli znajdziesz
                    for (Instruction inst : instructions) {
                        if(inst.getName().equals(instructionName)){
                            builder.setMessage("Instukcja \"" + instructionName + "\" zostanie nadpisana.");
                            //Toast.makeText(v.getContext(), "Instruckcja " + instructionName + " została nadpisana", Toast.LENGTH_SHORT).show();
                            found = true;
                            inst.setSteps(new ArrayList<>());
                            for (String step : instructionSteps)
                                inst.addStep(step);
                        }
                    }
                    //w przeciwnym wypadku dodaj jako nowa instrukcja
                    if(!found){
                        instructions.add(new Instruction(instructionName, instructionSteps));
                        builder.setMessage("Zostanie dodana nowa instrukcja \"" + instructionName + "\".");
                    }

                    //wyświetl okienko potwierdzenia zapisu pliku
                    builder.show();
                }
                else{
                    Toast.makeText(v.getContext(), "Nie nazwy podano instrukcji!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //przycisk - wyjdź do menu głównego
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
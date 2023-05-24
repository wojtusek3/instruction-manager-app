package com.example.instruktarz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
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

public class MainActivity extends AppCompatActivity {
    public ArrayList<Instruction> instructions, filteredInstructions;
    public ItemAdapter itemAdapter;
    public RecyclerView recyclerView;
    public ImageButton addInstructionImageButton, searchImageButton;
    public TextView ifEmptyTextView;
    public EditText searchForInstructionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instructions = SaveControl.getInstructions(getApplicationContext().getFilesDir());
        filteredInstructions = instructions;
        itemAdapter = new ItemAdapter(this, filteredInstructions);
        recyclerView = findViewById(R.id.instructionsRecyclerView);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ifEmptyTextView = findViewById(R.id.ifEmptyTextView);
        if(!filteredInstructions.isEmpty()) ifEmptyTextView.setVisibility(View.GONE);
        searchForInstructionEditText = findViewById(R.id.searchForInstructionEditText);
        searchImageButton = findViewById(R.id.searchImageButton);
        searchImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lookingFor = searchForInstructionEditText.getText().toString().trim().toLowerCase();
                if(lookingFor == "" || lookingFor == null) {
                    filteredInstructions = instructions;
                }
                else{
                    filteredInstructions = new ArrayList<>();
                    for(int i = 0; i < instructions.size(); i++){
                        if(instructions.get(i).getName().toLowerCase().contains(lookingFor))
                            filteredInstructions.add(instructions.get(i));
                    }
                }
                itemAdapter.setInstructions(filteredInstructions);
                if(filteredInstructions.isEmpty()) ifEmptyTextView.setVisibility(View.VISIBLE);
                else ifEmptyTextView.setVisibility(View.GONE);
                itemAdapter.notifyDataSetChanged();
            }
        });

        addInstructionImageButton = findViewById(R.id.addInstructionImageButton);
        addInstructionImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditStepActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
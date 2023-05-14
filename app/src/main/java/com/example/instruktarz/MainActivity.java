package com.example.instruktarz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.PersistableBundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public ArrayList<Instruction> instructions;
    public ItemAdapter itemAdapter;
    public RecyclerView recyclerView;

    public void getInstructions(){
        instructions = new ArrayList<>();
        instructions.add(new Instruction("Jak pokonac slime"));
        instructions.get(0).addStep("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin vel turpis consequat, mattis urna a, pellentesque velit. Cras luctus egestas felis at iaculis. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur id odio ligula. Aliquam lorem nisi, euismod nec mollis id, euismod sit amet quam. Phasellus lacinia et massa at fringilla. Sed facilisis mi massa, a tristique quam vestibulum eget. Duis blandit sagittis mi. Suspendisse hendrerit venenatis sodales. Mauris semper orci at lectus luctus porta. Praesent eu felis maximus, maximus mauris ut, hendrerit leo. Curabitur auctor eget sapien eu semper. Nulla enim ipsum, consequat eu fringilla ac, tempus vel leo.");
        instructions.get(0).addStep("Quisque cursus lacinia rhoncus. Maecenas est felis, euismod sollicitudin congue in, congue faucibus lectus. Nullam vestibulum viverra sem, eu viverra leo blandit nec. Sed vulputate nibh at volutpat accumsan. Morbi id mauris eget tellus tincidunt placerat. Ut volutpat est imperdiet nisi egestas, sit amet sagittis lectus fringilla. Phasellus et massa vitae ligula placerat porta. Cras lacinia enim orci, a lacinia quam viverra quis.");
        instructions.get(0).addStep("Phasellus urna lorem, imperdiet quis lacinia in, hendrerit eu nunc. Cras feugiat mi lectus. Vestibulum a interdum orci. Donec lobortis pellentesque congue. Pellentesque vel consequat nisi. Nunc id blandit mi, at finibus dolor. Nulla convallis justo vulputate, elementum enim et, tincidunt turpis. Morbi congue gravida erat, in iaculis quam faucibus in. Nunc lorem mauris, pretium vitae pharetra in, mattis at sapien. Nullam imperdiet, magna sed commodo facilisis, tellus massa tincidunt neque, eget cursus arcu metus ut tellus.");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getInstructions();
        itemAdapter = new ItemAdapter(this, instructions);
        recyclerView = findViewById(R.id.instructionsRecyclerView);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}
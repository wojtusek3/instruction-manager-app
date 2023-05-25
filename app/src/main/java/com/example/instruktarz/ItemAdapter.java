package com.example.instruktarz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<Instruction> instructions, oginstructions;

    public ItemAdapter(Context context, ArrayList<Instruction> instructions, ArrayList<Instruction> oginstructions) {
        inflater = LayoutInflater.from(context);
        this.oginstructions = oginstructions;
        this.instructions = instructions;
    }

    public void setInstructions(ArrayList<Instruction> instructions){
        this.instructions = instructions;
    }
    public void setOriginalInstructions(ArrayList<Instruction> oginstructions){
        this.oginstructions = oginstructions;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_view, parent, false);
        return new ItemViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.nameTextView.setText(instructions.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StepActivity.class);
                Instruction instr = instructions.get(holder.getAbsoluteAdapterPosition());
                intent.putExtra("InstructionName", instr.getName());
                String[] steps = new String[instr.getSteps().size()];
                for(int i = 0; i < steps.length; i++){
                    steps[i] = instr.getSteps().get(i);
                }
                intent.putExtra("InstructionSteps", steps);
                v.getContext().startActivity(intent);
            }
        });
        holder.editImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditStepActivity.class);
                Instruction instr = instructions.get(holder.getAbsoluteAdapterPosition());
                intent.putExtra("InstructionName", instr.getName());
                String[] steps = new String[instr.getSteps().size()];
                for(int i = 0; i < steps.length; i++){
                    steps[i] = instr.getSteps().get(i);
                }
                intent.putExtra("InstructionSteps", steps);
                v.getContext().startActivity(intent);
            }
        });
        holder.deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Instruction deleted_inst = instructions.get(holder.getAbsoluteAdapterPosition());
                instructions.remove(holder.getAbsoluteAdapterPosition());
                for(int i = 0; i < oginstructions.size(); i++){
                    if(oginstructions.get(i).getName().equals(deleted_inst.getName())){
                        oginstructions.remove(i);
                        break;
                    }
                }
                SaveControl.writeToFile("file.txt", inflater.getContext().getFilesDir(), oginstructions);
                Toast.makeText(inflater.getContext(), "Usunięto instrukcję " + deleted_inst.getName(), Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return instructions.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public ImageButton editImageButton;
        public ImageButton deleteImageButton;
        final ItemAdapter itemAdapter;

        public ItemViewHolder(@NonNull View itemView, ItemAdapter itemAdapter) {
            super(itemView);
            this.itemAdapter = itemAdapter;
            nameTextView = itemView.findViewById(R.id.nameTextView);
            editImageButton = itemView.findViewById(R.id.editImageButton);
            deleteImageButton = itemView.findViewById(R.id.deleteImageButton);
        }
    }
}

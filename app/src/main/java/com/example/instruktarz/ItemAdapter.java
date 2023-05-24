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
    private ArrayList<Instruction> instructions;

    public ItemAdapter(Context context, ArrayList<Instruction> instructions) {
        inflater = LayoutInflater.from(context);
        this.instructions = instructions;
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
                Toast.makeText(inflater.getContext(), "Usunięto instrukcję " + instructions.get(holder.getAbsoluteAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
                instructions.remove(holder.getAbsoluteAdapterPosition());
                SaveControl.writeToFile("file.txt", inflater.getContext().getFilesDir(), instructions);
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

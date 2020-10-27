package com.example.rickandmorty;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    ArrayList<Character> characters;
    LayoutInflater layoutInflater;
    Context context;
    Dialog dialog;

    public CustomAdapter(ArrayList<Character> characters, Context context) {
        this.characters = characters;
        this.context = context;
        this.dialog = new Dialog(context);
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Her bir Card için tanımlama yapılır.
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.card_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        holder.textView.setText(characters.get(position).getName());
        holder.imageView.setImageResource(characters.get(position).getImage());
        holder.layout.setTag(holder);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewHolder viewHolder =  (ViewHolder) view.getTag();
                int position = viewHolder.getAdapterPosition();
                showPopup(characters.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public void showPopup(Character character){
        dialog.setContentView(R.layout.custom_popup);
        TextView closeText = dialog.findViewById(R.id.closeTextButton);
        TextView name = dialog.findViewById(R.id.characterNameDialog);
        TextView status = dialog.findViewById(R.id.statusDialog);
        TextView lastLoc = dialog.findViewById(R.id.lastLocDialog);
        ImageView image = dialog.findViewById(R.id.imageViewDialog);

        name.setText(character.getName());
        status.setText(character.getStatus());
        lastLoc.setText(character.getLastKnownLoc());
        image.setImageResource(character.getImage());

        closeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        int width = (int)(context.getResources().getDisplayMetrics().widthPixels*0.95);
        Objects.requireNonNull(dialog.getWindow()).setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.image);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}

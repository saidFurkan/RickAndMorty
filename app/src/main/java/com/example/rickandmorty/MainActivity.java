package com.example.rickandmorty;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Character> characters = new ArrayList<>();
    Context context = MainActivity.this;

    DBHelper dbHelper = new DBHelper(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(getLayoutInflater().inflate(R.layout.title_bar, null));

        // The first time the program runs, it enables data to be transferred to the database.
        checkFirstTime();

        LinearLayout linearLayout = findViewById(R.id.mainLayout);
        TextView textView = findViewById(R.id.loadingText);
        linearLayout.removeView(textView);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.VISIBLE);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        characters = dbHelper.getAllCharacters();

        CustomAdapter customAdapter = new CustomAdapter(characters, context);
        recyclerView.setAdapter(customAdapter);

    }


    private void checkFirstTime(){
        SharedPreferences settings = getSharedPreferences("SQL", 0);
        boolean firstTime = settings.getBoolean("firstTime", true);

        if (firstTime) {
            loadJSONFromAsset();

            for (Character character : characters) {
                dbHelper.insertCharacter(character);
            }

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstTime", false);
            editor.apply();
        }
    }

    public void loadJSONFromAsset() {
        String json;
        try {
            InputStream is = context.getAssets().open("DataRickAndMorty.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println("Error Read file in 'loadJSONFromAsset()'");
            ex.printStackTrace();
            return;
        }

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray jsonArray = obj.getJSONArray("Characters");
            JSONObject jsonObject;
            Character character;
            int i, id, imageId;
            for (i = 0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                id = Integer.parseInt(jsonObject.getString("id"));
                imageId = this.getResources().getIdentifier("character_"+id+"_image", "drawable", this.getPackageName());
                character = new Character(id, jsonObject.getString("name"),
                        jsonObject.getString("status"),jsonObject.getString("lastLoc"),imageId);
                characters.add(character);
            }
        } catch (JSONException e) {
            System.out.println("Error parse json in 'loadJSONFromAsset()'");
            e.printStackTrace();
        }
    }




}
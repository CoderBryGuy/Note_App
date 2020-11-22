package com.example.note_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;

public class MakeNote extends AppCompatActivity {

//    Button saveNoteBtn;
    EditText myEditText;
    String myNote = "";
    ArrayList<String> myNotes;
    ArrayAdapter<String> myAdapter;
    int index = 0;
    boolean isEditing = false;
    SharedPreferences sharedPreferences;


    public void saveNote(View view){
        if(isEditing) {
            myNotes.set(index, myEditText.getText().toString());
        }else{
            myNotes.add(myEditText.getText().toString());
        }
        try {
            sharedPreferences.edit().putString("notes", ObjectSerializer.serialize(myNotes)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    myAdapter.notifyDataSetChanged();

    finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_note);
        sharedPreferences = this.getSharedPreferences("com.example.memorable_places_app", Context.MODE_PRIVATE);

        myNotes = MainActivity.getNotes();
        myAdapter = MainActivity.getMyAdapter();

//        saveNoteBtn = (Button)findViewById(R.id.myButton);
        Intent intent = getIntent();
        index = intent.getIntExtra(MainActivity.INDEX, 0);
        myEditText = (EditText)findViewById(R.id.myEditText);

        if(index > 0){
            isEditing = true;
            myEditText.setText(myNotes.get(index));
        }else {
            isEditing = false;
        }

    }
}
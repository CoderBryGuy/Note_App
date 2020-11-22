package com.example.note_app;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView myListView;
    static ArrayAdapter<String> myAdapter;
    final static String INDEX = "index";
    SharedPreferences sharedPreferences;
    static ArrayList<String> myNotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = this.getSharedPreferences("com.example.memorable_places_app", Context.MODE_PRIVATE);
        try {
            myNotes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notes", ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setUpListView();
    }

    private void setUpListView() {
        myNotes.add("add note...");

        myListView = (ListView)findViewById(R.id.myListView);
        myAdapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, myNotes);
        myListView.setAdapter(myAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MakeNote.class);
                intent.putExtra(INDEX, position);
                startActivity(intent);
            }
        });

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(getApplicationContext())
                        .setIcon(android.R.drawable.ic_btn_speak_now)
                        .setTitle("Delete This Note")
                        .setMessage("Yes Or No?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myNotes.remove(position);
                                myAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "Note Not Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();

                //need to google what return condition should be
                return false;
            }
        });
    }

    public static ArrayList<String> getNotes(){
        if(myNotes != null) {
            return myNotes;
        }
        return new ArrayList<>();
    }

    public static ArrayAdapter<String> getMyAdapter() {
        return myAdapter;
    }
}
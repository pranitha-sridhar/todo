package com.example.sql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText name;
    FloatingActionButton submit;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    AdapterP adapter;
    ArrayList<Model> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submit=findViewById(R.id.fab);
        name=findViewById(R.id.name);
        recyclerView=findViewById(R.id.rview);
        recyclerView.setHasFixedSize(true);

        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final DBHelper dbhelper=new DBHelper(getApplicationContext());

        arrayList=dbhelper.show();
        adapter=new AdapterP(arrayList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AdapterP.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                Toast.makeText(MainActivity.this, "HEHE", Toast.LENGTH_SHORT).show();
                //arrayList=dbhelper.show();
                //adapter=new AdapterP(arrayList);
                //recyclerView.setAdapter(adapter);
            }
        }, new AdapterP.ItemCheckListener() {
            @Override
            public void onItemCheck(int position) {
                Toast.makeText(MainActivity.this, "Checked", Toast.LENGTH_SHORT).show();
            }
        });






        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namee=name.getText().toString();
                if (namee.isEmpty()){
                    name.requestFocus();
                    name.setError("Field should not be empty");
                    return;

                }
                DBHelper dbHelper=new DBHelper(getApplicationContext());
                Model model=new Model(namee,false);
                dbHelper.add(model);
                name.setText("");
                Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();
                ArrayList<Model> arrayList=dbhelper.show();
                adapter=new AdapterP(arrayList);
                recyclerView.setAdapter(adapter);


            }
        });





    }
}

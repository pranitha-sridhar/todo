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

public class MainActivity extends AppCompatActivity implements AdapterP.OnItemClickListener{
    EditText editText;
    FloatingActionButton submit;
    RecyclerView recyclerView;
    AdapterP adapter;
    ArrayList<Model> arrayList;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submit=findViewById(R.id.fab);
        editText =findViewById(R.id.name);
        recyclerView=findViewById(R.id.rview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager=new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

        show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text= editText.getText().toString();
                if (text.isEmpty()){
                    editText.requestFocus();
                    editText.setError("Field should not be empty");
                    return;

                }
                dbHelper.add(new Model(text,false));
                editText.setText("");
                Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();
                show();
            }
        });
    }

    @Override
    public void onItemClick(Model model, int position) {
        Toast.makeText(this, "Text", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemDelete(Model model, int position) {
        dbHelper.delete(model);
        show();
        Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
    }

    public void show(){
        dbHelper=new DBHelper(MainActivity.this);
        arrayList=dbHelper.show();
        adapter=new AdapterP(MainActivity.this,arrayList);
        adapter.setOnItemClickListener(MainActivity.this);
        recyclerView.setAdapter(adapter);
    }
}

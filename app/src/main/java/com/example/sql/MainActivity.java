package com.example.sql;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterP.OnItemClickListener{
    EditText editText;
    FloatingActionButton submit;
    RecyclerView recyclerView;
    AdapterP adapter;
    ArrayList<Model> arrayList;
    DBHelper dbHelper;

    EditText title,desc,date,time;
    String str_time,str_date,str_title,str_desc;
    Button update;
    AlertDialog alertDialog;
    Calendar calendar;
    int chr, cmin, cdate, cmonth, cyear;

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
                boolean result=dbHelper.add(new Model(text,"","",""));
                if(result){
                    editText.setText("");
                    Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();
                    show();
                }
                else
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(final Model model, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.update_task, null);
        builder.setView(v);
        title=v.findViewById(R.id.ed_update_task);
        desc=v.findViewById(R.id.ed_update_desc);
        date=v.findViewById(R.id.ed_update_date);
        time=v.findViewById(R.id.ed_update_time);
        update=v.findViewById(R.id.bt_update);

        title.setText(model.getTitle());
        desc.setText(model.getDesc());
        date.setText(model.getDate());
        time.setText(model.getTime());

        alertDialog=builder.create();
        alertDialog.show();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        alertDialog.getWindow().setBackgroundDrawable(null);
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        calendar=Calendar.getInstance();


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(MainActivity.this, t4, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, t3, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_date=date.getText().toString();
                str_desc=desc.getText().toString();
                str_time=time.getText().toString();
                str_title=title.getText().toString();
                Model newModel=new Model(str_title,str_desc,str_date,str_time,model.getId());
                boolean res=dbHelper.update(newModel);
                if(res){
                    Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    show();
                }
                else
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
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

    TimePickerDialog.OnTimeSetListener t4 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            chr = hourOfDay;
            cmin = minute;
            str_time = chr + ":" + cmin;
            time.setText("" + str_time);
        }
    };

    DatePickerDialog.OnDateSetListener t3=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            cyear=year;
            cdate=dayOfMonth;
            cmonth=month+1;
            str_date=cdate+"/" +cmonth+"/"+cyear;
            date.setText(""+str_date);
        }
    };
}

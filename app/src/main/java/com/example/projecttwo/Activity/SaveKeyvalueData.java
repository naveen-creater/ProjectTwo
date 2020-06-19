package com.example.projecttwo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projecttwo.R;

public class SaveKeyvalueData extends AppCompatActivity {
    private SharedPreferences sharedpreferences;

    public static final String mypreference = "mypref";
    public static final String nameKey = "myprefName";
    public static final String emailKey = "myprefEmail";
    public static final String empIdKey = "myprefEmpId";
    private EditText name;
    private EditText emailId;
    private EditText employeId;
    private Button save;
    private Button clear;
    private Button retrive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_keyvalue_data);
        initView(); // intiate views
        listeners(); //click and listeners

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        if(sharedpreferences.contains(nameKey)){
            name.setText(sharedpreferences.getString(nameKey, ""));
        }
        if(sharedpreferences.contains(emailKey)){
            emailId.setText(sharedpreferences.getString(emailKey, ""));
        }
        if(sharedpreferences.contains(empIdKey)){
            employeId.setText(sharedpreferences.getString(empIdKey,""));
        }

    }

    private void listeners() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                if(!name.getText().toString().isEmpty())
                {
                    String names = name.getText().toString();
                    editor.putString(nameKey,names);
                }
                else {
                   name.setError("Enter name");
                }

                if(!emailId.getText().toString().isEmpty()){
                    String mails = emailId.getText().toString();
                    editor.putString(emailKey,mails);
                }else {
                    emailId.setError("Enter Email");
                }
                if(!employeId.getText().toString().isEmpty()){
                    String empids = employeId.getText().toString();
                    editor.putString(empIdKey,empids);
                }else {
                    employeId.setError("Enter EmpId");
                }
                editor.commit();
                editor.apply();

                Toast.makeText(SaveKeyvalueData.this, "Savind Data Successfully...", Toast.LENGTH_SHORT).show();

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                employeId.setText("");
                emailId.setText("");
            }
        });

        retrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sharedpreferences.contains(nameKey)){
                    name.setText(sharedpreferences.getString(nameKey, ""));
                }
                if(sharedpreferences.contains(emailKey)){
                    emailId.setText(sharedpreferences.getString(emailKey, ""));
                }
                if(sharedpreferences.contains(empIdKey)){
                    employeId.setText(sharedpreferences.getString(empIdKey,""));
                }

                Toast.makeText(SaveKeyvalueData.this, "RetrivedData Successfully...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        name = findViewById(R.id.name);
        emailId = findViewById(R.id.email);
        employeId = findViewById(R.id.emp_id);
        save = findViewById(R.id.save);
        clear = findViewById(R.id.clear);
        retrive = findViewById(R.id.retrive);

    }
}
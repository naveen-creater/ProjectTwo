package com.example.projecttwo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.projecttwo.R;

public class AutoCompleteActivity extends AppCompatActivity {
    String[] language ={"C","C++","Java",".NET","iPhone","Android","ASP.NET","PHP"};
    private Button getLanguage;
    private AutoCompleteTextView autoCompleteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_complete);
        initViewAndListeners();
        //Creating the instance of ArrayAdapter containing list of language names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,language);

        autoCompleteTextView.setThreshold(1);//will start working from first character
        autoCompleteTextView.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        autoCompleteTextView.setTextColor(Color.CYAN);
    }

    private void initViewAndListeners() {
        //View
        getLanguage = findViewById(R.id.getLanguage);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        //Listeners
        getLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String laguage = autoCompleteTextView.getText().toString();
                if(!laguage.isEmpty())
                Toast.makeText(AutoCompleteActivity.this, "Your Fav Language is : "+laguage, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(AutoCompleteActivity.this, "", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
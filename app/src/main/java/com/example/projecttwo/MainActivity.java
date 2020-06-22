package com.example.projecttwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import com.example.projecttwo.Activity.AutoCompleteActivity;
import com.example.projecttwo.Activity.BioMetricAuthActivity;
import com.example.projecttwo.Activity.ContactsProviderActivity;
import com.example.projecttwo.Activity.MyCalenderEvent;
import com.example.projecttwo.Activity.RoomDaoActivity;
import com.example.projecttwo.Activity.SaveKeyvalueData;
import com.example.projecttwo.Activity.SharingDataBluetooth;
import com.example.projecttwo.Adapter.RecycleAdapter;
import com.example.projecttwo.Model.HomeData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMain;
    private RecyclerView.LayoutManager layoutManager;
    private List<HomeData> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();

        recyclerViewMain.setAdapter(new RecycleAdapter(MainActivity.this,data));
    }

    private void initview() {
        data.add(new HomeData(R.drawable.roomdatabase_logo,"Room DataBase","RoomDatabase provides direct access to the underlying database implementation but you should prefer using Dao classes", RoomDaoActivity.class));
        data.add(new HomeData(R.drawable.autocomplete_logo,"AutoComplete TextView","You can check Android official documentation for complete list", AutoCompleteActivity.class));
        data.add(new HomeData(R.drawable.bleuetooth_logo,"Data Transfer Bluetooth","Bluetooth is a wireless technology standard used for exchanging data between fixed and mobile devices over short distance", SharingDataBluetooth.class));
        data.add(new HomeData(R.drawable.calender_event_logo,"Add Calender Event","The Calendar API provides different flavors of event resources, more information can be found in About events.", MyCalenderEvent.class));
        data.add(new HomeData(R.drawable.save_keyvalue_logo,"Save Key-Pair Value","Save key-value data. If you have a relatively small collection of key-values that you'd like to save", SaveKeyvalueData.class));
        data.add(new HomeData(R.drawable.contacts_logo,"Contacts","ontacts is the Contacts app, com. android. settings is the Settings app, and com. android. systemui is the System UI ", ContactsProviderActivity.class));
        data.add(new HomeData(R.drawable.ic_baseline_fingerprint_24,"BioMetric authendication","Finger print authendication", BioMetricAuthActivity.class));

        recyclerViewMain = findViewById(R.id.recyclemain);
        recyclerViewMain.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewMain.setLayoutManager(layoutManager);

    }
}
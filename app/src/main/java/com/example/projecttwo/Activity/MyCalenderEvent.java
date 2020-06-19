package com.example.projecttwo.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.projecttwo.R;

import java.util.Calendar;

public class MyCalenderEvent extends AppCompatActivity {
    private EditText topic;
    private EditText description;
    private EditText location;
    private ToggleButton needRemainder;
    private ToggleButton needAttendies;
    private Button useIntent;
    private Button useBackground;

    private boolean needremiderFalg = false;
    private boolean needattendisFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calender_event);

        initView(); // inteView

        listeners(); // listeners


    }

    private void listeners() {
        //Intent pass Extra valuse
        useIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mTopic = topic.getText().toString();
                String mLocation = location.getText().toString();
                String mDescription = description.getText().toString();

                if(!mTopic.isEmpty() && !mDescription.isEmpty() && !mLocation.isEmpty()){

                    useIntent(mTopic,mDescription,mLocation);
                }
                else {
                    if(mTopic.isEmpty())
                        topic.setError("Enter Topic");

                    if(mDescription.isEmpty())
                        description.setError("Enter description");

                    if(mLocation.isEmpty())
                        location.setError("Enter Location");
                }

            }
        });

        //use background and using Uri
        useBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mTopic = topic.getText().toString();
                String mLocation = location.getText().toString();
                String mDescription = description.getText().toString();

                if(!mTopic.isEmpty() && !mDescription.isEmpty() && !mLocation.isEmpty()){

                    setEventInCalender(mTopic,mDescription,mLocation,needremiderFalg,needattendisFlag);
                }
                else {
                    if(mTopic.isEmpty())
                        topic.setError("Enter Topic");

                    if(mDescription.isEmpty())
                        description.setError("Enter description");

                    if(mLocation.isEmpty())
                        location.setError("Enter Location");
                }


            }
        });

        needRemainder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                needremiderFalg = isChecked;
            }
        });

        needAttendies.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                needattendisFlag = isChecked;
            }
        });
    }

    private void initView() {
        topic = findViewById(R.id.topic);
        description = findViewById(R.id.description);
        location = findViewById(R.id.location);
        needAttendies = findViewById(R.id.needattendies);
        needRemainder = findViewById(R.id.needremainder);
        useBackground = findViewById(R.id.bgMethod);
        useIntent = findViewById(R.id.intenMethod);
    }


    private void setEventInCalender(String title, String descriptions, String locations, boolean remiderneed, boolean attendiesneed) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if ((ContextCompat.checkSelfPermission(MyCalenderEvent.this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED)) {

                long calID = 3;
                long startMillis = 0;
                long endMillis = 0;
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(2020, 7, 13, 7, 30);
                startMillis = beginTime.getTimeInMillis();
                Calendar endTime = Calendar.getInstance();
                endTime.set(2020, 7, 13, 8, 45);
                endMillis = endTime.getTimeInMillis();

                ContentResolver cr = getContentResolver();
                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.DTSTART, startMillis);
                values.put(CalendarContract.Events.DTEND, endMillis);
                values.put(CalendarContract.Events.TITLE, title);
                values.put(CalendarContract.Events.DESCRIPTION, descriptions);
                values.put(CalendarContract.Events.CALENDAR_ID, calID);
                values.put(CalendarContract.Events.EVENT_LOCATION, locations);
                values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

                long eventID = Long.parseLong(uri.getLastPathSegment());

                if (remiderneed) {

                    String reminderUriString = "content://com.android.calendar/reminders";
                    ContentValues reminderValues = new ContentValues();

                    reminderValues.put("event_id", eventID);
                    reminderValues.put("minutes", 10); // Reminder before minutes (10 minutes)
                    reminderValues.put("method", 1); // Alert Methods: Default(0),

                    Uri reminderUri = cr.insert(Uri.parse(reminderUriString), reminderValues);
                }

                if (attendiesneed) {
                    String attendeuesesUriString = "content://com.android.calendar/attendees";
                    ContentValues attendeesValues = new ContentValues();

                    attendeesValues.put("event_id", eventID);
                    attendeesValues.put("attendeeName", "Balamurugan"); // Attendees name
                    attendeesValues.put("attendeeEmail", "bala@gmail.com");// Attendee
                    attendeesValues.put("attendeeRelationship", 0);
                    attendeesValues.put("attendeeType", 0);
                    attendeesValues.put("attendeeStatus", 0);

                    Uri attendeuesesUri = cr.insert(Uri.parse(attendeuesesUriString), attendeesValues);
                }


                topic.setText("");
                description.setText("");
                location.setText("");

                Toast.makeText(this, "Event add is sucessfull...", Toast.LENGTH_LONG).show();


            } else {
                ActivityCompat.requestPermissions(MyCalenderEvent.this,
                        new String[]{Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR},
                        12);
            }
        }

    }

    private void useIntent(String title, String description, String location) {
        Calendar cal = Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis() + (1000 * 60 * 60))
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.DESCRIPTION, description)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.Events.HAS_ALARM, true)
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
//                .putExtra(Intent.EXTRA_EMAIL, "naveen@example.com,kumar@gmail.com");

        startActivity(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 12) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //granted
//                calenderEvent(MyCalenderEvent.this, "Go Gym", "don't forget", "Near pentrol bank", 1, 12, true, true);

            } else {
                //not granted

            }
        }

    }

}
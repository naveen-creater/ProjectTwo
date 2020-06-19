package com.example.projecttwo.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projecttwo.DAO.Mydatabase;
import com.example.projecttwo.DAO.RecyclerViewAdapter;
import com.example.projecttwo.DAO.Todo;
import com.example.projecttwo.DAO.TodoNoteActivity;
import com.example.projecttwo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomDaoActivity extends AppCompatActivity implements RecyclerViewAdapter.ClickListener, AdapterView.OnItemSelectedListener{
    Mydatabase myDatabase;
    RecyclerView recyclerView;
    Spinner spinner;
    RecyclerViewAdapter recyclerViewAdapter;
    Button addbutton;
    private String[] categories = {
            "All",
            "Android",
            "iOS",
            "Kotlin",
            "Swift",
            "C#",
            "HTML"
    };

    ArrayList<Todo> todoArrayList = new ArrayList<>();
    ArrayList<String> spinnerList = new ArrayList<>(Arrays.asList(categories));

    public static final int NEW_TODO_REQUEST_CODE = 200;
    public static final int UPDATE_TODO_REQUEST_CODE = 300;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_dao);

        initViews(); //initViews

        myDatabase = Room.databaseBuilder(getApplicationContext(), Mydatabase.class, Mydatabase.DB_NAME).fallbackToDestructiveMigration().build();

        checkIfAppLaunchedFirstTime(); // app lanched first

        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(0);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(RoomDaoActivity.this, TodoNoteActivity.class), NEW_TODO_REQUEST_CODE);
            }
        });
    }

    private void initViews() {
        addbutton = findViewById(R.id.fab);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //Recyclerview
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void launchIntent(int id) {
        startActivityForResult(new Intent(RoomDaoActivity.this, TodoNoteActivity.class).putExtra("id", id), UPDATE_TODO_REQUEST_CODE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            loadAllTodos();
        } else {
            String string = parent.getItemAtPosition(position).toString();
            loadFilteredTodos(string);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @SuppressLint("StaticFieldLeak")
    private void loadFilteredTodos(String category) {
        new AsyncTask<String, Void, List<Todo>>() {
            @Override
            protected List<Todo> doInBackground(String... params) {
                return myDatabase.daoAccess().fetchTodoListByCategory(params[0]);

            }

            @Override
            protected void onPostExecute(List<Todo> todoList) {
                recyclerViewAdapter.updateTodoList(todoList);
            }
        }.execute(category);

    }


    @SuppressLint("StaticFieldLeak")
    private void fetchTodoByIdAndInsert(int id) {
        new AsyncTask<Integer, Void, Todo>() {
            @Override
            protected Todo doInBackground(Integer... params) {
                return myDatabase.daoAccess().fetchTodoListById(params[0]);
            }
            @Override
            protected void onPostExecute(Todo todoList) {
                recyclerViewAdapter.addRow(todoList);
            }
        }.execute(id);

    }

    @SuppressLint("StaticFieldLeak")
    private void loadAllTodos() {
        new AsyncTask<String, Void, List<Todo>>() {
            @Override
            protected List<Todo> doInBackground(String... params) {
                return myDatabase.daoAccess().fetchAllTodos();
            }

            @Override
            protected void onPostExecute(List<Todo> todoList) {
                recyclerViewAdapter.updateTodoList(todoList);
            }
        }.execute();
    }

    private void buildDummyTodos() {
        Todo todo = new Todo();
        todo.name = "Android Retrofit Tutorial";
        todo.description = "Cover a tutorial on the Retrofit networking library using a RecyclerView to show the data.";
        todo.category = "Android";

        todoArrayList.add(todo);

        insertList(todoArrayList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            //reset spinners
            spinner.setSelection(0);

            if (requestCode == NEW_TODO_REQUEST_CODE) {
                long id = data.getLongExtra("id", -1);
                Toast.makeText(getApplicationContext(), "Row inserted", Toast.LENGTH_SHORT).show();
                fetchTodoByIdAndInsert((int) id);
            } else if(requestCode == UPDATE_TODO_REQUEST_CODE) {

                boolean isDeleted = data.getBooleanExtra("isDeleted", false);
                int number = data.getIntExtra("number", -1);
                if (isDeleted) {
                    Toast.makeText(getApplicationContext(), number + "rows deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), number + "rows updated", Toast.LENGTH_SHORT).show();
                }

                loadAllTodos();
            }


        } else {
            Toast.makeText(getApplicationContext(), "No action done by user", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertList(List<Todo> todoList) {

        new AsyncTask<List<Todo>, Void, Void>() {
            @Override
            protected Void doInBackground(List<Todo>... params) {
                myDatabase.daoAccess().insertTodoList(params[0]);
                return null;
            }
            @Override
            protected void onPostExecute(Void voids) {
                super.onPostExecute(voids);
            }
        }.execute(todoList);

    }

    private void checkIfAppLaunchedFirstTime() {
        final String PREFS_NAME = "SharedPref";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        if (settings.getBoolean("firstTime", true)) {
            settings.edit().putBoolean("firstTime", false).apply();
            buildDummyTodos();
        }
    }
}
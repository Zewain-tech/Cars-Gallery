package com.example.recyclerviewapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int CAR_ADD_REQ_CODE = 1;
    private static final int CAR_EDIT_REQ_CODE = 1;
    public static final String CAR_KEY = "car_key";
    private RecyclerView rv;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    CarRCAdapter adapter;
    private DataBaseAccess db;
    private final static int REQ_CODE_EXTERNAL = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQ_CODE_EXTERNAL);
        }


        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        rv = findViewById(R.id.main_rv);
        fab = findViewById(R.id.main_fab);


        db = DataBaseAccess.getInstance(this);

        db.open();
        ArrayList<Car> cars = db.getAllCars();
        db.close();

        adapter = new CarRCAdapter(cars, new OnRecyclerViewItemClickListner() {
            @Override
            public void itemClick(int carid) {
                Intent intent = new Intent(getBaseContext(), ViewCarDetails.class);
                intent.putExtra(CAR_KEY, carid);
                startActivityForResult(intent, CAR_EDIT_REQ_CODE);
            }
        });
        rv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new GridLayoutManager(this, 2);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ViewCarDetails.class);
                startActivityForResult(intent, CAR_ADD_REQ_CODE);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                db.open();
                ArrayList<Car> cars = db.getCars(query);
                db.close();
                adapter.setCars(cars);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                db.open();
                ArrayList<Car> cars = db.getCars(newText);
                db.close();
                adapter.setCars(cars);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                db.open();
                ArrayList<Car> cars = db.getAllCars();
                db.close();
                adapter.setCars(cars);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAR_ADD_REQ_CODE) {
            db.open();
            ArrayList<Car> cars = db.getAllCars();
            db.close();
            adapter.setCars(cars);
            adapter.notifyDataSetChanged();


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_CODE_EXTERNAL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Permission is allowed", Toast.LENGTH_SHORT).show();
                }
                return;

        }

    }

}

package com.example.recyclerviewapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class ViewCarDetails extends AppCompatActivity {

    private static final int IMAGE_REQ_CODE = 1;
    public static final int ADD_CAR_CODE = 2;
    public static final int EDIT_CAR_CODE = 3;
    private Toolbar toolbarDetails;
    private ImageView imageViewDetails;
    private TextInputEditText et_model, et_color, et_description, et_distance;
    private Uri ImageUri;
    private int carid = -1;
    private DataBaseAccess db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_car_details);

        toolbarDetails = findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbarDetails);

        imageViewDetails = findViewById(R.id.toolbar_iv_details);

        et_model = findViewById(R.id.ed_car_model);
        et_color = findViewById(R.id.ed_car_color);
        et_description = findViewById(R.id.ed_car_description);
        et_distance = findViewById(R.id.ed_car_distance);

        db = DataBaseAccess.getInstance(this);

        Intent intent = getIntent();
        carid = intent.getIntExtra(MainActivity.CAR_KEY, -1);
        if (carid == -1) {

            //عند الاضافه
            enableFields();
            clearFields();

        } else {
            // العرض
            disableFields();
            db.open();
            Car c = db.getCar(carid);
            db.close();
            if (c != null) {
                fillCarToDetails(c);
            }

        }

        imageViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, IMAGE_REQ_CODE);
            }
        });

    }

    private void fillCarToDetails(Car c) {
        if (c.getImage() != null && !c.getImage().equals(""))
            imageViewDetails.setImageURI(Uri.parse(c.getImage()));
        et_model.setText(c.getModel());
        et_color.setText(c.getColor());
        et_description.setText(c.getDescription());
        et_distance.setText(String.valueOf(c.getDistance()));
    }

    private void disableFields() {
        imageViewDetails.setEnabled(false);
        et_model.setEnabled(false);
        et_color.setEnabled(false);
        et_description.setEnabled(false);
        et_distance.setEnabled(false);
    }

    private void enableFields() {
        imageViewDetails.setEnabled(true);
        et_model.setEnabled(true);
        et_color.setEnabled(true);
        et_description.setEnabled(true);
        et_distance.setEnabled(true);
    }

    private void clearFields() {
        imageViewDetails.setImageURI(null);
        et_model.setText("");
        et_color.setText("");
        et_distance.setText("");
        et_description.setText("");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);

        MenuItem Save = menu.findItem(R.id.details_item_save);
        MenuItem Edit = menu.findItem(R.id.details_item_edit);
        MenuItem Delete = menu.findItem(R.id.details_item_delete);

        if (carid == -1) {
            //عند الاضافه
            Save.setVisible(true);
            Edit.setVisible(false);
            Delete.setVisible(false);
        } else {
            // العرض
            Save.setVisible(false);
            Edit.setVisible(true);
            Delete.setVisible(true);
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String model, color, desc, image = " ";
        double distance;
        db.open();

        switch (item.getItemId()) {


            case R.id.details_item_save:
                model = et_model.getText().toString();
                color = et_color.getText().toString();
                desc = et_description.getText().toString();
                distance = Double.parseDouble(et_distance.getText().toString());

                if (ImageUri != null )
                    image = ImageUri.toString();

                Car c = new Car(carid, model, color, desc, image, distance);
                Boolean res;
                if (carid == -1) {
                    res = db.insertCar(c);
                    if (res) {
                        Toast.makeText(this, "Car Added", Toast.LENGTH_SHORT).show();
                        setResult(ADD_CAR_CODE, null);
                        finish();
                    }
                } else {
                    res = db.updataCar(c);
                    Toast.makeText(this, "Car Edited", Toast.LENGTH_SHORT).show();
                    setResult(EDIT_CAR_CODE, null);
                    finish();
                }


                return true;

            //      التعدييييييييييييييييييييل

            case R.id.details_item_edit:
                MenuItem Save = toolbarDetails.getMenu().findItem(R.id.details_item_save);
                MenuItem Edit = toolbarDetails.getMenu().findItem(R.id.details_item_edit);
                MenuItem Delete = toolbarDetails.getMenu().findItem(R.id.details_item_delete);

                Save.setVisible(true);
                Edit.setVisible(false);
                Delete.setVisible(false);
                enableFields();
                return true;

            case R.id.details_item_delete:
                c = new Car(carid, null, null, null, null, 0);

                res = db.deleteCar(c);
                if (res) {
                    Toast.makeText(this, "Car deleted", Toast.LENGTH_SHORT).show();
                    setResult(EDIT_CAR_CODE, null);
                    finish();
                }


                return true;
        }

            db.close();

            return false;
        }


        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            if (IMAGE_REQ_CODE == requestCode && resultCode == RESULT_OK) {
                if (data != null) {
                    ImageUri = data.getData();
                    imageViewDetails.setImageURI(ImageUri);

                }

            }
        }
    }


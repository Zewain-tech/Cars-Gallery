package com.example.recyclerviewapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseAccess {
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private static DataBaseAccess instance;


    private DataBaseAccess(Context context) {
        this.openHelper = new MyDatabase(context);
    }

    public static DataBaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseAccess(context);

        }

        return instance;
    }

    public void open() {
        this.database = this.openHelper.getWritableDatabase();
    }

    public void close() {
        if (this.database != null) {
            this.database.close();
        }
    }


    public Boolean insertCar(Car car) {

        ContentValues values = new ContentValues();
        values.put(MyDatabase.CAR_CLN_MODEL, car.getModel());
        values.put(MyDatabase.CAR_CLN_COLOR, car.getColor());
        values.put(MyDatabase.CAR_CLN_DESCRIPTION, car.getDescription());
        values.put(MyDatabase.CAR_CLN_IMAGE, car.getImage());
        values.put(MyDatabase.CAR_CLN_DISTANCE, car.getDistance());
        long result = database.insert(MyDatabase.CAR_TB_NAME, null, values);
        return result != -1;


    }


    public Boolean updataCar(Car car) {

        ContentValues values = new ContentValues();
        values.put(MyDatabase.CAR_CLN_MODEL, car.getModel());
        values.put(MyDatabase.CAR_CLN_COLOR, car.getColor());
        values.put(MyDatabase.CAR_CLN_DESCRIPTION, car.getDescription());
        values.put(MyDatabase.CAR_CLN_IMAGE, car.getImage());
        values.put(MyDatabase.CAR_CLN_DISTANCE, car.getDistance());

        String arg[] = {car.getId() + ""};

        int result = database.update(MyDatabase.CAR_TB_NAME, values, "id=?", arg);

        return result > 0;


    }

    public Boolean deleteCar(Car car) {

        String arg[] = {car.getId() + ""};

        int result = database.delete(MyDatabase.CAR_TB_NAME, "id=?", arg);

        return result > 0;
    }


    public long getCarCount() {
        return DatabaseUtils.queryNumEntries(database, MyDatabase.CAR_TB_NAME);

    }

    public ArrayList<Car> getAllCars() {

        ArrayList<Car> cars = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM " + MyDatabase.CAR_TB_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(cursor.getColumnIndex(MyDatabase.CAR_CLN_ID));
                String model = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_MODEL));
                String color = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_COLOR));
                String description = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_DESCRIPTION));
                String image = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_IMAGE));
                double distance = cursor.getDouble(cursor.getColumnIndex(MyDatabase.CAR_CLN_DISTANCE));

                Car c = new Car(id, model, color, description, image, distance);
                cars.add(c);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return cars;
    }


    public ArrayList<Car> getCars(String ModelSearch) {

        ArrayList<Car> cars = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + MyDatabase.CAR_TB_NAME + " WHERE " + MyDatabase.CAR_CLN_MODEL + " LIKE ?", new String[]{ModelSearch + " %"});
        if (cursor != null && cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(cursor.getColumnIndex(MyDatabase.CAR_CLN_ID));
                String model = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_MODEL));
                String color = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_COLOR));
                String decription = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_DESCRIPTION));
                String image = cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_IMAGE));
                double distance = cursor.getDouble(cursor.getColumnIndex(MyDatabase.CAR_CLN_DISTANCE));

                Car c = new Car(id, model, color, decription, image, distance);
                cars.add(c);
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return cars;
    }



    public Car getCar(int carid){

        Cursor cursor=database.rawQuery("SELECT * FROM " +MyDatabase.CAR_TB_NAME+" WHERE " +MyDatabase.CAR_CLN_ID+"=?"
        ,new String[]{String.valueOf(carid)});
        if ( cursor != null && cursor.moveToFirst()  ){

                int id = cursor.getInt(cursor.getColumnIndex(MyDatabase.CAR_CLN_ID));
                String model =cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_MODEL));
                String color =cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_COLOR));
                String description =cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_DESCRIPTION));
                String image =cursor.getString(cursor.getColumnIndex(MyDatabase.CAR_CLN_IMAGE));
                double distance = cursor.getDouble(cursor.getColumnIndex(MyDatabase.CAR_CLN_DISTANCE));

                Car c =new Car(id,model,color,description,image,distance);
            cursor.close();
            return c;
            }
        return null;
    }
}

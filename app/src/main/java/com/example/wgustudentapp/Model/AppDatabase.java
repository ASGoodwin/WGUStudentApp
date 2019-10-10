package com.example.wgustudentapp.Model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.wgustudentapp.Model.DataAccessObjects.AssessmentDAO;
import com.example.wgustudentapp.Model.DataAccessObjects.CourseDAO;
import com.example.wgustudentapp.Model.DataAccessObjects.TermDAO;
import com.example.wgustudentapp.Model.Entities.Assessment;
import com.example.wgustudentapp.Model.Entities.Course;
import com.example.wgustudentapp.Model.Entities.Term;

@Database(entities = {Term.class, Course.class, Assessment.class}, exportSchema = false, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "AppDatabase";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){

        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    public abstract TermDAO termDao();
    public abstract CourseDAO courseDao();
    public abstract AssessmentDAO assessmentDao();


}

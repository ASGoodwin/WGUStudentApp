package com.example.wgustudentapp.Model.DataAccessObjects;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.wgustudentapp.Model.Entities.Term;

import java.util.List;

@Dao
public interface TermDAO {

    @Query("select * from term order by startDate")
    LiveData<List<Term>> getTermList();

    @Query("select id from term")
    int getTermId();

    @Insert
    void insertTerm(Term term);

    @Delete
    void deleteTerm(Term term);

}

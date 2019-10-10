package com.example.wgustudentapp.Model.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.wgustudentapp.Model.AppDatabase;
import com.example.wgustudentapp.Model.DataAccessObjects.TermDAO;
import com.example.wgustudentapp.Model.Entities.Term;

import java.util.List;

public class TermRepository {

    private TermDAO termDao;
    private LiveData<List<Term>> alTerm;
    //private int termId;

    public TermRepository(Application application){

        AppDatabase db = AppDatabase.getInstance(application);
        termDao = db.termDao();
        alTerm = termDao.getTermList();
    }

    public void insertTerm(Term term){
        new InsertTermAsyncTask(termDao).execute(term);
    }

    public void deleteTerm(Term term){
        new DeleteTermAsyncTask(termDao).execute(term);
    }

    //public int getTermId() { return termId; }

    public LiveData<List<Term>> getAlTerm(){
        return alTerm;
    }

    private static class InsertTermAsyncTask extends AsyncTask<Term, Void, Void> {

        private TermDAO termDao;

        private InsertTermAsyncTask(TermDAO termDao){
            this.termDao = termDao;
        }

        @Override
        protected Void doInBackground(Term... terms) {
            termDao.insertTerm(terms[0]);
            return null;
        }
    }

    private static class DeleteTermAsyncTask extends AsyncTask<Term, Void, Void> {

        private TermDAO termDao;

        private DeleteTermAsyncTask(TermDAO termDao){
            this.termDao = termDao;
        }

        @Override
        protected Void doInBackground(Term... terms) {
            termDao.deleteTerm(terms[0]);
            return null;
        }
    }
}

package com.example.wgustudentapp.Classes;


import android.util.Log;

import com.example.wgustudentapp.Model.DataAccessObjects.CourseDAO;
import com.example.wgustudentapp.Model.Entities.Course;
import com.example.wgustudentapp.Model.Entities.Term;

import java.util.Calendar;
import java.util.List;

public class Validation {

    //Validation
    //Verify start date before end date, called from AddTermActivity
    public static boolean isConsecutive(Calendar startDate, Calendar endDate){

        if(endDate.before(startDate) || endDate.equals(startDate)){
            return false;
        }
        return true;
    }
    //Verify term does not overlap with existing terms, called from AddTermActivity
    public static boolean noOverlap(Calendar startDate, Calendar endDate, List<Term> terms){

        //Convert dates to longs
        long start = startDate.getTimeInMillis();
        long end = endDate.getTimeInMillis();
        //Query existing db

        try {
            for(Term t: terms){

                if((start >= t.getStartDate() && start <= t.getEndDate()) //if new term starts during existing term
                        || (end >= t.getStartDate() && end <= t.getEndDate()) //if new term ends during existing term
                        || (t.getStartDate() >= start && t.getStartDate() <= end ) //if old term starts during new term
                        || (t.getEndDate() >= start && t.getEndDate() <= end) //if old term ends during new term
                ){
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return true;
    }
}

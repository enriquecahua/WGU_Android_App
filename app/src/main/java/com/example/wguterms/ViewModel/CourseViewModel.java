package com.example.wguterms.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wguterms.Database.Repository;
import com.example.wguterms.Entities.CourseEntity;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    int termID;
    private Repository repository;
    private LiveData<List<CourseEntity>> allCourses;
    private LiveData<List<CourseEntity>> allAssociatedCourses;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allCourses = repository.getAllCourses();
    }

    //this constructor was added based on the My Bicycle Example
    //It passes the ID of the parent table(terms) to be able to get all
    //associated child table rows

     public CourseViewModel(@NonNull Application application,int termID) {
        super(application);
        repository = new Repository(application);
        allAssociatedCourses = repository.getAssociatedCourses(termID);
     }

    public void insertCourse(CourseEntity course) {repository.insertCourse(course);}

    public void updateCourse(CourseEntity course) {repository.updateCourse(course);}

    public void deleteCourse(CourseEntity course) {repository.deleteCourse(course);}

    //My Bicycle Shop had a method to get allparts
    public LiveData<List<CourseEntity>> getAllCourses()
        { return allCourses;}

    public LiveData<List<CourseEntity>> getAllAssociatedCourses(int termID)
        {return allAssociatedCourses;}

}

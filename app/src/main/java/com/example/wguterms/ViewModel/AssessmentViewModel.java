package com.example.wguterms.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wguterms.Database.Repository;
import com.example.wguterms.Entities.AssessmentEntity;

import java.util.List;

public class AssessmentViewModel extends AndroidViewModel {

    int courseID;
    private Repository repository;
    private LiveData<List<AssessmentEntity>> allAssessments;
    private LiveData<List<AssessmentEntity>> allAssociatedAssessments;

    public AssessmentViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allAssessments = repository.getAllAssessments();
    }

    //this constructor was added based on the My Bicycle Example
    //It passes the ID of the parent table(courses) to be able to get all
    //associated child table rows

    public AssessmentViewModel(@NonNull Application application, int courseID) {
        super(application);
        repository = new Repository(application);
        allAssociatedAssessments = repository.getAssociatedAssessments(courseID);
    }

    public void insertAssessment(AssessmentEntity assessment) {repository.insertAssessment(assessment);}

    public void updateAssessment(AssessmentEntity assessment) {repository.updateAssessment(assessment);}

    public void deleteAssessment(AssessmentEntity assessment) {repository.deleteAssessment(assessment);}

    //My Bicycle Shop had a method to get allparts
    public LiveData<List<AssessmentEntity>> getAllAssessments()
    { return allAssessments;}

    public LiveData<List<AssessmentEntity>> getAllAssociatedAssessments(int courseID)
    {return repository.getAssociatedAssessments(courseID);}


}

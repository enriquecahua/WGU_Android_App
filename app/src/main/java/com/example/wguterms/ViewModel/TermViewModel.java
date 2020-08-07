package com.example.wguterms.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wguterms.Database.Repository;
import com.example.wguterms.Entities.TermEntity;

import java.util.List;

// AndroidViewModel is subclass of ViewModel, get passed application in constructor, can use whenever the application context is needed.
//never store context of activity, or view that references activity in ViewModel, VM is designed to outlive activity
//  need to pass a context to the repository, it is needed there to instantiate the database instance

public class TermViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<TermEntity>> allTerms;


    public TermViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allTerms = repository.getAllTerms();
    }

    public void insertTerm(TermEntity term) {
        repository.insertTerm(term);
    }

    public void updateTerm(TermEntity term) {
        repository.updateTerm(term);
    }

    public void deleteTerm(TermEntity term) {
        repository.deleteTerm(term);
    }

    public void deleteAllTerms() {
        repository.deleteAllTerms();
    }

    public LiveData<List<TermEntity>> getAllTerms() {
        return allTerms;
    }

}

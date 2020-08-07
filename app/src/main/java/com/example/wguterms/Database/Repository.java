package com.example.wguterms.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.wguterms.DAO.AssessmentDao;
import com.example.wguterms.DAO.CourseDao;
import com.example.wguterms.DAO.TermDao;
import com.example.wguterms.Entities.AssessmentEntity;
import com.example.wguterms.Entities.CourseEntity;
import com.example.wguterms.Entities.TermEntity;

import java.util.List;

public class Repository {

    private TermDao termDao;
    private CourseDao courseDao;
    private AssessmentDao assessmentDao;

    private LiveData<List<TermEntity>> allTerms;
    private LiveData<List<CourseEntity>> allCourses;
    private LiveData<List<AssessmentEntity>> allAssessments;

    private LiveData<List<CourseEntity>> associatedCourses;
    private LiveData<List<AssessmentEntity>> associatedAssessments;

    private int termID;
    private int courseID;

    public Repository(Application application) {
        Database database = Database.getInstance(application);
        termDao = database.termDao();
        courseDao = database.courseDao();
        assessmentDao = database.assessmentDao();


        //all
        allTerms = termDao.getAllTerms();
        allCourses = courseDao.getAllCourses();
        allAssessments = assessmentDao.getAllAssessments();

        //get all of the courses/assessment for term/course
        associatedCourses = courseDao.getAssociatedCourses(termID);
        associatedAssessments = assessmentDao.getAssociatedAssessments(courseID);
    }
    //
    //TERM TASKS
    //
    public void insertTerm(TermEntity term) {
        new InsertTermAsyncTask(termDao).execute(term);
    }
    public void updateTerm(TermEntity term) {
        new UpdateTermAsyncTask(termDao).execute(term);
    }
    public void deleteTerm(TermEntity term) {
        new DeleteTermAsyncTask(termDao).execute(term);
    }
    public void deleteAllTerms() {
        new DeleteAllTermAsyncTask(termDao).execute();
    }
    //
    //COURSE TASKS
    //
    public void insertCourse(CourseEntity course) { new InsertCourseAsyncTask(courseDao).execute(course); }
    public void updateCourse(CourseEntity course) { new UpdateCourseAsyncTask(courseDao).execute(course); }
    public void deleteCourse(CourseEntity course) { new DeleteCourseAsyncTask(courseDao).execute(course); }
    //
    // ASSESSMENT TASKS
    //
    public void insertAssessment(AssessmentEntity assessment) { new InsertAssessmentAsyncTask(assessmentDao).execute(assessment); }
    public void updateAssessment(AssessmentEntity assessment) { new UpdateAssessmentAsyncTask(assessmentDao).execute(assessment); }
    public void deleteAssessment(AssessmentEntity assessment) {  new DeleteAssessmentAsyncTask(assessmentDao).execute(assessment); }



    ///LIVE DATA

    public LiveData<List<TermEntity>> getAllTerms() { return allTerms; }
    public LiveData<List<CourseEntity>> getAllCourses() { return allCourses; }
    public LiveData<List<AssessmentEntity>> getAllAssessments() { return allAssessments; }

    public LiveData<List<CourseEntity>> getAssociatedCourses(int termID) { return associatedCourses; }
    public LiveData<List<AssessmentEntity>> getAssociatedAssessments(int courseID) { return associatedAssessments; }

    //ASYNC TASKS
    //must be static so it doesn't have a reference to the repository to itself
    //CONSTRUCTOR

    //TERM ACTIONS
    //INSERT TERM
    private static class InsertTermAsyncTask extends AsyncTask<TermEntity, Void, Void> {
        private TermDao termDao;
        private InsertTermAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }
        @Override
        protected Void doInBackground(TermEntity... terms) {
            termDao.insertTerm(terms[0]);
            return null;
        }
    }
    //UPDATE TERM
    private static class UpdateTermAsyncTask extends AsyncTask<TermEntity, Void, Void> {
        private TermDao termDao;
        private UpdateTermAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }
        @Override
        protected Void doInBackground(TermEntity... terms) {
            termDao.updateTerm(terms[0]);
            return null;
        }
    }
    //DELETE TERM
    private static class DeleteTermAsyncTask extends AsyncTask<TermEntity, Void, Void> {
        private TermDao termDao;
        private DeleteTermAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }
        @Override
        protected Void doInBackground(TermEntity... terms) {
            termDao.deleteTerm(terms[0]);
            return null;
        }
    }
    //DELETE ALL
    private static class DeleteAllTermAsyncTask extends AsyncTask<Void, Void, Void> {
        private TermDao termDao;
        private DeleteAllTermAsyncTask(TermDao termDao) {
            this.termDao = termDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            termDao.deleteAllTerms();
            return null;
        }
    }
    //COURSE FUNCTIONS
    //
    //
    //INSERT COURSE
    private static class InsertCourseAsyncTask extends AsyncTask<CourseEntity, Void, Void> {
        private CourseDao courseDao;
        private InsertCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }
        @Override
        protected Void doInBackground(final CourseEntity... courses) {
            courseDao.insertCourse(courses[0]);
            return null;
        }
    }
    //UPDATE COURSE
    private static class UpdateCourseAsyncTask extends AsyncTask<CourseEntity, Void, Void> {
        private CourseDao courseDao;
        private UpdateCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }
        @Override
        protected Void doInBackground(final CourseEntity... courses) {
            courseDao.updateCourse(courses[0]);
            return null;
        }
    }
    //DELETE COURSE
    private static class DeleteCourseAsyncTask extends AsyncTask<CourseEntity, Void, Void> {
        private CourseDao courseDao;
        private DeleteCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }
        @Override
        protected Void doInBackground(final CourseEntity... courses) {
            courseDao.deleteCourse(courses[0]);
            return null;
        }
    }
    //ASSESSMENT FUNCTIONS
    //
    //
    //INSERT ASSESSMENT
    private static class InsertAssessmentAsyncTask extends AsyncTask<AssessmentEntity, Void, Void> {
        private AssessmentDao assessmentDao;
        private InsertAssessmentAsyncTask(AssessmentDao assessmentDao) { this.assessmentDao = assessmentDao; }
        @Override
        protected Void doInBackground(final AssessmentEntity... assessments) {
            assessmentDao.insertAssessment(assessments[0]);
            return null;
        }
    }
    //UPDATE ASSESSMENT
    private static class UpdateAssessmentAsyncTask extends AsyncTask<AssessmentEntity, Void, Void> {
        private AssessmentDao assessmentDao;
        private UpdateAssessmentAsyncTask(AssessmentDao assessmentDao) { this.assessmentDao = assessmentDao; }
        @Override
        protected Void doInBackground(final AssessmentEntity... assessments) {
            assessmentDao.updateAssessment(assessments[0]);
            return null;
        }
    }
    //DELETE ASSESSMENT
    private static class DeleteAssessmentAsyncTask extends AsyncTask<AssessmentEntity, Void, Void> {
        private AssessmentDao assessmentDao;
        private DeleteAssessmentAsyncTask(AssessmentDao assessmentDao) { this.assessmentDao = assessmentDao; }
        @Override
        protected Void doInBackground(final AssessmentEntity... assessments) {
            assessmentDao.deleteAssessment(assessments[0]);
            return null;
        }
    }

















}

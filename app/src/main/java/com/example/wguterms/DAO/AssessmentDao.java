package com.example.wguterms.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wguterms.Entities.AssessmentEntity;

import java.util.List;
@Dao
public interface AssessmentDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(AssessmentEntity assessmentEntity);

    @Update
    void updateAssessment(AssessmentEntity assessmentEntity);

    @Delete
    void deleteAssessment(AssessmentEntity assessmentEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllAssessments(List<AssessmentEntity> assessments);


    @Query("SELECT * FROM assessments WHERE assessment_id = :assessment_id")
    AssessmentEntity getAssessmentByID(int assessment_id);

    @Query("SELECT * FROM assessments WHERE course_id = :course_id")
    LiveData<List<AssessmentEntity>> getAssociatedAssessments(int course_id);

    @Query("SELECT * FROM assessments ORDER BY assessment_date DESC")
    LiveData<List<AssessmentEntity>> getAllAssessments();

    @Query("DELETE FROM assessments")
    int deleteAllAssessments();

    @Query("SELECT COUNT(*) FROM assessments")
    int getAssessmentCount();



}

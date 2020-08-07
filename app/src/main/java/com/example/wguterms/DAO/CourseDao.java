package com.example.wguterms.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wguterms.Entities.CourseEntity;

import java.util.List;
@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(CourseEntity courseEntity);

    @Update
    void updateCourse(CourseEntity courseEntity);

    @Delete
    void deleteCourse(CourseEntity courseEntity);

    //Used for SampleData
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllCourses(List<CourseEntity> courses);


    @Query("SELECT * FROM courses WHERE course_id = :course_id")
    CourseEntity getCourseByID(int course_id);

    @Query("SELECT * FROM courses WHERE term_id = :term_id")
    LiveData<List<CourseEntity>> getAssociatedCourses(int term_id);

    @Query("SELECT * FROM courses ORDER BY course_start_date DESC")
    LiveData<List<CourseEntity>> getAllCourses();

    @Query("DELETE FROM courses")
    int deleteAllCourses();

    @Query("SELECT COUNT(*) FROM courses")
    int getCourseCount();


}

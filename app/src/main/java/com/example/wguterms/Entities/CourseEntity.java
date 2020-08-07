package com.example.wguterms.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("term_id")}, tableName = "courses", foreignKeys = @ForeignKey(entity = TermEntity.class,
                parentColumns = "term_id", childColumns = "term_id", onDelete = ForeignKey.CASCADE))
public class CourseEntity {


    @PrimaryKey(autoGenerate = true)
    private int course_id;
    private String course_title;
    private String course_start_date;
    private String course_end_date;
    private String course_status;
    private int term_id;
    private String mentor_name;
    private String mentor_phone;
    private String mentor_email;
    private String course_notes;

    public CourseEntity(String course_title, String course_start_date, String course_end_date, String course_status, int term_id, String mentor_name, String mentor_phone, String mentor_email, String course_notes) {
        this.course_title = course_title;
        this.course_start_date = course_start_date;
        this.course_end_date = course_end_date;
        this.course_status = course_status;
        this.term_id = term_id;
        this.mentor_name = mentor_name;
        this.mentor_phone = mentor_phone;
        this.mentor_email = mentor_email;
        this.course_notes = course_notes;
    }


    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public String getCourse_start_date() {
        return course_start_date;
    }

    public void setCourse_start_date(String course_start_date) {
        this.course_start_date = course_start_date;
    }

    public String getCourse_end_date() {
        return course_end_date;
    }

    public void setCourse_end_date(String course_end_date) {
        this.course_end_date = course_end_date;
    }

    public String getCourse_status() {
        return course_status;
    }

    public void setCourse_status(String course_status) {
        this.course_status = course_status;
    }

    public int getTerm_id() {
        return term_id;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }

    public String getMentor_name() {
        return mentor_name;
    }

    public void setMentor_name(String mentor_name) {
        this.mentor_name = mentor_name;
    }

    public String getMentor_phone() {
        return mentor_phone;
    }

    public void setMentor_phone(String mentor_phone) {
        this.mentor_phone = mentor_phone;
    }

    public String getMentor_email() {
        return mentor_email;
    }

    public void setMentor_email(String mentor_email) {
        this.mentor_email = mentor_email;
    }

    public String getCourse_notes() {
        return course_notes;
    }

    public void setCourse_notes(String course_notes) {
        this.course_notes = course_notes;
    }
}


package com.example.wguterms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wguterms.Adapters.AssessmentAdapter;
import com.example.wguterms.Entities.AssessmentEntity;
import com.example.wguterms.ViewModel.AssessmentViewModel;
import com.example.wguterms.ViewModel.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddEditCourseActivity extends AppCompatActivity {

    EditText editTextCourseTitle;
    EditText editCourseStartDate;
    EditText editCourseEndDate;
    DatePickerDialog picker;
    EditText editTextCourseStatus;
    EditText editCourseMentorName;
    EditText editCourseMentorPhone;
    EditText editCourseMentorEmail;
    EditText editCourseNotes;

    String courseId;
    int assessmentCourtId;

    public static int assessmentCount;
    private CourseViewModel courseViewModel;
    private AssessmentViewModel assessmentViewModel;

    public static final int ADD_ASSESSMENT_REQUEST = 1;
    public static final int EDIT_ASSESSMENT_REQUEST = 2;

    //These are the extras that will be sent back to the main activity as part of the intent
    public static final String EXTRA_COURSE_ID =
            "com.example.wguterms.EXTRA_COURSE_ID";
    public static final String EXTRA_COURSE_TITLE =
            "com.example.wguterms.EXTRA_COURSE_TITLE";
    public static final String EXTRA_COURSE_START_DATE =
            "com.example.wguterms.EXTRA_COURSE_START_DATE";
    public static final String EXTRA_COURSE_END_DATE =
            "com.example.wguterms.EXTRA_COURSE_END_DATE";
    public static final String EXTRA_COURSE_STATUS =
            "com.example.wguterms.EXTRA_COURSE_STATUS";
    public static final String EXTRA_COURSE_MENTOR_NAME =
            "com.example.wguterms.EXTRA_COURSE_MENTOR_NAME";
    public static final String EXTRA_COURSE_MENTOR_PHONE =
            "com.example.wguterms.EXTRA_COURSE_MENTOR_PHONE";
    public static final String EXTRA_COURSE_MENTOR_EMAIL =
            "com.example.wguterms.EXTRA_COURSE_MENTOR_EMAIL";
    public static final String EXTRA_COURSE_TERM_ID =
            "com.example.wguterms.EXTRA_COURSE_TERM_ID";
    public static final String EXTRA_COURSE_NOTES =
            "com.example.wguterms.EXTRA_COURSE_NOTES";

    int day;
    int month;
    int year;
    int day2;
    int month2;
    int year2;
    Calendar cal = Calendar.getInstance();
    Calendar cal2 = Calendar.getInstance();

    Long mills;
    Long mills2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        assessmentCourtId = -1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);


        editTextCourseTitle = findViewById(R.id.course_text_title);
        editCourseStartDate = findViewById(R.id.course_start_date);
        editCourseEndDate = findViewById(R.id.course_end_date);
        editTextCourseStatus = findViewById(R.id.course_status);
        editCourseMentorName = findViewById(R.id.course__mentor_name);
        editCourseMentorPhone = findViewById(R.id.course_mentor_phone);
        editCourseMentorEmail = findViewById(R.id.course_mentor_email);
        editCourseNotes = findViewById(R.id.course_notes);


        editCourseStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calStart = Calendar.getInstance();
                int dayStart = calStart.get(Calendar.DAY_OF_MONTH);
                int monthStart = calStart.get(Calendar.MONTH);
                int yearStart = calStart.get(Calendar.YEAR);

                // date picker dialog
                picker = new DatePickerDialog(AddEditCourseActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editCourseStartDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, yearStart, monthStart, dayStart);
                picker.show();


//                Calendar cal = Calendar.getInstance();
//                String stringDate = editCourseStartDate.getText().toString();
//                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//                Date date = null;
//                try {
//                    date = sdf.parse(stringDate);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                cal.setTime(date);
//                mills = cal.getTimeInMillis();
//                day = cal.get(Calendar.DAY_OF_MONTH);
//                month = cal.get(Calendar.MONTH);
//                year = cal.get(Calendar.YEAR);
//                cal.set(year, month, day, 0, 0);
//                mills = cal.getTimeInMillis();
//                Log.e("value of mills", String.valueOf(mills));


            }
        });

        editCourseEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calEnd = Calendar.getInstance();
                int dayEnd = calEnd.get(Calendar.DAY_OF_MONTH);
                int monthEnd = calEnd.get(Calendar.MONTH);
                int yearEnd = calEnd.get(Calendar.YEAR);
                // date picker dialog

                picker = new DatePickerDialog(AddEditCourseActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editCourseEndDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, yearEnd, monthEnd, dayEnd);
                picker.show();

//                Calendar cal2 = Calendar.getInstance();
//                String stringDateEnd = editCourseEndDate.getText().toString();
//                SimpleDateFormat sdfEnd = new SimpleDateFormat("MM/dd/yyyy");
//                Date dateEnd = null;
//                try {
//                    dateEnd = sdfEnd.parse(stringDateEnd);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                cal2.setTime(dateEnd);
//                mills2 = cal2.getTimeInMillis();
//                day2 = cal2.get(Calendar.DAY_OF_MONTH);
//                month2 = cal2.get(Calendar.MONTH);
//                year2 = cal2.get(Calendar.YEAR);
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_COURSE_ID)) {
            setTitle("Edit Course");
            editTextCourseTitle.setText(intent.getStringExtra(EXTRA_COURSE_TITLE));
            editCourseStartDate.setText((CharSequence) intent.getSerializableExtra(EXTRA_COURSE_START_DATE));
            editCourseEndDate.setText((CharSequence) intent.getSerializableExtra(EXTRA_COURSE_END_DATE));
            //for passing later as the assessmentCourseID
            assessmentCourtId = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);
//             termId  = String.valueOf(assessmentCourtId);
            courseId = intent.getStringExtra(intent.getStringExtra(EXTRA_COURSE_ID));
            Log.e("selected course", String.valueOf(assessmentCourtId));
//            //ints need a default value, in this case it is  1
//            //            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
            editTextCourseStatus.setText(intent.getStringExtra(EXTRA_COURSE_STATUS));
            editCourseMentorName.setText(intent.getStringExtra(EXTRA_COURSE_MENTOR_NAME));
            editCourseMentorPhone.setText(intent.getStringExtra(EXTRA_COURSE_MENTOR_PHONE));
            editCourseMentorEmail.setText(intent.getStringExtra(EXTRA_COURSE_MENTOR_EMAIL));
            editCourseNotes.setText(intent.getStringExtra(EXTRA_COURSE_NOTES));
        } else {
            setTitle("Add Course");
            RelativeLayout relativeLayout3 = (RelativeLayout) findViewById(R.id.add_assessment_container);
            relativeLayout3.setVisibility(View.INVISIBLE);
            TextView swipeToDelete = findViewById(R.id.swipe_to_delete);
            swipeToDelete.setVisibility(View.INVISIBLE);
        }
        //FAB stuff
        FloatingActionButton buttonAddTerm = findViewById(R.id.button_add_assessment);
        buttonAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEditCourseActivity.this, AddEditAssessmentActivity.class);
                startActivityForResult(intent, ADD_ASSESSMENT_REQUEST);
            }
        });
        //Assessment RecyclerView
        RecyclerView recyclerView = findViewById(R.id.associated_assessments_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //set to true if you know the RecyclerView won't change, it would be more efficient
        recyclerView.setHasFixedSize(true);
        final AssessmentAdapter adapter = new AssessmentAdapter();
        //by default, list in the adapter is empty, must be updated in onChanged
        recyclerView.setAdapter(adapter);
        assessmentViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory
                        .getInstance(this.getApplication()))
                .get(AssessmentViewModel.class);

        //viewmodel live data observer
        final int finalId = assessmentCourtId;
        assessmentViewModel.getAllAssessments().observe(this, new Observer<List<AssessmentEntity>>() {
            @Override
            public void onChanged(List<AssessmentEntity> assessments) {
                List<AssessmentEntity> assessmentsInCourse = new ArrayList<>();
                for (AssessmentEntity a : assessments) { //each course in courses will have a turn to be c
                    if (a.getCourse_id() == finalId) { //if the term assessmentCourtId in the course matches the class-level assessmentCourtId variable..
                        assessmentsInCourse.add(a); //add the course to coursesInTerm
                    }
                }
                //update recyclerview
                adapter.setAssessments(assessmentsInCourse);
            }
//            }
        });
        //on swipe delete term
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            //drag and drop, not used in this app
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                assessmentViewModel.deleteAssessment(adapter.getAssessmentAt(viewHolder.getAdapterPosition()));
                Toast.makeText(AddEditCourseActivity.this, "Assessment deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        //setting the adapter fromAssessmentAdapter
        adapter.setOnItemClickListener(new AssessmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AssessmentEntity assessment) {
                RecyclerView.ViewHolder viewHolder;
                Intent intent = new Intent(AddEditCourseActivity.this, AddEditAssessmentActivity.class);
                intent.putExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_ID, assessment.getAssessment_id());
                intent.putExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_TITLE, assessment.getAssessment_name());
                intent.putExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_DATE, assessment.getAssessment_date());
                intent.putExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_TYPE, assessment.getAssessment_type());
                startActivityForResult(intent, EDIT_ASSESSMENT_REQUEST);
            }
        });
    }
    //get info from the editText and the DatePicker
    //check for empty values
    private void saveCourse() {
        String title = editTextCourseTitle.getText().toString();
        String start = editCourseStartDate.getText().toString();
        String end = editCourseEndDate.getText().toString();
        String status = editTextCourseStatus.getText().toString();
        String mentorName = editCourseMentorName.getText().toString();
        String mentorPhone = editCourseMentorPhone.getText().toString();
        String mentorEmail = editCourseMentorEmail.getText().toString();
        String notes = editCourseNotes.getText().toString();

        if (title.trim().isEmpty() || start.trim().isEmpty() || end.trim().isEmpty() ||
                status.trim().isEmpty() || mentorName.trim().isEmpty() ||
                mentorPhone.trim().isEmpty() || mentorEmail.trim().isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        //adding the EXTRAS to the intent to pass to mainActivity
        Intent data = new Intent();
        data.putExtra(EXTRA_COURSE_TITLE, title);
        data.putExtra(EXTRA_COURSE_START_DATE, start);
        data.putExtra(EXTRA_COURSE_END_DATE, end);
        data.putExtra(EXTRA_COURSE_STATUS, status);
        data.putExtra(EXTRA_COURSE_MENTOR_NAME, mentorName);
        data.putExtra(EXTRA_COURSE_MENTOR_PHONE, mentorPhone);
        data.putExtra(EXTRA_COURSE_MENTOR_EMAIL, mentorEmail);
        data.putExtra(EXTRA_COURSE_NOTES, notes);

        //added in part 9 of COF
        //part of getting assessmentCourtId for Edit
        int id = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_COURSE_ID, id);
        } else
            Log.e("save course method", "something wrong with course assessmentCourtId ");
        //rest in in main

        //whether activity was OK or not. for example,
        //leaving activity using back button is not successful
        //clicking save will be okay
        setResult(RESULT_OK, data);
        finish();

        //If form is not empty accept input and add a term to the database
        //in option in developing is to create a viewmodel variable in this act[
        //  ivity and use it
        //to use db  operations here
        //atm the moment, addTerm does not have to retrieve all the terms

        //in Coding In Flow, the inputs from saveTerm() here are instead sent back to
        // main where they are processed there, to avoid having a viewModel here.
        //To achieve this, startActivityForResult is used, this starts in the main activity,
        //then actions are taken in this activity then sent back to main. This method
        // uses intent extras, to do so you need "keys", the best practice is to create these
        //keys as constants
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_course_menu, menu);
        if (editTextCourseTitle.getText().toString().isEmpty()) {
            // menu.findItem(R.assessmentCourtId.delete_course).setVisible(false);
            menu.findItem(R.id.alert_course).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_course:
                saveCourse();
                return true;
            case R.id.share_course:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editCourseNotes.getText().toString());
                // (Optional) Here we're setting the title of the content
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Course notes for: " + editTextCourseTitle.getText().toString());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;

            case R.id.alert_course:
                setAlert();
                setAlertEnd();
                Toast.makeText(this, "Alarms set for start and end dates", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void setAlert() {

        //create calendar from text string
        //call calendar.getTimeInMills
        //public Long  mills = Long.parseLong(findViewById(R.id.course_start_date));
        String stringDate = "default";
        stringDate = editCourseStartDate.getText().toString();
        Log.e("stringDate as string", stringDate);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date date = null;
        //need to check if this date creation is working
        Log.e("string date", stringDate);
        try {
            date = sdf.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(date);
        mills = cal.getTimeInMillis();
        Log.e("current Time", String.valueOf(System.currentTimeMillis() ));
        Log.e("calAfterAssign", String.valueOf(mills));

        Intent intent = new Intent(AddEditCourseActivity.this, MyReceiver.class);
        intent.putExtra("key", "Course starts today");
        PendingIntent sender = PendingIntent.getBroadcast(AddEditCourseActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Log.e("mill in setAlert", String.valueOf(mills));
        alarmManager.set(AlarmManager.RTC_WAKEUP, mills, sender);
    }

    public void setAlertEnd() {
        //The End Date alarm
        String stringDateEnd = "default";
        stringDateEnd = editCourseEndDate.getText().toString();
        Log.e("stringDate as string", stringDateEnd);
        SimpleDateFormat sdfEnd = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date dateEnd = null;
        //need to check if this date creation is working
        Log.e("string date", stringDateEnd);
        try {
            dateEnd = sdfEnd.parse(stringDateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal2.setTime(dateEnd);
        mills2 = cal2.getTimeInMillis();
        Log.e("End current Time", String.valueOf(System.currentTimeMillis() ));
        Log.e("End calAfterAssign", String.valueOf(mills2));

        Intent intentEnd = new Intent(AddEditCourseActivity.this, MyReceiver.class);
        intentEnd.putExtra("key", "Course ends today");
        PendingIntent senderEnd = PendingIntent.getBroadcast(AddEditCourseActivity.this, 1, intentEnd, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManagerEnd = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Log.e("mill in setAlert", String.valueOf(mills2));
        alarmManagerEnd.set(AlarmManager.RTC_WAKEUP, mills2, senderEnd);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ASSESSMENT_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_TITLE);
            String date = data.getStringExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_DATE);
            String type = data.getStringExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_TYPE);

            int courseId = assessmentCourtId;
            AssessmentEntity assessment = new AssessmentEntity(title, date, type, courseId);
            assessmentViewModel.insertAssessment(assessment);
            Toast.makeText(this, "Assessment saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_ASSESSMENT_REQUEST && resultCode == RESULT_OK) {
            //get the ID
            int id = data.getIntExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_ID, -1);
            if (id == -1) {
                Toast.makeText(this, " can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_TITLE);
            String date = data.getStringExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_DATE);
            String type = data.getStringExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_TYPE);
            //create new term
            AssessmentEntity assessment = new AssessmentEntity(title, date, type);
            //pass the ID
            assessment.setAssessment_id(id);

            //assign the course ID
            assessment.setCourse_id(assessmentCourtId);
            //call the update term method
            assessmentViewModel.updateAssessment(assessment);
            //ass
            Toast.makeText(this, "Term updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Assessment not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
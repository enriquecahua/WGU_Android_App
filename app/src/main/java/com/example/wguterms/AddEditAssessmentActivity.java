package com.example.wguterms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wguterms.Adapters.AssessmentAdapter;
import com.example.wguterms.Entities.AssessmentEntity;
import com.example.wguterms.ViewModel.AssessmentViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddEditAssessmentActivity extends AppCompatActivity {

    EditText editTextAssessmentTitle;
    EditText editTextAssessmentDate;
    DatePickerDialog picker;
    EditText editTextAssessmentType;
    String assessmentId;
    int id;
    private AssessmentViewModel assessmentViewModel;

    //These are the extras that will be sent back to the AddEditCourse activity as part of the intent
    public static final String EXTRA_ASSESSMENT_ID =
            "com.example.wguterms.EXTRA_ASSESSMENT_ID";

    public static final String EXTRA_ASSESSMENT_TITLE =
            "com.example.wguterms.EXTRA_ASSESSMENT_TITLE";

    public static final String EXTRA_ASSESSMENT_DATE =
            "com.example.wguterms.EXTRA_ASSESSMENT_DATE";

    public static final String EXTRA_ASSESSMENT_TYPE =
            "com.example.wguterms.EXTRA_COURSE_TYPE";

    Calendar cal = Calendar.getInstance();
    Long mills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int id = 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);

        editTextAssessmentTitle = findViewById(R.id.edit_text_assessment_title);
        editTextAssessmentDate = findViewById(R.id.edit_text_assessment_date);
        editTextAssessmentType = findViewById(R.id.edit_text_assessment_type);

        editTextAssessmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
//                String stringDate = editTextAssessmentDate.getText().toString();
//                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//                Date date = null;
//                try {
//                    date = sdf.parse(stringDate);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                cal.setTime(date);
//                mills = cal.getTimeInMillis();
                int  day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                cal.set(year, month, day, 0, 0);
                mills = cal.getTimeInMillis();
                Log.e("value of mills", String.valueOf(mills));
                // date picker dialog
                picker = new DatePickerDialog(AddEditAssessmentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editTextAssessmentDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        setTitle("Add Assessment");

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ASSESSMENT_ID)) {
            setTitle("Edit Assessment");
            editTextAssessmentTitle.setText(intent.getStringExtra(EXTRA_ASSESSMENT_TITLE));
            editTextAssessmentDate.setText(intent.getStringExtra(EXTRA_ASSESSMENT_DATE));
            editTextAssessmentType.setText(intent.getStringExtra(EXTRA_ASSESSMENT_TYPE));
        } else {
            setTitle("Add Assessment");
        }
    }

    private void saveAssessment() {
        String title = editTextAssessmentTitle.getText().toString();
        String date = editTextAssessmentDate.getText().toString();
        String type = editTextAssessmentType.getText().toString();
        if (title.trim().isEmpty() || date.trim().isEmpty() || type.trim().isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_ASSESSMENT_TITLE, title);
        data.putExtra(EXTRA_ASSESSMENT_DATE, date);
        data.putExtra(EXTRA_ASSESSMENT_TYPE, type);

        //added in part 9 of COF
        //part of getting assessmentCourtId for Edit
        int id = getIntent().getIntExtra(EXTRA_ASSESSMENT_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ASSESSMENT_ID, id);
        }

        //whether activity was OK or not. for example,
        //leaving activity using back button is not successful
        //clicking save will be okay
        setResult(RESULT_OK, data);
        finish();

        //If form is not empty accept input and add a term to the database
        //in option in developing is to create a viewmodel variable in this activity and use it
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
        menuInflater.inflate(R.menu.add_assessment_menu, menu);
        if (editTextAssessmentTitle.getText().toString().isEmpty()) {
            menu.findItem(R.id.alert_assessment).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_assessment:
                saveAssessment();
                return true;
            case R.id.alert_assessment:
                setAlert();

                Toast.makeText(this, "Alarms set for asssessment", Toast.LENGTH_LONG).show();
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
        stringDate = editTextAssessmentDate.getText().toString();
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

        Intent intent = new Intent(AddEditAssessmentActivity.this, MyReceiver.class);
        intent.putExtra("key", "Assessment ' today");
        PendingIntent sender = PendingIntent.getBroadcast(AddEditAssessmentActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Log.e("mill in setAlert", String.valueOf(mills));
        alarmManager.set(AlarmManager.RTC_WAKEUP, mills, sender);
    }
}
































































package com.example.wguterms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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

import com.example.wguterms.Adapters.CourseAdapter;
import com.example.wguterms.Adapters.TermAdapter;
import com.example.wguterms.Entities.CourseEntity;
import com.example.wguterms.Entities.TermEntity;
import com.example.wguterms.ViewModel.CourseViewModel;
import com.example.wguterms.ViewModel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddEditTermActivity extends AppCompatActivity {
    public static int courseCount;

    //used to distingush requests, because you can se startActivityOnRequest for different activities
    public static final int ADD_COURSE_REQUEST = 1;
    public static final int EDIT_COURSE_REQUEST = 2;

    //These are the extras that will be sent back to the main activity as part of the intent
    public static final String EXTRA_ID =
            "com.example.wguterms.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.wguterms.EXTRA_TITLE";
    public static final String EXTRA_START_DATE =
            "com.example.wguterms.EXTRA_START_DATE";
    public static final String EXTRA_END_DATE =
            "com.example.wguterms.EXTRA_END_DATE";

    EditText editTextTermTitle;
    EditText editStartDate;
    EditText editEndDate;
    DatePickerDialog picker;
    private CourseViewModel courseViewModel;
    private TermViewModel termViewModel;

    TermAdapter termAdapter = new TermAdapter();

    String termId;
    int courseTermId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        termViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory
                        .getInstance(this.getApplication()))
                .get(TermViewModel.class);

        //viewModel live data observer
        termViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(List<TermEntity> terms) {
                //everytime onChanged is triggered, everytime table is changed,
                //term adapter should be updated with the new list of terms
                termAdapter.setTerms(terms);

            }
        });
//        String termId;
        courseTermId = -1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        //instantiate the title item
        editTextTermTitle = findViewById(R.id.edit_text_title);

        //START DATE: DATE PICKER
        editStartDate = findViewById(R.id.edit_text_start_date);
        editStartDate.setInputType(InputType.TYPE_NULL);
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddEditTermActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editStartDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        //END DATE: DATE PICKER
        editEndDate = findViewById(R.id.edit_text_end_date);
        editEndDate.setInputType(InputType.TYPE_NULL);
        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddEditTermActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editEndDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        //ADD X IN MENU TO CLOSE AND RETURN TO THE MAIN ACTIVITY
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        // get the intent and then change title text depending
        // whether is is add or an edit screen
        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Term");

            editTextTermTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editStartDate.setText((CharSequence) intent.getSerializableExtra(EXTRA_START_DATE));
            editEndDate.setText((CharSequence) intent.getSerializableExtra(EXTRA_END_DATE));

            //this termId is for later passing on to courses
//            termId = intent.getStringExtra(intent.getStringExtra(EXTRA_ID));

            courseTermId = getIntent().getIntExtra(EXTRA_ID, -1);
//             termId  = String.valueOf(assessmentCourtId);
            Log.e("selected term", String.valueOf(courseTermId));


        } else {
            setTitle("Add Term");
            RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.add_courses_container);
            relativeLayout2.setVisibility(View.INVISIBLE);
            TextView swipeToDelete = findViewById(R.id.swipe_to_delete);
            swipeToDelete.setVisibility(View.INVISIBLE);
        }


        //COURSE RECYCLERVIEW HOLDER

        RecyclerView recyclerView = findViewById(R.id.associated_courses_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //set to true if you know the RecyclerView won't change, it would be more efficient
        recyclerView.setHasFixedSize(true);

        final CourseAdapter adapter = new CourseAdapter();
        //by default, list in the adapter is empty, must be updated in onChanged
        recyclerView.setAdapter(adapter);

        //added from part 5 of Coding in Flow
        //system knows when a new ViewModel is needed
        courseViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory
                        .getInstance(this.getApplication()))
                .get(CourseViewModel.class);



        //viewModel live data observer
        final int finalId = courseTermId;
        courseViewModel.getAllCourses().observe(this, new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(List<CourseEntity> courses) {

                List<CourseEntity> coursesInTerm = new ArrayList<>(); //create a new list
//                int intId = Integer.parseInt(termId); //convert the class-level String variable assessmentCourtId to an int so I can compare later on
                for (CourseEntity c : courses) { //each course in courses will have a turn to be c
                    if (c.getTerm_id() == finalId) { //if the term assessmentCourtId in the course matches the class-level assessmentCourtId variable..
                        coursesInTerm.add(c); //add the course to coursesInTerm
                    }
                }
                adapter.setCourses(coursesInTerm);//set the courses in the adapter (this line used to be adapter.setCourses(courses)
                courseCount = coursesInTerm.size();
                //everytime onChanged is triggered, everytime table is changed,
                //term adapter should be updated with the new list of terms
                //    adapter.setCourses(courses);
            }
        });
        //END COURSE RECYCLERVIEW STUFF



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
                courseViewModel.deleteCourse(adapter.getCourseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(AddEditTermActivity.this,  "Course deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        //setting the adapter from courseAdapter
        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CourseEntity course) {
                RecyclerView.ViewHolder viewHolder;
                Intent intent = new Intent(AddEditTermActivity.this, AddEditCourseActivity.class);
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID, course.getCourse_id());
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_TITLE, course.getCourse_title());
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_START_DATE, course.getCourse_start_date());
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_END_DATE, course.getCourse_end_date());
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_STATUS, course.getCourse_status());
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_NAME, course.getMentor_name());
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_PHONE, course.getMentor_phone());
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_EMAIL, course.getMentor_email());
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_TERM_ID, course.getTerm_id());
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_NOTES, course.getCourse_notes());

                startActivityForResult(intent, EDIT_COURSE_REQUEST);

            }
        });

        FloatingActionButton buttonAddCourse = findViewById(R.id.button_add_course);
        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditTermActivity.this, AddEditCourseActivity.class);
                startActivityForResult(intent, ADD_COURSE_REQUEST);
            }
        });
    }

    //get info from the editText and the DatePicker
    //check for empty values
    private void saveTerm() {
        String title = editTextTermTitle.getText().toString();
        String start = editStartDate.getText().toString();
        String end = editEndDate.getText().toString();

        if (title.trim().isEmpty() || start.trim().isEmpty() || end.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and dates", Toast.LENGTH_SHORT).show();
            return;
        }

        //adding the EXTRAS to the intent to pass to mainActivity
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_START_DATE, start);
        data.putExtra(EXTRA_END_DATE, end);

        //added in part 9 of COF
        //part of getting assessmentCourtId for Edit
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        //rest in in main

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


    //add the menu to the activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_term_menu, menu);
        if(editTextTermTitle.getText().toString().isEmpty()){
            menu.findItem(R.id.delete_term).setVisible(false);
        }
        return true;
    }
    //handle clicks to the menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_term:
                saveTerm();
                return true;
            case R.id.delete_term:

                if (courseCount == 0) {

                    termViewModel.getAllTerms().observe(this, new Observer<List<TermEntity>>() {
                        @Override
                        public void onChanged(@Nullable final List<TermEntity> terms) {
                            // Update the cached copy of the words in the adapter.
//                            List<TermAdapter> newTerms = new ArrayList<>();
                            for (TermEntity t : terms)
                                if (t.getTerm_id() == getIntent().getIntExtra(EXTRA_ID, 01))
                                    termViewModel.deleteTerm(t);

                            Toast.makeText(getApplicationContext(), "Term Deleted", Toast.LENGTH_LONG).show();
//                        // make a toast
//                        //adapter.setWords(words);
                            finish();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Can't delete a term with courses", Toast.LENGTH_LONG).show();// make another toast
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //this is where we can see which add_term_request is being used
        if (requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_TITLE);
            String start = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_START_DATE);
            String end = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_END_DATE);
            String status = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_STATUS);
            String mentorName = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_NAME);
            String mentorPhone = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_PHONE);
            String mentorEmail = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_EMAIL);
            String notes = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_NOTES);
            // ^^ ints require a default value
//
            CourseEntity course = new CourseEntity(title, start, end, status,courseTermId, mentorName, mentorPhone, mentorEmail,notes);
            courseViewModel.insertCourse(course);
            Toast.makeText(this, "Course saved", Toast.LENGTH_SHORT).show();

        }else if (requestCode == EDIT_COURSE_REQUEST && resultCode == RESULT_OK) {
            //get the ID
            int id = data.getIntExtra(AddEditCourseActivity.EXTRA_COURSE_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Course can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_TITLE);
            String start = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_START_DATE);
            String end = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_END_DATE);
            String status = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_STATUS);
            String mentorName = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_NAME);
            String mentorPhone = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_PHONE);
            String mentorEmail = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_MENTOR_EMAIL);
            String notes = data.getStringExtra(AddEditCourseActivity.EXTRA_COURSE_NOTES);

            //getting the term assessmentCourtId to add to the constructor
//            int courseTermId = courseid;

            Log.e("course term Id", String.valueOf(courseTermId) );
            //create new course to pass
            CourseEntity course = new CourseEntity(title, start, end, status,courseTermId, mentorName, mentorPhone, mentorEmail,notes);
            course.setCourse_id(id);

            courseViewModel.updateCourse(course);
            Toast.makeText(this, "Course updated", Toast.LENGTH_SHORT).show();
        }

        else{
            //on activityResult will also be triggered if you leave add activity using Back button
            Toast.makeText(this, "No changed applied", Toast.LENGTH_SHORT).show();

        }
    }
}
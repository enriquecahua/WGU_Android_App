package com.example.wguterms;

import android.content.Intent;
import android.os.Bundle;

import com.example.wguterms.Adapters.CourseAdapter;
import com.example.wguterms.Adapters.TermAdapter;
import com.example.wguterms.Entities.TermEntity;
import com.example.wguterms.ViewModel.CourseViewModel;
import com.example.wguterms.ViewModel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    //used for startActivityForResult()
    public static final int ADD_TERM_REQUEST = 1;

    public static final int EDIT_TERM_REQUEST = 2;

    private TermViewModel termViewModel;
    //implementing courseViewModel to check for associated courses
//    private CourseViewModel courseViewModel;
//    private CourseAdapter courseAdapter = new CourseAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = findViewById(R.assessmentCourtId.toolbar);
//        setSupportActionBar(toolbar);

        //added in part 6 of coding in flow second half
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //set to true if you know the RecyclerView won't change, it would be more efficient
        recyclerView.setHasFixedSize(true);

        final TermAdapter adapter = new TermAdapter();
        //by default, list in the adapter is empty, must be updated in onChanged
        recyclerView.setAdapter(adapter);

        //added from part 5 of Coding in Flow
        //system knows when a new ViewModel is needed

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
                adapter.setTerms(terms);

//                //update RecyclerViewer
//                Toast.makeText(MainActivity.this, "OnChanged", Toast.LENGTH_SHORT).show();
            }
        });


        //FAB stuff
        FloatingActionButton buttonAddTerm = findViewById(R.id.button_add_term);
        buttonAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditTermActivity.class);
                startActivityForResult(intent, ADD_TERM_REQUEST);

            }
        });


        //DISABLE SWIPE TO DELETE BECAUSE THERE WAS NO CHECK FOR COURSES IMPLEMENTED HERE
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
//                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//
//            //meant for drag and drop, not used
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            //left and right swipes
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                //getterAt was created in adapter class to pass the selected Term for to delete
////                termViewModel.deleteTerm(adapter.getTermAt(viewHolder.getAdapterPosition()));
//                Toast.makeText(MainActivity.this, "Delete Term on Details page", Toast.LENGTH_SHORT).show();
//            }
//        }).attachToRecyclerView(recyclerView);



        adapter.setOnItemClickListener(new TermAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TermEntity term) {
                RecyclerView.ViewHolder viewHolder;
                Intent intent = new Intent(MainActivity.this, AddEditTermActivity.class);
                intent.putExtra(AddEditTermActivity.EXTRA_ID, term.getTerm_id());
                intent.putExtra(AddEditTermActivity.EXTRA_TITLE, term.getTerm_title());
                intent.putExtra(AddEditTermActivity.EXTRA_START_DATE, term.getTerm_start_date());
                intent.putExtra(AddEditTermActivity.EXTRA_END_DATE, term.getTerm_end_date());


                startActivityForResult(intent, EDIT_TERM_REQUEST);

                //
//                int assessmentCourtId = adapter.getTermAt(viewHolder.getAdapterPosition()).getId();
            }
        });


        setTitle("WGU Terms");
    }


    //option bar settings

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.main_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.assessmentCourtId.delete_all_terms:
//                termViewModel.deleteAllTerms();
//                Toast.makeText(this, "All terms deleted", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //this is where we can see which add_term_request is being used
        if (requestCode == ADD_TERM_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditTermActivity.EXTRA_TITLE);
            String start = data.getStringExtra(AddEditTermActivity.EXTRA_START_DATE);
            String end = data.getStringExtra(AddEditTermActivity.EXTRA_END_DATE);
            // ^^ ints require a default value

            TermEntity term = new TermEntity(title, start, end);
            termViewModel.insertTerm(term);
            Toast.makeText(this, "Term saved", Toast.LENGTH_SHORT).show();


        } else if (requestCode == EDIT_TERM_REQUEST && resultCode == RESULT_OK) {
            //get the ID
            int id = data.getIntExtra(AddEditTermActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, " can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditTermActivity.EXTRA_TITLE);
            String start = data.getStringExtra(AddEditTermActivity.EXTRA_START_DATE);
            String end = data.getStringExtra(AddEditTermActivity.EXTRA_END_DATE);


            //create new term
            TermEntity term = new TermEntity(title, start, end);
            //pass the ID
            term.setTerm_id(id);
            //call the update term method
            termViewModel.updateTerm(term);

            Toast.makeText(this, "Term updated", Toast.LENGTH_SHORT).show();
        } else {
            //on activityResult will also be triggered if you leave add activity using Back button
            Toast.makeText(this, "No changed applied", Toast.LENGTH_SHORT).show();
        }
    }


}

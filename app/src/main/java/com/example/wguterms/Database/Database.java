package com.example.wguterms.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.wguterms.DAO.AssessmentDao;
import com.example.wguterms.DAO.CourseDao;
import com.example.wguterms.DAO.TermDao;
import com.example.wguterms.Entities.AssessmentEntity;
import com.example.wguterms.Entities.CourseEntity;
import com.example.wguterms.Entities.TermEntity;

@androidx.room.Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public static Database instance;

    //returns a termDao, later use this method to access to access the dao
    //no body because Room takes care of the code
    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();

    //returns TermDataBase instance
    //synchronized means only one thread at a time can access the method
    //only instance of database in the application

    //only instantiate if null, if not null return current instance
    public static synchronized Database getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    Database.class, "term_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }




//
//ALL ABOVE WAS DONE IN THE INITIAL SETUP OF THE DATABASE ^^^
// EXCEPT FOR .addCallback(), was added after repository was created
// the rest was created after the repository creation



    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private TermDao termDao;
        private CourseDao courseDao;
        private AssessmentDao assessmentDao;

        private PopulateDBAsyncTask(Database db) {
            termDao = db.termDao();
            courseDao = db.courseDao();
            assessmentDao = db.assessmentDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            termDao.insertTerm(new TermEntity("red term", "7/01/2020", "12/31/2020"));
            termDao.insertTerm(new TermEntity("blue term", "1/01/2021", "6/31/2021"));
            termDao.insertTerm(new TermEntity("green term", "7/01/2021", "12/31/2021"));
            termDao.insertTerm(new TermEntity("yellow term", "1/01/2022", "6/31/2022"));
            termDao.insertTerm(new TermEntity("purple term", "7/01/2022", "12/31/2022"));
            termDao.insertTerm(new TermEntity("orange term", "1/01/2023", "6/31/2020"));


            courseDao.insertCourse(new CourseEntity("Math","7/01/2020","8/01/2020",
                    "active",1,"Sarah Hyland", "555-222-3333", "sarahhyland@wgu.edu","test notes"));

            courseDao.insertCourse(new CourseEntity("History","9/01/2020","10/01/2020",
                    "active",1,"Abigail Grant", "333-444-5555", "abgrant@wgu.edu","sample notes"));

            courseDao.insertCourse(new CourseEntity("Digital Escapism","11/01/2020","12/01/2020",
                    "active",1,"Professor Professerson", "222-333-8775", "prof@wgu.edu","sample test"));

            courseDao.insertCourse(new CourseEntity("Beginning of Western Society","1/01/2021","2/01/2021",
                    "active",2,"Professor Professerson", "222-333-8775", "prof@wgu.edu","note stuff"));

            courseDao.insertCourse(new CourseEntity("End of Western Society","3/01/2021","4/01/2021",
                    "active",2,"Professor Professerson", "222-333-8775", "prof@wgu.edu","testing stuff"));

            courseDao.insertCourse(new CourseEntity("Daffy Duck","5/01/2021","6/01/2021",
                    "active",3,"Professor Professerson", "222-333-8775", "prof@wgu.edu","the end of notes"));

            courseDao.insertCourse(new CourseEntity("Mickey Dynasty","5/01/2021","6/01/2021",
                    "active",4,"Professor Professerson", "222-333-8775", "prof@wgu.edu","the end of notes"));

            courseDao.insertCourse(new CourseEntity("Return of the Jedi","5/01/2021","6/01/2021",
                    "active",5,"Professor Professerson", "222-333-8775", "prof@wgu.edu","the end of notes"));



            assessmentDao.insertAssessment(new AssessmentEntity("Finale","7/15/2020", "PA",1));
            assessmentDao.insertAssessment(new AssessmentEntity("test","8/15/2020", "PA",1));

            assessmentDao.insertAssessment(new AssessmentEntity("Oracle","7/15/2020", "PA",2));
            assessmentDao.insertAssessment(new AssessmentEntity("CIW","8/15/2020", "PA",2));

            assessmentDao.insertAssessment(new AssessmentEntity("Project +","7/15/2020", "PA",3));
            assessmentDao.insertAssessment(new AssessmentEntity("ITIL","8/15/2020", "PA",3));

            assessmentDao.insertAssessment(new AssessmentEntity("Java","7/15/2020", "PA",4));
            assessmentDao.insertAssessment(new AssessmentEntity("Android","8/15/2020", "PA",4));


            return null;
        }
    }

}








































package edu.sdsu.tvidhate.registerationapp.Helper;

/**
 * Created by tanvi on 3/25/18.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.sdsu.tvidhate.registerationapp.Entity.Course;
import edu.sdsu.tvidhate.registerationapp.Entity.Student;

public class DatabaseHelper extends SQLiteOpenHelper implements ServerConstants
{
    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists " + STUDENT_TABLE +
                "("+STUDENT_RED_ID+" TEXT PRIMARY KEY," +
                ""+STUDENT_FIRST_NAME+" TEXT," +
                ""+STUDENT_LAST_NAME+" TEXT," +
                ""+STUDENT_EMAIL+" TEXT," +
                ""+STUDENT_PASSWORD+" TEXT)");

        sqLiteDatabase.execSQL("create table if not exists " + COURSE_TABLE +
                "("+ COURSE_IID +" TEXT PRIMARY KEY," +
                ""+ COURSE_DESCRIPTION +" TEXT," +
                ""+ COURSE_DEPARTMENT +" TEXT," +
                ""+ COURSE_SUFFIX +" TEXT," +
                ""+ COURSE_BUILDING +" TEXT," +
                ""+ COURSE_START_TIME +" TEXT," +
                ""+ COURSE_MEETING_TYPE +" TEXT," +
                ""+ COURSE_SECTION +" TEXT," +
                ""+ COURSE_END_TIME +" TEXT," +
                ""+ COURSE_ENROLLED +" TEXT," +
                ""+ COURSE_DAYS +" TEXT," +
                ""+ COURSE_PREREQUISITE +" TEXT," +
                ""+ COURSE_TITLE +" TEXT," +
                ""+ COURSE_INSTRUCTOR +" TEXT," +
                ""+ COURSE_SCHEDULE.toString().replace("#","") +" TEXT," +
                ""+ COURSE_UNITS +" TEXT," +
                ""+ COURSE_ROOM +" TEXT," +
                ""+ COURSE_WAITLIST +" TEXT," +
                ""+ COURSE_SEATS +" TEXT," +
                ""+ COURSE_FULL_TITLE +" TEXT," +
                ""+ COURSE_SUBJECT +" TEXT," +
                ""+ COURSE_NO.toString().replace("#","") +" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + COURSE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean insertStudentData(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STUDENT_RED_ID,student.getmRedId());
        contentValues.put(STUDENT_FIRST_NAME,student.getmFirstName());
        contentValues.put(STUDENT_LAST_NAME,student.getmLastName());
        contentValues.put(STUDENT_EMAIL,student.getmEmailId());
        contentValues.put(STUDENT_PASSWORD,student.getmPassword());
        long result = db.insert(STUDENT_TABLE,null ,contentValues);
        //db.close();
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertCourseData(Course course)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COURSE_DESCRIPTION , course.getmDescription());
        contentValues.put(COURSE_DEPARTMENT , course.getmDepartment());
        contentValues.put(COURSE_SUFFIX , course.getmSuffix());
        contentValues.put(COURSE_BUILDING , course.getmBuilding());
        contentValues.put(COURSE_START_TIME , course.getmStartTime());
        contentValues.put(COURSE_MEETING_TYPE , course.getmMeetingType());
        contentValues.put(COURSE_SECTION , course.getmSection());
        contentValues.put(COURSE_END_TIME , course.getmEndTime());
        contentValues.put(COURSE_ENROLLED , course.getmEnrolled());
        contentValues.put(COURSE_DAYS , course.getmDays());
        contentValues.put(COURSE_PREREQUISITE , course.getmPrerequisite());
        contentValues.put(COURSE_TITLE , course.getmTitle());
        contentValues.put(COURSE_IID , course.getmId());
        contentValues.put(COURSE_INSTRUCTOR , course.getmInstructor());
        contentValues.put(COURSE_SCHEDULE.replace("#","") , course.getmScheduleNo());
        contentValues.put(COURSE_UNITS , course.getmUnit());
        contentValues.put(COURSE_ROOM , course.getmRoom());
        contentValues.put(COURSE_WAITLIST , course.getmWaitlist());
        contentValues.put(COURSE_SEATS , course.getmSeats());
        contentValues.put(COURSE_FULL_TITLE , course.getmFullTitle());
        contentValues.put(COURSE_SUBJECT , course.getmSubject());
        contentValues.put(COURSE_NO.replace("#","") , course.getmCourseNo());
        long result = db.insert(COURSE_TABLE,null ,contentValues);
        //db.close();
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getCourse(String courseId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+COURSE_TABLE+" WHERE "+COURSE_IID+"='"+courseId+"'",null);
        return res;
    }

    public Cursor getAllCourses(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+COURSE_TABLE,null);
        return res;
    }

    public Cursor authenticateUser(String emailID, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+STUDENT_TABLE+" WHERE "+STUDENT_EMAIL+"='"+emailID+"'",null);
        //db.close();
        return res;
    }
}

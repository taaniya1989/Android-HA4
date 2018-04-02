package edu.sdsu.tvidhate.registerationapp.Helper;

public interface ServerConstants {
    String SERVER_URL = "https://bismarck.sdsu.edu/registration/";
    String SUBJECT_LIST = "subjectlist";

    String ADD_STUDENT = "addstudent";
    String STUDENT_FIRST_NAME = "firstname";
    String STUDENT_LAST_NAME = "lastname";
    String STUDENT_RED_ID = "redid";
    String STUDENT_PASSWORD = "password";
    String STUDENT_EMAIL = "email";

    String CLASS_IDS_LIST = "classidslist";
    String SUBJECT_ID = "subjectid";
    String LEVEL = "level";
    String START_TIME = "start-time";

    String CLASS_DETAILS = "classdetails";
    String CLASS_ID = "classid";
    String COURSE_ID = "courseid";

    String REGISTER_CLASS = "registerclass";
    String UNREGISTER_CLASS = "unregisterclass";

    //DATABASE CONSTANTS
    String STUDENT_TABLE = "student_details_table";
    String REGISTERED_COURSE_TABLE = "registered_course_table";
    String STUDENT_CLASSES = "studentclasses";

    String WAITLIST_CLASS = "waitlistclass";
    String UNWAITLIST_CLASS = "unwaitlistclass";
}


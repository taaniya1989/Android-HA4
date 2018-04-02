package edu.sdsu.tvidhate.registerationapp.Activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import edu.sdsu.tvidhate.registerationapp.Entity.Course;
import edu.sdsu.tvidhate.registerationapp.Entity.Student;
import edu.sdsu.tvidhate.registerationapp.Helper.RVCourseDetailAdapter;
import edu.sdsu.tvidhate.registerationapp.Helper.ServerConstants;
import edu.sdsu.tvidhate.registerationapp.Helper.VolleyQueue;
import edu.sdsu.tvidhate.registerationapp.R;

public class CourseDetailActivity extends AppCompatActivity implements View.OnClickListener,ServerConstants{

    private RecyclerView courseDetailRecyclerView;
    private Button mAddButton,mBackButton,mDropButton;
    private String courseID;
    HashMap<String ,String> courseDetails = new HashMap<>();
    private String getCourseDetailsListURL="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail_fragment);
        mBackButton = findViewById(R.id.backButton);
        mAddButton = findViewById(R.id.addCourseButton);
        mDropButton = findViewById(R.id.dropCourseButton);
        courseDetailRecyclerView = findViewById(R.id.courseDetails);

        mAddButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mDropButton.setOnClickListener(this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        courseDetailRecyclerView.setLayoutManager(llm);

        setCourseID(getIntent().getExtras().getString(COURSE_ID));
        getCourseDetailsListURL = SERVER_URL+CLASS_DETAILS+"?"+CLASS_ID+"="+getCourseID();
        getCoursesDetails(getCourseDetailsListURL);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.addCourseButton:
                Log.i("TPV","Adding Course");
                addStudentToCourse(LoginActivity.sessionStudent);
                break;
            case R.id.backButton:
                Log.i("TPV","Going Back");
                finish();
                break;
            case R.id.dropCourseButton:
                Log.i("TPV","Dropping Course");
                dropStudentToCourse(LoginActivity.sessionStudent);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void getCoursesDetails(String getCourseDetailsListURL)
    {
        Log.i("TPV",getCourseDetailsListURL);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,getCourseDetailsListURL,null ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    Course currentCourse = new Course(response);
                    Log.i("TPV",currentCourse.toString());
                    courseDetails = currentCourse.getCourseDetailsInHashMap();
                courseDetailRecyclerView.setAdapter(new RVCourseDetailAdapter(courseDetails));
                Log.i("TPV","In Response Listener "+courseDetails.toString());
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TPV", error.toString());
            }
        });
        VolleyQueue.instance(this).add(req);
    }

    public void dropStudentToCourse(Student currentStudent){

        final JSONObject postParams = new JSONObject();
        Long courseNumber = new Long(getCourseID());

        try {
            postParams.put(STUDENT_RED_ID,currentStudent.getmRedId());
            postParams.put(STUDENT_PASSWORD,currentStudent.getmPassword());
            postParams.put(COURSE_ID,courseNumber);
        } catch (JSONException error) {
            Log.e("rew", "JSON eorror", error);
            return;
        }

        Response.Listener<JSONObject> success = new Response.Listener<JSONObject>()
        {
            public void onResponse(JSONObject response) {
                Log.i("rew", response.toString());
            }
//Process response here
        };

        Response.ErrorListener failure = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("rew", "post fail " + new String(error.networkResponse.data));
            }
        };

        String addStudentToClassURL = SERVER_URL+UNREGISTER_CLASS;

        JsonObjectRequest postRequest = new JsonObjectRequest(addStudentToClassURL, postParams, success, failure);
        VolleyQueue.instance(this).add(postRequest);
    }


    public void addStudentToCourse(Student currentStudent){

        final JSONObject postParams = new JSONObject();
        Long courseNumber = new Long(getCourseID());


        try {
            postParams.put(STUDENT_RED_ID,currentStudent.getmRedId());
            postParams.put(STUDENT_PASSWORD,currentStudent.getmPassword());
            postParams.put(COURSE_ID,courseNumber);
        } catch (JSONException error) {
            Log.e("rew", "JSON eorror", error);
            return;
        }

        Response.Listener<JSONObject> success = new Response.Listener<JSONObject>()
        {
            public void onResponse(JSONObject response) {
                Log.i("rew", response.toString());
                try {
                    String errorMessage = response.getString("error");

                    if(errorMessage.equalsIgnoreCase("Student already enrolled in 3 classes"))
                    {
                        Toast.makeText(getApplicationContext(), "Student cannot add more than 3 courses", Toast.LENGTH_LONG).show();
                    }
                    if(errorMessage.equalsIgnoreCase("Course is full"))
                    {
                        onWaitlistDialog();
                    }
                }catch (Exception e){

                }
            }
//Process response here
        };

        Response.ErrorListener failure = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("rew", "post fail " + new String(error.networkResponse.data));
            }
        };

        String addStudentToClassURL = SERVER_URL+REGISTER_CLASS;
                //+"?"+STUDENT_RED_ID+"="+currentStudent.getmRedId()+"&"+STUDENT_PASSWORD+"="+currentStudent.getmPassword()+"&"+COURSE_ID+"="+getCourseID();

        JsonObjectRequest postRequest = new JsonObjectRequest(addStudentToClassURL, postParams, success, failure);
        VolleyQueue.instance(this).add(postRequest);
        //return studentAdded;
    }

    public void onWaitlistDialog()
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.waitlist_dialog);
        dialog.setTitle("Waitlist Confirmation");

        Button yesButton = dialog.findViewById(R.id.yesButton);
        Button noButton = dialog.findViewById(R.id.noButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TPV", "Adding to waitlist");
                addStudentToCourseWaitlist(LoginActivity.sessionStudent);
                dialog.dismiss();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TPV", "Not adding to waitlist");
                dropStudentToCourseWaitlist(LoginActivity.sessionStudent);
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void addStudentToCourseWaitlist(Student currentStudent){

        final JSONObject postParams = new JSONObject();
        Long courseNumber = new Long(getCourseID());

        try {
            postParams.put(STUDENT_RED_ID,currentStudent.getmRedId());
            postParams.put(STUDENT_PASSWORD,currentStudent.getmPassword());
            postParams.put(COURSE_ID,courseNumber);
        } catch (JSONException error) {
            Log.e("rew", "JSON eorror", error);
            return;
        }

        Response.Listener<JSONObject> success = new Response.Listener<JSONObject>()
        {
            public void onResponse(JSONObject response) {
                Log.i("rew", response.toString());
            }
        };

        Response.ErrorListener failure = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("rew", "post fail " + new String(error.networkResponse.data));
            }
        };

        String addStudentToClassURL = SERVER_URL+WAITLIST_CLASS;

        JsonObjectRequest postRequest = new JsonObjectRequest(addStudentToClassURL, postParams, success, failure);
        VolleyQueue.instance(this).add(postRequest);
    }

    public void dropStudentToCourseWaitlist(Student currentStudent){

        final JSONObject postParams = new JSONObject();
        Long courseNumber = new Long(getCourseID());

        try {
            postParams.put(STUDENT_RED_ID,currentStudent.getmRedId());
            postParams.put(STUDENT_PASSWORD,currentStudent.getmPassword());
            postParams.put(COURSE_ID,courseNumber);
        } catch (JSONException error) {
            Log.e("rew", "JSON eorror", error);
            return;
        }

        Response.Listener<JSONObject> success = new Response.Listener<JSONObject>()
        {
            public void onResponse(JSONObject response) {
                Log.i("rew", response.toString());
            }
        };

        Response.ErrorListener failure = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("rew", "post fail " + new String(error.networkResponse.data));
            }
        };

        String addStudentToClassURL = SERVER_URL+UNWAITLIST_CLASS;

        JsonObjectRequest postRequest = new JsonObjectRequest(addStudentToClassURL, postParams, success, failure);
        VolleyQueue.instance(this).add(postRequest);
    }


    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }
}

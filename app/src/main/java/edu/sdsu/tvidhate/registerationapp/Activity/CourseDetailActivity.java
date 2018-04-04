package edu.sdsu.tvidhate.registerationapp.Activity;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
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
import java.util.List;

import edu.sdsu.tvidhate.registerationapp.Entity.Course;
import edu.sdsu.tvidhate.registerationapp.Entity.Student;
import edu.sdsu.tvidhate.registerationapp.Fragments.DashboardFragment;
import edu.sdsu.tvidhate.registerationapp.Helper.RVCourseDetailAdapter;
import edu.sdsu.tvidhate.registerationapp.Helper.ServerConstants;
import edu.sdsu.tvidhate.registerationapp.Helper.VolleyQueue;
import edu.sdsu.tvidhate.registerationapp.R;

public class CourseDetailActivity extends AppCompatActivity implements View.OnClickListener,ServerConstants{

    private RecyclerView courseDetailRecyclerView;
    private String courseID;
    HashMap<String ,String> courseDetails = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail_fragment);
        Button mAddButton,mBackButton,mDropButton;

        mBackButton = findViewById(R.id.backButton);
        mAddButton = findViewById(R.id.addCourseButton);
        mDropButton = findViewById(R.id.dropCourseButton);
        courseDetailRecyclerView = findViewById(R.id.courseDetails);

        mAddButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);
        mDropButton.setOnClickListener(this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        courseDetailRecyclerView.setLayoutManager(llm);

        Bundle intentValues = getIntent().getExtras();
        if(intentValues != null)
            setCourseID(intentValues.getString(COURSE_ID));
        String getCourseDetailsListURL = SERVER_URL + CLASS_DETAILS + "?" + CLASS_ID + "=" + getCourseID();
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
        Response.Listener<JSONObject> success = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Course currentCourse = new Course(response);
                Log.i("TPV",currentCourse.toString());
                courseDetails = currentCourse.getCourseDetailsInHashMap();
                courseDetailRecyclerView.setAdapter(new RVCourseDetailAdapter(courseDetails));
                Log.i("TPV","In Response Listener "+courseDetails.toString());
            }};
        Response.ErrorListener failure =  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TPV", error.toString());
            }
        };
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,getCourseDetailsListURL,null ,success,failure);
        VolleyQueue.instance(this).add(req);
    }

    public void dropStudentToCourse(Student currentStudent){

        final JSONObject postParams = new JSONObject();
        Long courseNumber = Long.valueOf(getCourseID());

        try {
            postParams.put(STUDENT_RED_ID,currentStudent.getmRedId());
            postParams.put(STUDENT_PASSWORD,currentStudent.getmPassword());
            postParams.put(COURSE_ID,courseNumber);
        } catch (JSONException error) {
            Log.e("TPV", "JSON error", error);
            return;
        }

        Response.Listener<JSONObject> success = new Response.Listener<JSONObject>()
        {
            public void onResponse(JSONObject response) {
                Log.i("TPV", response.toString());
                try {
                    if(response.has("error")) {
                        if (response.getString("error").equalsIgnoreCase(COURSE_DROPPED)) {
                            Toast.makeText(getApplicationContext(), COURSE_DROPPED, Toast.LENGTH_LONG).show();
                            finish();
                        }
                        if(response.getString("error").equalsIgnoreCase(USER_NOT_ENROLLED))
                        {
                            dropStudentToCourseWaitlist(LoginActivity.sessionStudent);
                        }
                    }
                    if(response.has("ok"))
                        if(response.getString("ok").equalsIgnoreCase(USER_ALREADY_IN_COURSE))
                        {
                            Toast.makeText(getApplicationContext(), USER_ALREADY_IN_COURSE, Toast.LENGTH_LONG).show();
                            finish();
                        }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener failure = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TPV", "post fail " + new String(error.networkResponse.data));
            }
        };

        String addStudentToClassURL = SERVER_URL+UNREGISTER_CLASS;

        JsonObjectRequest postRequest = new JsonObjectRequest(addStudentToClassURL, postParams, success, failure);
        VolleyQueue.instance(this).add(postRequest);
    }

    public void addStudentToCourse(Student currentStudent){

        final JSONObject postParams = new JSONObject();
        Long courseNumber = Long.valueOf(getCourseID());


        try {
            postParams.put(STUDENT_RED_ID,currentStudent.getmRedId());
            postParams.put(STUDENT_PASSWORD,currentStudent.getmPassword());
            postParams.put(COURSE_ID,courseNumber);
        } catch (JSONException error) {
            Log.e("TPV", "JSON error", error);
            return;
        }

        Response.Listener<JSONObject> success = new Response.Listener<JSONObject>()
        {
            public void onResponse(JSONObject response) {
                Log.i("TPV", response.toString());

                try {
                    if(response.has("error"))
                    {
                        String errorMessage = response.getString("error");
                        if(errorMessage.equalsIgnoreCase(USER_REGISTERED_IN_3_COURSES))
                        {
                            Toast.makeText(getApplicationContext(), USER_REGISTERED_IN_3_COURSES, Toast.LENGTH_LONG).show();
                            finish();
                        }
                        if(errorMessage.equalsIgnoreCase(COURSE_IS_FULL))
                            onWaitlistDialog();
                        if(errorMessage.equalsIgnoreCase(USER_ALREADY_IN_COURSE))
                        {
                            Toast.makeText(getApplicationContext(), USER_ALREADY_IN_COURSE, Toast.LENGTH_LONG).show();
                            finish();
                        }
                        if(errorMessage.equalsIgnoreCase(COURSE_TIMES_OVERLAP))
                        {
                            Toast.makeText(getApplicationContext(), COURSE_TIMES_OVERLAP, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                    if(response.has("ok")) {
                        String okMessage = response.getString("ok");
                        if (okMessage.equalsIgnoreCase(COURSE_ADDED)) {
                            Toast.makeText(getApplicationContext(), COURSE_ADDED, Toast.LENGTH_LONG).show();
                        }
                        finish();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener failure = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TPV", "post fail " + new String(error.networkResponse.data));
            }
        };

        String addStudentToClassURL = SERVER_URL+REGISTER_CLASS;

        JsonObjectRequest postRequest = new JsonObjectRequest(addStudentToClassURL, postParams, success, failure);
        VolleyQueue.instance(this).add(postRequest);
    }

    public void onWaitlistDialog()
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.waitlist_dialog);

        Button yesButton = dialog.findViewById(R.id.yesButton);
        Button noButton = dialog.findViewById(R.id.noButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TPV", "Adding to waitlist");
                addStudentToCourseWaitlist(LoginActivity.sessionStudent);
                dialog.dismiss();
                finish();
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TPV", "Not adding to waitlist");
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();

    }

    public void addStudentToCourseWaitlist(Student currentStudent){

        final JSONObject postParams = new JSONObject();
        Long courseNumber = Long.valueOf(getCourseID());

        try {
            postParams.put(STUDENT_RED_ID,currentStudent.getmRedId());
            postParams.put(STUDENT_PASSWORD,currentStudent.getmPassword());
            postParams.put(COURSE_ID,courseNumber);
        } catch (JSONException error) {
            Log.e("TPV", "JSON error", error);
            return;
        }

        Response.Listener<JSONObject> success = new Response.Listener<JSONObject>()
        {
            public void onResponse(JSONObject response) {
                Log.i("TPV", response.toString());
            }
        };

        Response.ErrorListener failure = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TPV", "post fail " + new String(error.networkResponse.data));
            }
        };

        String addStudentToClassURL = SERVER_URL+WAITLIST_CLASS;

        JsonObjectRequest postRequest = new JsonObjectRequest(addStudentToClassURL, postParams, success, failure);
        VolleyQueue.instance(this).add(postRequest);
    }

    public void dropStudentToCourseWaitlist(Student currentStudent){

        final JSONObject postParams = new JSONObject();
        Long courseNumber = Long.valueOf(getCourseID());

        try {
            postParams.put(STUDENT_RED_ID,currentStudent.getmRedId());
            postParams.put(STUDENT_PASSWORD,currentStudent.getmPassword());
            postParams.put(COURSE_ID,courseNumber);
        } catch (JSONException error) {
            Log.e("TPV", "JSON error", error);
            return;
        }

        Response.Listener<JSONObject> success = new Response.Listener<JSONObject>()
        {
            public void onResponse(JSONObject response) {
                Log.i("TPV", response.toString());
                try{
                    if(response.has("error")) {
                        if (response.getString("error").equalsIgnoreCase(COURSE_DROPPED))
                            Toast.makeText(getApplicationContext(),COURSE_DROPPED, Toast.LENGTH_LONG).show();
                        if(response.getString("error").equalsIgnoreCase(USER_NOT_ENROLLED))
                            Toast.makeText(getApplicationContext(),USER_NOT_ENROLLED, Toast.LENGTH_LONG).show();
                    }finish();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener failure = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TPV", "post fail " + new String(error.networkResponse.data));
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

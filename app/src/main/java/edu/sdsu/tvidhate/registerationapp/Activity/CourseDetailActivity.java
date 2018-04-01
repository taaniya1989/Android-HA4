package edu.sdsu.tvidhate.registerationapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import edu.sdsu.tvidhate.registerationapp.Entity.Course;
import edu.sdsu.tvidhate.registerationapp.Helper.RVCourseDetailAdapter;
import edu.sdsu.tvidhate.registerationapp.Helper.VolleyQueue;
import edu.sdsu.tvidhate.registerationapp.R;

public class CourseDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView courseDetailRecyclerView;
    private Button mAddButton,mBackButton;
    HashMap<String ,String> courseDetails = new HashMap<>();
    private String getCourseDetailsListURL="https://bismarck.sdsu.edu/registration/classdetails?classid=7033";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_detail_fragment);
        mBackButton = findViewById(R.id.backButton);
        mAddButton = findViewById(R.id.addCourseButton);
        courseDetailRecyclerView = findViewById(R.id.courseDetails);

        mAddButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        courseDetailRecyclerView.setLayoutManager(llm);

        getCoursesDetails(getCourseDetailsListURL);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.addCourseButton:
                Log.i("TPV","Adding Course");
                finish();
                break;
            case R.id.backButton:
                Log.i("TPV","Going Back");
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
}

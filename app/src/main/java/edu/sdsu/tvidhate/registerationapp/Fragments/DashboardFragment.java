package edu.sdsu.tvidhate.registerationapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.sdsu.tvidhate.registerationapp.Activity.LoginActivity;
import edu.sdsu.tvidhate.registerationapp.Entity.Course;
import edu.sdsu.tvidhate.registerationapp.Helper.RVCourseIDAdapter;
import edu.sdsu.tvidhate.registerationapp.Helper.ServerConstants;
import edu.sdsu.tvidhate.registerationapp.Helper.VolleyQueue;
import edu.sdsu.tvidhate.registerationapp.R;

public class DashboardFragment extends Fragment implements ServerConstants//,View.OnClickListener
{
    RecyclerView rv,rv1;
    String getRegisteredClassIdsListURL = SERVER_URL+STUDENT_CLASSES+"?";
    List<String> courseListID;
    List<Course> courseDetailsList = new ArrayList<>();
    List<Course> waitlistedCourseDetailsList = new ArrayList<>();
    private String getClassDetailsURL = SERVER_URL+CLASS_DETAILS+"?"+CLASS_ID+"=";
    //https://bismarck.sdsu.edu/registration/studentclasses?redid=820841740&password=RGBnko123!

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_fragment, container, false);

       /* Button dropRegisteredClassButton = view.findViewById(R.id.dropRegisteredClass);
        Button dropWaitlistedClassButton = view.findViewById(R.id.dropWaitlisedClass);
        dropRegisteredClassButton.setOnClickListener(this);
        dropWaitlistedClassButton.setOnClickListener(this);*/
        rv = view.findViewById(R.id.registeredCourses);
        rv1 = view.findViewById(R.id.registeredCourses1);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        LinearLayoutManager llm1 = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        rv1.setLayoutManager(llm1);

        waitlistedCourseDetailsList.clear();
        courseDetailsList.clear();

        getRegisteredClassIdsListURL=getRegisteredClassIdsListURL+STUDENT_RED_ID+"="+ LoginActivity.sessionStudent.getmRedId()+"&"+STUDENT_PASSWORD+"="+LoginActivity.sessionStudent.getmPassword();
        getRegisteredCourse(getRegisteredClassIdsListURL);
        return view;
    }

  /*  @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.dropRegisteredClass:
                break;
            case R.id.dropWaitlisedClass:
                break;
        }
    }*/

    public void getRegisteredCourse(String getRegisteredClassIdsListURL)
    {
        Log.i("TPV",getRegisteredClassIdsListURL);
        courseListID = new ArrayList<>();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,getRegisteredClassIdsListURL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TPV", response.toString());
                try {
                    JSONArray classes = response.getJSONArray("classes");
                    JSONArray waitlist = response.getJSONArray("waitlist");
                   // ArrayList<String> classids = new ArrayList<>();
                   // classids.add(classes);
                    for(int i=0;i<classes.length();i++) {
                        Log.i("TPV", classes.get(i).toString());
                        getCoursesDetails(classes.get(i).toString());
                    }
                    for(int i=0;i<waitlist.length();i++) {
                        Log.i("TPV", waitlist.get(i).toString());
                        getWaitlistedCoursesDetails(waitlist.get(i).toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }}, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("TPV", error.toString());
                }
            });
        VolleyQueue.instance(getContext()).add(req);
    }

    private void getCoursesDetails(String aLong) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                getClassDetailsURL+aLong, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                Course course = new Course(response);
                courseDetailsList.add(course);
                // LoginActivity.dbHelper.insertCourseData(course);
                rv.setAdapter(new RVCourseIDAdapter(courseDetailsList));
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TPV", "Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog

            }
        });

        // Adding request to request queue
        VolleyQueue.instance(this.getContext()).add(jsonObjReq);
    }

    private void getWaitlistedCoursesDetails(String aLong) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                getClassDetailsURL+aLong, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                Course course = new Course(response);
                waitlistedCourseDetailsList.add(course);
                // LoginActivity.dbHelper.insertCourseData(course);
                rv1.setAdapter(new RVCourseIDAdapter(waitlistedCourseDetailsList));
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TPV", "Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog

            }
        });

        // Adding request to request queue
        VolleyQueue.instance(this.getContext()).add(jsonObjReq);
    }
}

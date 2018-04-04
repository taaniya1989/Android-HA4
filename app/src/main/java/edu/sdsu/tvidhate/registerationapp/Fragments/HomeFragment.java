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
import java.util.Objects;

import edu.sdsu.tvidhate.registerationapp.Activity.LoginActivity;
import edu.sdsu.tvidhate.registerationapp.Entity.Course;
import edu.sdsu.tvidhate.registerationapp.Helper.RVCourseIDAdapter;
import edu.sdsu.tvidhate.registerationapp.Helper.ServerConstants;
import edu.sdsu.tvidhate.registerationapp.Helper.VolleyQueue;
import edu.sdsu.tvidhate.registerationapp.R;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener,ServerConstants,View.OnClickListener
{
    private Spinner mMajorSpinner;
    private List<String> majorsList = new ArrayList<>();
    private String majorValue="",levelValue="",startTimeValue="";
    RecyclerView courseRecyclerView;
    private List<String> courseListID = new ArrayList<>();
    private List<Course> courseDetailsList = new ArrayList<>();
    private HashMap<String,Integer> majorsInfo = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        Button mSearchButton;
        Spinner mLevelSpinner,mStartTimeSpinner;

        mMajorSpinner = view.findViewById(R.id.majorSpinner);
        mLevelSpinner = view.findViewById(R.id.levelSpinner);
        mStartTimeSpinner = view.findViewById(R.id.startTimeSpinner);
        mSearchButton = view.findViewById(R.id.searchButton);

        mMajorSpinner.setOnItemSelectedListener(this);
        mLevelSpinner.setOnItemSelectedListener(this);
        mStartTimeSpinner.setOnItemSelectedListener(this);
        mSearchButton.setOnClickListener(this);

        courseRecyclerView = view.findViewById(R.id.subjectListView);
        courseListID = new ArrayList<>();

        LinearLayoutManager courseLinearLayoutManager = new LinearLayoutManager(getContext());
        courseRecyclerView.setLayoutManager(courseLinearLayoutManager);

        getMajors();
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.searchButton)
        {
            courseListID.clear();
            courseDetailsList.clear();
            if(getMajorValue().equalsIgnoreCase(""))
                Toast.makeText(getContext(),"Please select Major",Toast.LENGTH_LONG).show();
            else {
                String getClassIdsListURL = SERVER_URL+CLASS_IDS_LIST+"?"+SUBJECT_ID+"="+majorsInfo.get(getMajorValue());
                if(getLevelValue() != "")
                    getClassIdsListURL = getClassIdsListURL+"&"+LEVEL+"="+getLevelValue();
                if(getStartTimeValue() != "")
                    getClassIdsListURL = getClassIdsListURL+"&"+START_TIME+"="+getStartTimeValue();
                getCoursesID(getClassIdsListURL);
                Log.i("TPV","OnClick : "+courseListID.toString());
            }

        }
    }

    public void getCoursesID(String getClassIdsListURL)
    {
        Log.i("TPV",getClassIdsListURL);
        courseListID = new ArrayList<>();

        Response.Listener<JSONArray> success = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("TPV", response.toString());
                try {
                    for(int i=0;i<response.length();i++){
                        Integer courseID = (Integer) response.get(i);

                        courseListID.add(courseID.toString());
                        Log.d("TPV","CourseListID contents "+ courseListID.toString());
                    }
                    for (int i=0; i<courseListID.size();i++)
                    {
                        getCoursesDetails(courseListID.get(i));
                    }
                    Log.i("TPV","In Response Listener "+courseListID.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }};

        Response.ErrorListener failure = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TPV", error.toString());
            }
        };

        JsonArrayRequest getArrayRequest = new JsonArrayRequest(getClassIdsListURL,success ,failure);
        VolleyQueue.instance(getContext()).add(getArrayRequest);
    }

    private void getCoursesDetails(String aLong) {

        Response.Listener<JSONObject> success = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                Course course = new Course(response);
                courseDetailsList.add(course);
                //LoginActivity.dbHelper.insertCourseData(course);
                courseRecyclerView.setAdapter(new RVCourseIDAdapter(courseDetailsList));
            }
        };
        Response.ErrorListener failure = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TPV", "Error: " + error.getMessage());
                Toast.makeText(getContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog

            }
        };

        String getClassDetailsURL = SERVER_URL + CLASS_DETAILS + "?" + CLASS_ID + "=";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                getClassDetailsURL +aLong, null, success, failure);
        VolleyQueue.instance(this.getContext()).add(jsonObjReq);
    }

    public void getMajors()
    {
        Response.Listener<JSONArray> success = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("TPV", response.toString());
                try {
                    for(int i=0;i<response.length();i++){
                        JSONObject subject = response.getJSONObject(i);
                        int subjectId = subject.getInt("id");
                        String subjectName = subject.getString("title");
                        majorsInfo.put(subjectName,subjectId);
                        majorsList.add(subjectName);
                    }
                    loadSpinnerData(majorsList,mMajorSpinner);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener failure =  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TPV", error.toString());

            }
        };
        JsonArrayRequest req = new JsonArrayRequest(SERVER_URL+SUBJECT_LIST,
                success, failure);
        VolleyQueue.instance(this.getContext()).add(req);
    }

    private void loadSpinnerData(List<String> dataList,Spinner spinner)
    {
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_item, dataList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void setLevelValue(String levelValue) {
        this.levelValue = levelValue;
    }

    public void setStartTimeValue(String startTimeValue) {
        this.startTimeValue = startTimeValue;
    }

    public void setMajorValue(String majorValue) {
        this.majorValue = majorValue;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String label = parent.getItemAtPosition(position).toString();
        switch(parent.getId())
        {
            case R.id.majorSpinner:
                setMajorValue(label);
                break;
            case R.id.levelSpinner:
                setLevelValue(label);
                break;
            case R.id.startTimeSpinner:
                setStartTimeValue(label);
                break;
        }
    }

    public String getMajorValue() {
        return majorValue;
    }

    public String getLevelValue() {
        return levelValue;
    }

    public String getStartTimeValue() {
        return startTimeValue;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}

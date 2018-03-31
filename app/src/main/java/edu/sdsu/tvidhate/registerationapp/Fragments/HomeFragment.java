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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.sdsu.tvidhate.registerationapp.Helper.RVCourseIDAdapter;
import edu.sdsu.tvidhate.registerationapp.Helper.ServerConstants;
import edu.sdsu.tvidhate.registerationapp.Helper.VolleyQueue;
import edu.sdsu.tvidhate.registerationapp.R;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener,ServerConstants,View.OnClickListener
{
    private Spinner mMajorSpinner,mLevelSpinner,mStartTimeSpinner;
    private Button mSearchButton;
    private List<String> majorsList = new ArrayList<String>();
    private String majorValue="",levelValue="",startTimeValue="";
    RecyclerView rv;
    private String getClassIdsListURL;
    private List<String> courseListID;
    private HashMap<String,Integer> majorsInfo = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        mMajorSpinner = view.findViewById(R.id.majorSpinner);
        mLevelSpinner = view.findViewById(R.id.levelSpinner);
        mStartTimeSpinner = view.findViewById(R.id.startTimeSpinner);
        mSearchButton = view.findViewById(R.id.searchButton);

        getMajors();
        mMajorSpinner.setOnItemSelectedListener(this);
        mLevelSpinner.setOnItemSelectedListener(this);
        mStartTimeSpinner.setOnItemSelectedListener(this);
        mSearchButton.setOnClickListener(this);

        rv = view.findViewById(R.id.subjectListView);

        courseListID = new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.searchButton)
        {
            if(getMajorValue() == "" )
                Toast.makeText(getContext(),"Please select Major",Toast.LENGTH_LONG).show();
            else {
                getClassIdsListURL = SERVER_URL+CLASS_IDS_LIST+"?"+SUBJECT_ID+"="+majorsInfo.get(getMajorValue());
                if(getLevelValue() != "")
                    getClassIdsListURL = getClassIdsListURL+"&"+LEVEL+"="+getLevelValue();
                if(getStartTimeValue() != "")
                    getClassIdsListURL = getClassIdsListURL+"&"+START_TIME+"="+getStartTimeValue();
                getCoursesID(getClassIdsListURL);
                Log.i("TPV","OnClick"+courseListID.toString());
            }

        }
        Log.i("TPV",v.getId()+" "+R.id.searchButton);
    }

    public void getCoursesID(String getClassIdsListURL)
    {
        Log.i("TPV",getClassIdsListURL);
        courseListID = new ArrayList<>();
        JsonArrayRequest req = new JsonArrayRequest(getClassIdsListURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("TPV", response.toString());
                try {
                    for(int i=0;i<response.length();i++){
                        Integer courseID = (Integer)response.get(i);
                        Log.d("TPV", response.get(i).toString()+" "+courseID.toString());
                        courseListID.add(courseID.toString());
                    }
                    rv.setAdapter(new RVCourseIDAdapter(courseListID));
                    Log.i("TPV","In Response Listener "+courseListID.toString());

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

    public void getMajors()
    {
        JsonArrayRequest req = new JsonArrayRequest(SERVER_URL+SUBJECT_LIST,
                new Response.Listener<JSONArray>() {
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
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TPV", error.toString());

            }
        });
        VolleyQueue.instance(this.getContext()).add(req);
    }

    private void loadSpinnerData(List<String> dataList,Spinner spinner)
    {
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(getContext(),
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

    public Spinner getmMajorSpinner() {
        return mMajorSpinner;
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

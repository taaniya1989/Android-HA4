package edu.sdsu.tvidhate.registerationapp.Fragments;

import android.app.DownloadManager;
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

import edu.sdsu.tvidhate.registerationapp.Helper.RVCourseDetailAdapter;
import edu.sdsu.tvidhate.registerationapp.Helper.RVCourseIDAdapter;
import edu.sdsu.tvidhate.registerationapp.Helper.ServerConstants;
import edu.sdsu.tvidhate.registerationapp.Helper.VolleyQueue;
import edu.sdsu.tvidhate.registerationapp.R;

public class CourseDetailFragment extends Fragment implements ServerConstants,View.OnClickListener
{
    private Button mAddButton,mBackButton;
    RecyclerView courseDetailRecyclerView;
    private String getCourseDetailsListURL="https://bismarck.sdsu.edu/registration/classdetails?classid=7033";
    HashMap<String ,String> courseDetails = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        mBackButton = view.findViewById(R.id.backButton);
        mAddButton = view.findViewById(R.id.addButton);
        courseDetailRecyclerView = view.findViewById(R.id.courseDetails);

        mAddButton.setOnClickListener(this);
        mBackButton.setOnClickListener(this);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        courseDetailRecyclerView.setLayoutManager(llm);

        getCoursesDetails(getCourseDetailsListURL);
        return view;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.addButton:
                Log.i("TPV","Adding Course");
                break;
            case R.id.backButton:
                Log.i("TPV","Going Back");
                break;
        }
    }

    public void getCoursesDetails(String getCourseDetailsListURL)
    {
        Log.i("TPV",getCourseDetailsListURL);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,getCourseDetailsListURL,null ,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    courseDetails.put("instructor",response.getString("instructor"));
                    courseDetails.put("title",response.getString("title"));
                    courseDetails.put("units",response.getString("units"));
                    courseDetails.put("days",response.getString("days"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                courseDetailRecyclerView.setAdapter(new RVCourseDetailAdapter(courseDetails));
                Log.i("TPV","In Response Listener "+courseDetails.toString());
            }}, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("TPV", error.toString());
                }
            });
        VolleyQueue.instance(getContext()).add(req);
    }

}

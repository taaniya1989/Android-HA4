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

public class DashboardFragment extends Fragment implements ServerConstants
{
    RecyclerView rv;
    String getRegisteredClassIdsListURL = SERVER_URL+STUDENT_CLASSES+"?";
    List<String> courseListID;
    //https://bismarck.sdsu.edu/registration/studentclasses?redid=820841740&password=RGBnko123!

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        rv = view.findViewById(R.id.subjectListView);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        getRegisteredClassIdsListURL=getRegisteredClassIdsListURL+STUDENT_RED_ID+"="+ LoginActivity.sessionStudent.getmRedId()+"&"+STUDENT_PASSWORD+"="+LoginActivity.sessionStudent.getmPassword();
        getRegisteredCourse(getRegisteredClassIdsListURL);
        return view;
    }

    public void getRegisteredCourse(String getRegisteredClassIdsListURL)
    {
        Log.i("TPV",getRegisteredClassIdsListURL);
        courseListID = new ArrayList<>();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,getRegisteredClassIdsListURL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TPV", response.toString());
                try {
                    String classes = response.getString("classes");
                    ArrayList<String> classids = new ArrayList<>();
                    classids.add(classes);
                    Log.i("TPV",classes.toString()+" "+classids.get(0));
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

}

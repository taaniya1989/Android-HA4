package edu.sdsu.tvidhate.registerationapp.Activity;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import edu.sdsu.tvidhate.registerationapp.Entity.Student;
import edu.sdsu.tvidhate.registerationapp.Helper.DatabaseHelper;
import edu.sdsu.tvidhate.registerationapp.Helper.ServerConstants;
import edu.sdsu.tvidhate.registerationapp.Helper.VolleyQueue;
import edu.sdsu.tvidhate.registerationapp.R;

public class UserRegistrationActivity extends AppCompatActivity implements View.OnClickListener,ServerConstants{

    private EditText mUserFirstName,mUserLastName,mUserEmailId,mUserPassword,mUserRedID;
    private Button mCancelButton,mSubmitButton;
//    private HashMap<String,String> postParams = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        Bundle userData = getIntent().getExtras();
        String userEmail = userData.getString(STUDENT_EMAIL);
        String userPassword = userData.getString(STUDENT_PASSWORD);

        mUserFirstName = findViewById(R.id.userFirstName);
        mUserLastName = findViewById(R.id.userLastName);
        mUserEmailId = findViewById(R.id.userEmail);
        mUserPassword = findViewById(R.id.userPassword);
        mUserRedID = findViewById(R.id.userRedID);
        mCancelButton = findViewById(R.id.cancelButton);
        mSubmitButton = findViewById(R.id.submitButton);

        mUserEmailId.setText(userEmail);
        mUserPassword.setText(userPassword);

        mCancelButton.setOnClickListener(this);
        mSubmitButton.setOnClickListener(this);

    }

    public void addStudentToServer(){
       // boolean studentAdded = false;


        final JSONObject postParams = new JSONObject();

        try {
            postParams.put(STUDENT_FIRST_NAME,mUserFirstName.getText().toString());
            postParams.put(STUDENT_LAST_NAME,mUserLastName.getText().toString());
            postParams.put(STUDENT_RED_ID,mUserRedID.getText().toString());
            postParams.put(STUDENT_EMAIL,mUserEmailId.getText().toString());
            postParams.put(STUDENT_PASSWORD,mUserPassword.getText().toString());
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

        JsonObjectRequest postRequest = new JsonObjectRequest(SERVER_URL+ADD_STUDENT, postParams, success, failure);
        VolleyQueue.instance(this).add(postRequest);
        //return studentAdded;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.cancelButton:
                finish();
                break;
            case R.id.submitButton:
               // Student newStudent = new Student(mUserRedID.getText().toString(),mUserFirstName.getText().toString(),mUserLastName.getText().toString(),mUserEmailId.getText().toString(),mUserPassword.getText().toString());
                if(Student.isValidEmail(mUserEmailId.getText().toString()) && Student.isValidPassword(mUserPassword.getText().toString())) {
                    addStudentToServer();

                        DatabaseHelper databaseHelper = new DatabaseHelper(this);
                        boolean insertData = databaseHelper.insertData(mUserRedID.getText().toString(), mUserFirstName.getText().toString(), mUserLastName.getText().toString(), mUserEmailId.getText().toString(), mUserPassword.getText().toString());
                        if (insertData) {
                            finish();
                        } else {
                            Toast.makeText(this, "Data not inserted", Toast.LENGTH_SHORT).show();
                        }

                    finish();
                }
                else
                {
                    mUserEmailId.setText("");
                    mUserPassword.setText("");
                    Toast.makeText(this, "Enter Valid User Email/Password", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

}

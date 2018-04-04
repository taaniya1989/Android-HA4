package edu.sdsu.tvidhate.registerationapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;

import edu.sdsu.tvidhate.registerationapp.Entity.Notifications;
import edu.sdsu.tvidhate.registerationapp.Entity.Student;
import edu.sdsu.tvidhate.registerationapp.Helper.ServerConstants;
import edu.sdsu.tvidhate.registerationapp.Helper.VolleyQueue;
import edu.sdsu.tvidhate.registerationapp.R;

public class UserRegistrationActivity extends AppCompatActivity implements View.OnClickListener,ServerConstants{

    private EditText mUserFirstName,mUserLastName,mUserEmailId,mUserPassword,mUserRedID;
    private boolean isStudentDataPostedToServer = false;
    private boolean isStudentDataSavedToDevice = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        Bundle userData = getIntent().getExtras();
        String userEmail = null,userPassword = null;
        if (userData != null) {
            userEmail = userData.getString(STUDENT_EMAIL);
            userPassword = userData.getString(STUDENT_PASSWORD);
        }

        Button mCancelButton,mSubmitButton;

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

    public void addStudentToServer(Student currentStudent)
    {
        final JSONObject postParams = new JSONObject();

        try {
            postParams.put(STUDENT_FIRST_NAME,currentStudent.getmFirstName());
            postParams.put(STUDENT_LAST_NAME,currentStudent.getmLastName());
            postParams.put(STUDENT_RED_ID,currentStudent.getmRedId());
            postParams.put(STUDENT_EMAIL,currentStudent.getmEmailId());
            postParams.put(STUDENT_PASSWORD,currentStudent.getmPassword());
        } catch (JSONException error) {
            Log.e("TPV", "JSON error while creating JSONObject "+error);
            return;
        }

        Response.Listener<JSONObject> success = new Response.Listener<JSONObject>()
        {
            public void onResponse(JSONObject response) {
                Log.i("TPV", response.toString());
                try{
                    if(response.has("error")){
                        if(response.getString("error").equalsIgnoreCase("Red Id already in use"))
                        {
                            Toast.makeText(getApplicationContext(),"Red Id already in use.\nEnter Valid values.",Toast.LENGTH_LONG).show();
                            Log.i("TPV","In UserRegistrationActivity : "+"Red Id already in use.\nEnter Valid values.");
                            resetFields();
                            UserRegistrationActivity.this.setStudentDataPostedToServer(false);
                        }
                    }
                    if(response.has("ok"))
                        if(response.getString("ok").equalsIgnoreCase("Student Added"))
                        {
                            UserRegistrationActivity.this.setStudentDataPostedToServer(true);
                            Log.i("TPV",response.get("ok").toString()+" : "+UserRegistrationActivity.this.isStudentDataPostedToServer());
                        }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener failure = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("TPV", "In UserRegistrationActivity : Post failed " + new String(error.networkResponse.data));
                UserRegistrationActivity.this.setStudentDataPostedToServer(false);
            }
        };

        JsonObjectRequest postRequest = new JsonObjectRequest(SERVER_URL+ADD_STUDENT,
                postParams, success, failure);
        VolleyQueue.instance(this).add(postRequest);
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.cancelButton:
                //Do nothing but close this activity
                finish();
                break;

            case R.id.submitButton:
                //If student email and password is valid at to server and device
                if(Student.isValidEmail(mUserEmailId.getText().toString())
                        && Student.isValidPassword(mUserPassword.getText().toString()))
                {
                    Student currentStudent = new Student(mUserRedID.getText().toString(), mUserFirstName.getText().toString(),
                            mUserLastName.getText().toString(), mUserEmailId.getText().toString(), mUserPassword.getText().toString());
                    addStudentToServer(currentStudent);
                    UserRegistrationActivity.this.setStudentDataSavedToDevice(LoginActivity.dbHelper.insertStudentData(currentStudent));
                    if (UserRegistrationActivity.this.isStudentDataSavedToDevice())// && UserRegistrationActivity.this.isStudentDataPostedToServer())
                    {
                        LoginActivity.allNotifications.add(new Notifications((System.currentTimeMillis()/1000),USER_ADDED));
                        Log.i("TPV",LoginActivity.allNotifications.toString());
                    }
                    else
                    {
                        Log.i("TPV","In UserRegistrationActivity : "+"Data not inserted in Device Database");
                        Toast.makeText(this, "Data not inserted", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
                else
                {
                    ////If student email and password is not valid reset fields
                    mUserEmailId.setText("");
                    mUserPassword.setText("");
                    Toast.makeText(this, "Enter Valid User Email/Password", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void resetFields()
    {
        this.mUserPassword.setText("");
        this.mUserEmailId.setText("");
        this.mUserFirstName.setText("");
        this.mUserLastName.setText("");
        this.mUserRedID.setText("");
    }

    public boolean isStudentDataPostedToServer() {
        Log.i("TPV","isStudentDataPostedToServer ? "+this.isStudentDataPostedToServer);
        return this.isStudentDataPostedToServer;
    }

    public void setStudentDataPostedToServer(boolean studentDataPostedToServer) {
        this.isStudentDataPostedToServer = studentDataPostedToServer;
    }

    public boolean isStudentDataSavedToDevice() {
        Log.i("TPV","isStudentDataSavedToDevice ? "+this.isStudentDataSavedToDevice);
        return this.isStudentDataSavedToDevice;
    }

    public void setStudentDataSavedToDevice(boolean studentDataSavedToDevice) {
        this.isStudentDataSavedToDevice = studentDataSavedToDevice;
    }
}

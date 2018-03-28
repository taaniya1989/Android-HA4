package edu.sdsu.tvidhate.registerationapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.sdsu.tvidhate.registerationapp.Entity.Student;
import edu.sdsu.tvidhate.registerationapp.Helper.DatabaseHelper;
import edu.sdsu.tvidhate.registerationapp.R;

public class UserRegistrationActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mUserFirstName,mUserLastName,mUserEmailId,mUserPassword,mUserRedID;
    private Button mCancelButton,mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        Bundle userData = getIntent().getExtras();
        String userEmail = userData.getString(getString(R.string.EMAIL));
        String userPassword = userData.getString(getString(R.string.PASSWORD));

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

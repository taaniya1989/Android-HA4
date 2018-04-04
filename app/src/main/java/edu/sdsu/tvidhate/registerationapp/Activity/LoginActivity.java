package edu.sdsu.tvidhate.registerationapp.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.sdsu.tvidhate.registerationapp.Entity.Student;
import edu.sdsu.tvidhate.registerationapp.Helper.DatabaseHelper;
import edu.sdsu.tvidhate.registerationapp.Helper.ServerConstants;
import edu.sdsu.tvidhate.registerationapp.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,ServerConstants{

    private EditText mUserEmail,mUserPassword;
    private static final int INTENT_REQUESTCODE = 333;

    public static Student sessionStudent;
    public static DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button mSignUp,mLogIn;

        mUserEmail = findViewById(R.id.userEmail);
        mUserPassword = findViewById(R.id.userPassword);
        mSignUp = findViewById(R.id.login_sign_up);
        mLogIn = findViewById(R.id.login_submit);

        mSignUp.setOnClickListener(this);
        mLogIn.setOnClickListener(this);

        dbHelper = new DatabaseHelper(this);
    }

    @Override
    public void onClick(View view) {

        String emailFromDatabase="",passwordFromDatabase="",fnameFromDatabase="",lnameFromDatabase="",redidFromDatabase="";

        switch (view.getId())
        {
            case R.id.login_sign_up:

                Intent goToUserRegistrationActivty = new Intent(this,UserRegistrationActivity.class);

                goToUserRegistrationActivty.putExtra(STUDENT_EMAIL,mUserEmail.getText().toString());
                goToUserRegistrationActivty.putExtra(STUDENT_PASSWORD,mUserPassword.getText().toString());

                startActivityForResult(goToUserRegistrationActivty,INTENT_REQUESTCODE);

                break;

            case R.id.login_submit:

                if(Student.isValidEmail(mUserEmail.getText().toString())
                        && Student.isValidPassword(mUserPassword.getText().toString()))
                {
                    Cursor results = dbHelper.authenticateUser(mUserEmail.getText().toString(), mUserPassword.getText().toString());

                    if (results.getCount() == 0) {
                        Log.i("TPV","In LoginActivity : User Doesn't Exist");
                        Toast.makeText(LoginActivity.this, "User Doesn't Exist", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    while (results.moveToNext())
                    {
                        redidFromDatabase = results.getString(0);
                        fnameFromDatabase = results.getString(1);
                        lnameFromDatabase = results.getString(2);
                        emailFromDatabase = results.getString(3);
                        passwordFromDatabase = results.getString(4);
                    }

                    if (emailFromDatabase.equalsIgnoreCase(mUserEmail.getText().toString())
                            && passwordFromDatabase.equalsIgnoreCase(mUserPassword.getText().toString()))
                    {

                        sessionStudent = new Student(redidFromDatabase,fnameFromDatabase,lnameFromDatabase,emailFromDatabase,passwordFromDatabase);

                        Intent homeActivity = new Intent(LoginActivity.this,HomeScreenActivity.class);
                        startActivity(homeActivity);
                        finish();

                        Toast.makeText(this, "Authentication successful!!\nLogging In", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(this, "Enter Valid User Email/Password", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                if(requestCode == INTENT_REQUESTCODE){
                    mUserEmail.setText(data.getStringExtra(STUDENT_EMAIL));
                    mUserPassword.setText(data.getStringExtra(STUDENT_PASSWORD));
                    finish();
                }
        }
    }
}

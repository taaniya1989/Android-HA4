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

import edu.sdsu.tvidhate.registerationapp.Entity.Student;
import edu.sdsu.tvidhate.registerationapp.Helper.DatabaseHelper;
import edu.sdsu.tvidhate.registerationapp.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mUserEmail,mUserPassword;
    private Button mSignUp,mLogIn;
    private static final int INTENT_REQUESTCODE = 333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserEmail = findViewById(R.id.userEmail);
        mUserPassword = findViewById(R.id.userPassword);
        mSignUp = findViewById(R.id.login_sign_up);
        mLogIn = findViewById(R.id.login_submit);

        mSignUp.setOnClickListener(this);
        mLogIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        String emailFromDatabase = "";
        String passwordFromDatabase = "";

        switch (view.getId())
        {
            case R.id.login_sign_up:
                Intent goToUserRegistrationActivty = new Intent(this,UserRegistrationActivity.class);
                goToUserRegistrationActivty.putExtra("EMAIL",mUserEmail.getText().toString());
                goToUserRegistrationActivty.putExtra("PASSWORD",mUserPassword.getText().toString());
                startActivityForResult(goToUserRegistrationActivty,INTENT_REQUESTCODE);
                Toast.makeText(this,mSignUp.getText().toString(),Toast.LENGTH_LONG).show();
                break;

            case R.id.login_submit:
                if(Student.isValidEmail(mUserEmail.getText().toString()) && Student.isValidPassword(mUserPassword.getText().toString())) {

                    Log.i("TPV","Valid Input "+mUserEmail.getText().toString()+" "+mUserPassword.getText().toString());
                    DatabaseHelper databaseHelper = new DatabaseHelper(LoginActivity.this);
                    Cursor results = databaseHelper.authenticateUser(mUserEmail.getText().toString(), mUserPassword.getText().toString());

                    if (results.getCount() == 0) {
                        Toast.makeText(LoginActivity.this, "User Doesn't Exist", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    while (results.moveToNext()) {
                        emailFromDatabase = results.getString(3);
                        passwordFromDatabase = results.getString(4);
                        Log.i("TPV", "email id from table= " + emailFromDatabase);
                        Log.i("TPV", "pass from table= " + passwordFromDatabase);
                    }

                    if (emailFromDatabase.equalsIgnoreCase(mUserEmail.getText().toString()) && passwordFromDatabase.equalsIgnoreCase(mUserPassword.getText().toString())) {
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
        Log.i("TPV",mUserEmail.getText().toString()+" "+mUserPassword.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                if(requestCode == INTENT_REQUESTCODE){
                    mUserEmail.setText(data.getStringExtra(getString(R.string.EMAIL)));
                    mUserPassword.setText(data.getStringExtra(getString(R.string.PASSWORD)));
                    finish();
                }
        }
    }

    public EditText getmUserEmail() {
        return mUserEmail;
    }

    public void setmUserEmail(EditText mUserEmail) {
        this.mUserEmail = mUserEmail;
    }

    public EditText getmUserPassword() {
        return mUserPassword;
    }

    public void setmUserPassword(EditText mUserPassword) {
        this.mUserPassword = mUserPassword;
    }
}

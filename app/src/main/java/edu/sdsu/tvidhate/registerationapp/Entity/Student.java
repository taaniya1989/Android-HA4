package edu.sdsu.tvidhate.registerationapp.Entity;

import java.util.regex.Pattern;

/**
 * Created by tanvi on 3/25/18.
 */

public class Student {

    private String mRedId;
    private String mFirstName;
    private String mLastName;
    private String mEmailId;
    private String mPassword;

    public Student(String mRedId, String mFirstName, String mLastName, String mEmailId, String mPassword) {
        this.mRedId = mRedId;
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        if(isValidEmail(mEmailId))
            this.mEmailId = mEmailId;
        if(isValidPassword(mPassword))
            this.mPassword = mPassword;
    }

    public static boolean isValidEmail(String email){
        boolean isValid = false;
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+"[a-zA-Z0-9_+&*-]+)*@"+"(?:[a-zA-Z0-9-]+\\.)+[a-z"+"A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return isValid;
        isValid = pat.matcher(email).matches();
        return isValid;
    }

    public static boolean isValidPassword(String password)
    {
        boolean isValid = false;
        if(password.length() >= 8)
            isValid = true;
        return isValid;
    }

    @Override
    public String toString() {
        return "Student{" +
                "mRedId='" + mRedId + '\'' +
                ", mFirstName='" + mFirstName + '\'' +
                ", mLastName='" + mLastName + '\'' +
                ", mEmailId='" + mEmailId + '\'' +
                ", mPassword='" + mPassword + '\'' +
                '}';
    }

    public String getmRedId() {
        return mRedId;
    }

    public void setmRedId(String mRedId) {
        this.mRedId = mRedId;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getmEmailId() {
        return mEmailId;
    }

    public void setmEmailId(String mEmailId) {
        this.mEmailId = mEmailId;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}

package edu.sdsu.tvidhate.registerationapp.Entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Course {

    private String mDescription,mDepartment,mSuffix,mBuilding,mStartTime,
            mMeetingType,mSection,mEndTime,mEnrolled,mDays,mPrerequisite,
            mTitle,mId,mInstructor,mScheduleNo,mUnit,mRoom,mWaitlist,
            mSeats,mFullTitle,mSubject,mCourseNo;
    private HashMap<String,String> courseDetailsInHashMap = new HashMap<>();

    public Course(String mDescription, String mDepartment, String mSuffix, String mBuilding, String mStartTime, String mMeetingType, String mSection, String mEndTime, String mEnrolled, String mDays, String mPrerequisite, String mTitle, String mId, String mInstructor, String mScheduleNo, String mUnit, String mRoom, String mWaitlist, String mSeats, String mFullTitle, String mSubject, String mCourseNo) {
        this.mDescription = mDescription;
        this.mDepartment = mDepartment;
        this.mSuffix = mSuffix;
        this.mBuilding = mBuilding;
        this.mStartTime = mStartTime;
        this.mMeetingType = mMeetingType;
        this.mSection = mSection;
        this.mEndTime = mEndTime;
        this.mEnrolled = mEnrolled;
        this.mDays = mDays;
        this.mPrerequisite = mPrerequisite;
        this.mTitle = mTitle;
        this.mId = mId;
        this.mInstructor = mInstructor;
        this.mScheduleNo = mScheduleNo;
        this.mUnit = mUnit;
        this.mRoom = mRoom;
        this.mWaitlist = mWaitlist;
        this.mSeats = mSeats;
        this.mFullTitle = mFullTitle;
        this.mSubject = mSubject;
        this.mCourseNo = mCourseNo;
    }

    public Course(JSONObject jsonObject) {
        try {
            this.mDescription = jsonObject.getString("description");
            this.courseDetailsInHashMap.put("description",jsonObject.getString("description"));
            this.mDepartment = jsonObject.getString("department");
            this.courseDetailsInHashMap.put("department",jsonObject.getString("department"));
            this.mSuffix = jsonObject.getString("suffix");
            this.courseDetailsInHashMap.put("suffix",jsonObject.getString("suffix"));
            this.mBuilding = jsonObject.getString("building");
            this.courseDetailsInHashMap.put("building",jsonObject.getString("building"));
            this.mStartTime = jsonObject.getString("startTime");
            this.courseDetailsInHashMap.put("startTime",jsonObject.getString("startTime"));
            this.mMeetingType = jsonObject.getString("meetingType");
            this.courseDetailsInHashMap.put("meetingType",jsonObject.getString("meetingType"));
            this.mSection = jsonObject.getString("section");
            this.courseDetailsInHashMap.put("section",jsonObject.getString("section"));
            this.mEndTime = jsonObject.getString("endTime");
            this.courseDetailsInHashMap.put("endTime",jsonObject.getString("endTime"));
            this.mEnrolled = jsonObject.getString("enrolled");
            this.courseDetailsInHashMap.put("enrolled",jsonObject.getString("enrolled"));
            this.mDays = jsonObject.getString("days");
            this.courseDetailsInHashMap.put("days",jsonObject.getString("days"));
            this.mPrerequisite = jsonObject.getString("prerequisite");
            this.courseDetailsInHashMap.put("prerequisite",jsonObject.getString("prerequisite"));
            this.mTitle = jsonObject.getString("title");
            this.courseDetailsInHashMap.put("title",jsonObject.getString("title"));
            this.mId = jsonObject.getString("id");
            this.courseDetailsInHashMap.put("id",jsonObject.getString("id"));
            this.mInstructor = jsonObject.getString("instructor");
            this.courseDetailsInHashMap.put("instructor",jsonObject.getString("instructor"));
            this.mScheduleNo = jsonObject.getString("schedule#");
            this.courseDetailsInHashMap.put("schedule#",jsonObject.getString("schedule#"));
            this.mUnit = jsonObject.getString("units");
            this.courseDetailsInHashMap.put("units",jsonObject.getString("units"));
            this.mRoom = jsonObject.getString("room");
            this.courseDetailsInHashMap.put("room",jsonObject.getString("room"));
            this.mWaitlist = jsonObject.getString("waitlist");
            this.courseDetailsInHashMap.put("waitlist",jsonObject.getString("waitlist"));
            this.mSeats = jsonObject.getString("seats");
            this.courseDetailsInHashMap.put("seats",jsonObject.getString("seats"));
            this.mFullTitle = jsonObject.getString("fullTitle");
            this.courseDetailsInHashMap.put("fullTitle",jsonObject.getString("fullTitle"));
            this.mSubject = jsonObject.getString("subject");
            this.courseDetailsInHashMap.put("subject",jsonObject.getString("subject"));
            this.mCourseNo = jsonObject.getString("course#");
            this.courseDetailsInHashMap.put("course#",jsonObject.getString("course#"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> getCourseDetailsInHashMap() {
        return courseDetailsInHashMap;
    }

    public void setCourseDetailsInHashMap(HashMap<String, String> courseDetailsInHashMap) {
        this.courseDetailsInHashMap = courseDetailsInHashMap;
    }

    @Override
    public String toString() {
        return "Course{" +
                "mDescription='" + mDescription + '\'' +
                ", mDepartment='" + mDepartment + '\'' +
                ", mSuffix='" + mSuffix + '\'' +
                ", mBuilding='" + mBuilding + '\'' +
                ", mStartTime='" + mStartTime + '\'' +
                ", mMeetingType='" + mMeetingType + '\'' +
                ", mSection='" + mSection + '\'' +
                ", mEndTime='" + mEndTime + '\'' +
                ", mEnrolled='" + mEnrolled + '\'' +
                ", mDays='" + mDays + '\'' +
                ", mPrerequisite='" + mPrerequisite + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mId='" + mId + '\'' +
                ", mInstructor='" + mInstructor + '\'' +
                ", mScheduleNo='" + mScheduleNo + '\'' +
                ", mUnit='" + mUnit + '\'' +
                ", mRoom='" + mRoom + '\'' +
                ", mWaitlist='" + mWaitlist + '\'' +
                ", mSeats='" + mSeats + '\'' +
                ", mFullTitle='" + mFullTitle + '\'' +
                ", mSubject='" + mSubject + '\'' +
                ", mCourseNo='" + mCourseNo + '\'' +
                '}';
    }
}

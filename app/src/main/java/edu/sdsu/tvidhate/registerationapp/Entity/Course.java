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

    public Course(){

    }

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

            if(jsonObject.has("description"))
            {
                this.mDescription = jsonObject.getString("description");
                this.courseDetailsInHashMap.put("description",jsonObject.getString("description"));
            }
            if(jsonObject.has("department"))
            {
                this.mDepartment = jsonObject.getString("department");
                this.courseDetailsInHashMap.put("department",jsonObject.getString("department"));
            }
            if(jsonObject.has("suffix"))
            {
                this.mSuffix = jsonObject.getString("suffix");
                this.courseDetailsInHashMap.put("suffix",jsonObject.getString("suffix"));
            }
            if(jsonObject.has("building")) {
                this.mBuilding = jsonObject.getString("building");
                this.courseDetailsInHashMap.put("building", jsonObject.getString("building"));
            }
            if(jsonObject.has("startTime"))
            {
                this.mStartTime = jsonObject.getString("startTime");
                this.courseDetailsInHashMap.put("startTime",jsonObject.getString("startTime"));
            }
            if(jsonObject.has("meetingType"))
            {
                this.mMeetingType = jsonObject.getString("meetingType");
                this.courseDetailsInHashMap.put("meetingType",jsonObject.getString("meetingType"));
            }
            if(jsonObject.has("section"))
            {
                this.mSection = jsonObject.getString("section");
                this.courseDetailsInHashMap.put("section",jsonObject.getString("section"));
            }
            if(jsonObject.has("endTime"))
            {
                this.mEndTime = jsonObject.getString("endTime");
                this.courseDetailsInHashMap.put("endTime",jsonObject.getString("endTime"));
            }
            if(jsonObject.has("enrolled"))
            {
                this.mEnrolled = jsonObject.getString("enrolled");
                this.courseDetailsInHashMap.put("enrolled",jsonObject.getString("enrolled"));
            }
            if(jsonObject.has("days"))
            {
                this.mDays = jsonObject.getString("days");
                this.courseDetailsInHashMap.put("days",jsonObject.getString("days"));
            }
            if(jsonObject.has("prerequisite"))
            {
                this.mPrerequisite = jsonObject.getString("prerequisite");
                this.courseDetailsInHashMap.put("prerequisite", jsonObject.getString("prerequisite"));
            }
            if(jsonObject.has("title"))
            {
                this.mTitle = jsonObject.getString("title");
                this.courseDetailsInHashMap.put("title",jsonObject.getString("title"));
            }
            if(jsonObject.has("id"))
            {
                this.mId = jsonObject.getString("id");
                this.courseDetailsInHashMap.put("id",jsonObject.getString("id"));
            }
            if(jsonObject.has("instructor"))
            {
                this.mInstructor = jsonObject.getString("instructor");
                this.courseDetailsInHashMap.put("instructor",jsonObject.getString("instructor"));
            }
            if(jsonObject.has("schedule#"))
            {
                this.mScheduleNo = jsonObject.getString("schedule#");
                this.courseDetailsInHashMap.put("schedule#", jsonObject.getString("schedule#"));
            }
            if(jsonObject.has("units"))
            {
                this.mUnit = jsonObject.getString("units");
                this.courseDetailsInHashMap.put("units",jsonObject.getString("units"));
            }
            if(jsonObject.has("room"))
            {
                this.mRoom = jsonObject.getString("room");
                this.courseDetailsInHashMap.put("room", jsonObject.getString("room"));
            }
            if(jsonObject.has("waitlist"))
            {
                this.mWaitlist = jsonObject.getString("waitlist");
                this.courseDetailsInHashMap.put("waitlist",jsonObject.getString("waitlist"));
            }
            if(jsonObject.has("seats"))
            {
                this.mSeats = jsonObject.getString("seats");
                this.courseDetailsInHashMap.put("seats", jsonObject.getString("seats"));
            }
            if(jsonObject.has("fullTitle"))
            {
                this.mFullTitle = jsonObject.getString("fullTitle");
                this.courseDetailsInHashMap.put("fullTitle", jsonObject.getString("fullTitle"));
            }
            if(jsonObject.has("subject"))
            {
                this.mSubject = jsonObject.getString("subject");
                this.courseDetailsInHashMap.put("subject", jsonObject.getString("subject"));
            }
            if(jsonObject.has("course#"))
            {
                this.mCourseNo = jsonObject.getString("course#");
                this.courseDetailsInHashMap.put("course#", jsonObject.getString("course#"));
            }
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

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmDepartment() {
        return mDepartment;
    }

    public void setmDepartment(String mDepartment) {
        this.mDepartment = mDepartment;
    }

    public String getmSuffix() {
        return mSuffix;
    }

    public void setmSuffix(String mSuffix) {
        this.mSuffix = mSuffix;
    }

    public String getmBuilding() {
        return mBuilding;
    }

    public void setmBuilding(String mBuilding) {
        this.mBuilding = mBuilding;
    }

    public String getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

    public String getmMeetingType() {
        return mMeetingType;
    }

    public void setmMeetingType(String mMeetingType) {
        this.mMeetingType = mMeetingType;
    }

    public String getmSection() {
        return mSection;
    }

    public void setmSection(String mSection) {
        this.mSection = mSection;
    }

    public String getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }

    public String getmEnrolled() {
        return mEnrolled;
    }

    public void setmEnrolled(String mEnrolled) {
        this.mEnrolled = mEnrolled;
    }

    public String getmDays() {
        return mDays;
    }

    public void setmDays(String mDays) {
        this.mDays = mDays;
    }

    public String getmPrerequisite() {
        return mPrerequisite;
    }

    public void setmPrerequisite(String mPrerequisite) {
        this.mPrerequisite = mPrerequisite;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmInstructor() {
        return mInstructor;
    }

    public void setmInstructor(String mInstructor) {
        this.mInstructor = mInstructor;
    }

    public String getmScheduleNo() {
        return mScheduleNo;
    }

    public void setmScheduleNo(String mScheduleNo) {
        this.mScheduleNo = mScheduleNo;
    }

    public String getmUnit() {
        return mUnit;
    }

    public void setmUnit(String mUnit) {
        this.mUnit = mUnit;
    }

    public String getmRoom() {
        return mRoom;
    }

    public void setmRoom(String mRoom) {
        this.mRoom = mRoom;
    }

    public String getmWaitlist() {
        return mWaitlist;
    }

    public void setmWaitlist(String mWaitlist) {
        this.mWaitlist = mWaitlist;
    }

    public String getmSeats() {
        return mSeats;
    }

    public void setmSeats(String mSeats) {
        this.mSeats = mSeats;
    }

    public String getmFullTitle() {
        return mFullTitle;
    }

    public void setmFullTitle(String mFullTitle) {
        this.mFullTitle = mFullTitle;
    }

    public String getmSubject() {
        return mSubject;
    }

    public void setmSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    public String getmCourseNo() {
        return mCourseNo;
    }

    public void setmCourseNo(String mCourseNo) {
        this.mCourseNo = mCourseNo;
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

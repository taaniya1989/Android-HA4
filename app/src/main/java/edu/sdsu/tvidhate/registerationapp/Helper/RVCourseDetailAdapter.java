package edu.sdsu.tvidhate.registerationapp.Helper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import edu.sdsu.tvidhate.registerationapp.R;

public class RVCourseDetailAdapter extends RecyclerView.Adapter<RVCourseDetailAdapter.CourseViewHolder>{

    private HashMap<String,String> coursesDetails;
    List<String> keys;
    List<String> values;

    class CourseViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv;
        TextView mCourseKey,mCourseValue;

        CourseViewHolder(View itemView) {
            super(itemView);
            rv = itemView.findViewById(R.id.courseDetails);
            mCourseKey = itemView.findViewById(R.id.courseKey);
            mCourseValue = itemView.findViewById(R.id.courseValue);
        }
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_detail_row_item, viewGroup, false);
        CourseViewHolder pvh = new CourseViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CourseViewHolder courseViewHolder, int i) {
    courseViewHolder.
        mCourseValue.setText(coursesDetails.get(keys.get(i)));
    courseViewHolder.mCourseKey.setText(keys.get(i));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public RVCourseDetailAdapter(HashMap<String,String> courses) {
        this.coursesDetails = courses;
        this.keys = new ArrayList<String>(courses.keySet());
    }

    @Override
    public int getItemCount() {
        return coursesDetails.size();
    }
}

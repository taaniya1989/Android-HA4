package edu.sdsu.tvidhate.registerationapp.Helper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.sdsu.tvidhate.registerationapp.R;

public class RVCourseDetailAdapter extends RecyclerView.Adapter<RVCourseDetailAdapter.CourseViewHolder>{

    private HashMap<String,String> coursesDetails;
    private List<String> keys;

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

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_detail_row_item, viewGroup, false);
        return new CourseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder courseViewHolder, int i) {
    courseViewHolder.
        mCourseValue.setText(coursesDetails.get(keys.get(i)));
    courseViewHolder.mCourseKey.setText(keys.get(i));
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public RVCourseDetailAdapter(HashMap<String,String> courses) {
        this.coursesDetails = courses;
        this.keys = new ArrayList<>(courses.keySet());
    }

    @Override
    public int getItemCount() {
        return coursesDetails.size();
    }
}

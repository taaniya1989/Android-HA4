package edu.sdsu.tvidhate.registerationapp.Helper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.sdsu.tvidhate.registerationapp.Activity.CourseDetailActivity;
import edu.sdsu.tvidhate.registerationapp.Entity.Course;
import edu.sdsu.tvidhate.registerationapp.R;

public class RVCourseIDAdapter extends RecyclerView.Adapter<RVCourseIDAdapter.CourseViewHolder> implements ServerConstants{

    private List<Course> courses;

    class CourseViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv;
        TextView mCourseID,mCourseTitle,mCourseTime,mCourseInstructor,mCourseWaitlist;

        CourseViewHolder(View itemView) {
            super(itemView);
            rv = itemView.findViewById(R.id.subjectListView);
            mCourseID = itemView.findViewById(R.id.courseid);
            mCourseTitle = itemView.findViewById(R.id.coursetitle);
            mCourseTime = itemView.findViewById(R.id.coursetime);
            mCourseInstructor = itemView.findViewById(R.id.courseinstructor);
            mCourseWaitlist = itemView.findViewById(R.id.coursewaitlist);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    String clickedDataItem;

                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        clickedDataItem = courses.get(pos).getmId();
                        Intent courseDetailIntent = new Intent(v.getContext(), CourseDetailActivity.class);
                        courseDetailIntent.putExtra(COURSE_ID,clickedDataItem);
                        v.getContext().startActivity(courseDetailIntent);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item, viewGroup, false);
        return new CourseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder courseViewHolder, int i)
    {
        String courseNumber = courses.get(i).getmSubject()+"-"+courses.get(i).getmCourseNo();
        String courseTimes = courses.get(i).getmStartTime()+"-"+courses.get(i).getmEndTime();
        courseViewHolder.mCourseID.setText(courseNumber);
        courseViewHolder.mCourseTitle.setText(courses.get(i).getmTitle());
        courseViewHolder.mCourseInstructor.setText(courses.get(i).getmInstructor());
        courseViewHolder.mCourseTime.setText(courseTimes);
        courseViewHolder.mCourseWaitlist.setText(courses.get(i).getmWaitlist());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public RVCourseIDAdapter(List<Course> courses) {
       this.courses = courses;
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}

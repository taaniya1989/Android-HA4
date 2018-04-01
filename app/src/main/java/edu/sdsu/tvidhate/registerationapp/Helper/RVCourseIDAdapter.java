package edu.sdsu.tvidhate.registerationapp.Helper;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.sdsu.tvidhate.registerationapp.Activity.CourseDetailActivity;
import edu.sdsu.tvidhate.registerationapp.Entity.Course;
import edu.sdsu.tvidhate.registerationapp.R;

public class RVCourseIDAdapter extends RecyclerView.Adapter<RVCourseIDAdapter.CourseViewHolder>{

    private List<String> courses;

    class CourseViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv;
        TextView mCourseID;

        CourseViewHolder(View itemView) {
            super(itemView);
            rv = itemView.findViewById(R.id.subjectListView);
            mCourseID = itemView.findViewById(R.id.courseid);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();

                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION){
                        String clickedDataItem = courses.get(pos);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem, Toast.LENGTH_SHORT).show();
                    }

                    Intent courseDetailIntent = new Intent(v.getContext(), CourseDetailActivity.class);
                    v.getContext().startActivity(courseDetailIntent);
                    // custom dialog
/*                    final Dialog dialog = new Dialog(v.getContext());
                    dialog.setContentView(R.layout.custom_dialog);
                    dialog.setTitle("Take Action");
                    Button viewButton = dialog.findViewById(R.id.viewButton);
                    viewButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent courseDetailIntent = new Intent(this, CourseDetailActivity.class);

                        }
                    });
                    dialog.show();*/
                }
            });
        }
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item, viewGroup, false);
        CourseViewHolder pvh = new CourseViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(CourseViewHolder courseViewHolder, int i) {
    courseViewHolder.
        mCourseID.setText(courses.get(i).toString());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public RVCourseIDAdapter(List<String> courses) {
        this.courses = courses;
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}

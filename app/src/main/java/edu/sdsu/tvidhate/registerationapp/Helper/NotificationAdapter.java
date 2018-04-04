package edu.sdsu.tvidhate.registerationapp.Helper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import edu.sdsu.tvidhate.registerationapp.Entity.Notifications;
import edu.sdsu.tvidhate.registerationapp.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationsViewHolder>{

    private List<Notifications> notificationList;

    class NotificationsViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv;
        TextView mNotification;

        NotificationsViewHolder(View itemView) {
            super(itemView);
            rv = itemView.findViewById(R.id.notificationsListRV);
            mNotification = itemView.findViewById(R.id.notificationid);
        }
    }

    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_row_item, viewGroup, false);
        return new NotificationsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsViewHolder NotificationsViewHolder, int i)
    {
        NotificationsViewHolder.mNotification.setText(notificationList.get(i).getNotificationMessage());
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public NotificationAdapter(List<Notifications> notificationList) {
        this.notificationList = notificationList;
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}

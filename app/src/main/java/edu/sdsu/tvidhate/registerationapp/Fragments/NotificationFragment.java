package edu.sdsu.tvidhate.registerationapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.sdsu.tvidhate.registerationapp.Activity.LoginActivity;
import edu.sdsu.tvidhate.registerationapp.Helper.NotificationAdapter;
import edu.sdsu.tvidhate.registerationapp.R;

public class NotificationFragment extends Fragment
{
    RecyclerView notificationRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notification_fragment, container, false);

        this.notificationRecyclerView = view.findViewById(R.id.notificationsListRV);
        LinearLayoutManager notificationLinearLayoutManager = new LinearLayoutManager(getContext());
        this.notificationRecyclerView.setLayoutManager(notificationLinearLayoutManager);
        Log.i("TPV",LoginActivity.allNotifications.toString());
        this.notificationRecyclerView.setAdapter(new NotificationAdapter(LoginActivity.allNotifications));
        return view;
    }
}

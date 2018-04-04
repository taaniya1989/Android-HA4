package edu.sdsu.tvidhate.registerationapp.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import edu.sdsu.tvidhate.registerationapp.Fragments.DashboardFragment;
import edu.sdsu.tvidhate.registerationapp.Fragments.HomeFragment;
import edu.sdsu.tvidhate.registerationapp.R;

public class HomeScreenActivity extends AppCompatActivity {

    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentManager.beginTransaction().replace(R.id.DataFrame,new HomeFragment()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    fragmentManager.beginTransaction().replace(R.id.DataFrame,new DashboardFragment()).commit();
                    return true;
                case R.id.logout:
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        fragmentManager.beginTransaction().replace(R.id.DataFrame,new HomeFragment()).commit();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setTitle(R.string.app_name);
    }

}

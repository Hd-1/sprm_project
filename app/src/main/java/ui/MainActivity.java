package ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.sprm_project.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import fragment.mainPage.MainPageFragment;
import fragment.messages.MessagesFragment;
import fragment.notifications.NotificationsFragment;
import profileController.Profile;

public class MainActivity extends AppCompatActivity{

    private MainActivityViewModel viewModel;
    private NavController navController;
    private AppBarConfiguration mAppBarConfiguration;

    private TextView drawername, drawerfollows, drawersubs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.navigation_layout);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.init();

        checkIfSignedIn();

        //Init Navigation Components
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationDrawer = findViewById(R.id.nav_view);
        View headerView = navigationDrawer.getHeaderView(0);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);

        drawername = headerView.findViewById(R.id.drawername);
        drawerfollows = headerView.findViewById(R.id.drawerfollows);
        drawersubs = headerView.findViewById(R.id.drawersubs);

        //Setup Navigation Components
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile, R.id.nav_travels, R.id.nav_documentation,
                R.id.nav_tips, R.id.nav_jobs, R.id.nav_settings)
                .setOpenableLayout(drawerLayout)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationDrawer, navController);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener bottomnavListener =
            item -> {
                Fragment selectedF = null;

                switch (item.getItemId()){
                    case R.id.bottomnav_mainpage :
                        selectedF = new MainPageFragment();
                        break;
                    case R.id.bottomnav_notifications :
                        selectedF = new NotificationsFragment();
                        break;
                    case R.id.bottomnav_messages :
                        selectedF = new MessagesFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedF).commit();
                return true;
    };

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }

    private void checkIfSignedIn() {
        viewModel.getCurrentUser().observe(this, user -> {
            if (user != null) {
                drawername.setText(user.getDisplayName());
            } else
                startLoginActivity();
        });
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    public void signOut(View v) {
        viewModel.signOut();
    }

    public void profile(View v){
        startActivity(new Intent(getApplicationContext(), Profile.class));
    }

}
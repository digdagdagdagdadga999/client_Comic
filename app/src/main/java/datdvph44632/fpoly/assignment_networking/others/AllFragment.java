package datdvph44632.fpoly.assignment_networking.others;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import datdvph44632.fpoly.assignment_networking.Fragment.AllComicFragment;
import datdvph44632.fpoly.assignment_networking.Fragment.AllUserFragment;
import datdvph44632.fpoly.assignment_networking.Fragment.DangXuatFragment;
import datdvph44632.fpoly.assignment_networking.Fragment.HomeFragment;
import datdvph44632.fpoly.assignment_networking.R;

public class AllFragment extends AppCompatActivity {

    NavigationView navigationView;

    public static DrawerLayout drawerLayout;

    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_fragment);

        findById();
        Toolbar toolbar = findViewById(R.id.id_toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(AllFragment.this, drawerLayout, toolbar, 0, 0);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.ic_home) {
                    drawerLayout.close();
                    HomeFragment HomeFragment = new HomeFragment();
                    Click(HomeFragment);
                } else if (item.getItemId() == R.id.ic_truyen) {
                    drawerLayout.close();
                    AllComicFragment comicFragment = new AllComicFragment();
                    Click(comicFragment);
                } else if (item.getItemId() == R.id.ic_user) {
                    drawerLayout.close();
                    AllUserFragment userFragment = new AllUserFragment();
                    Click(userFragment);
                } else if (item.getItemId() == R.id.ic_dangxuat) {
                    drawerLayout.close();
                    DangXuatFragment DangXuatFragment = new DangXuatFragment();
                    Click(DangXuatFragment);
                }
                return true;
            }
        });
    }

    public void Click(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void findById() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

    }
}
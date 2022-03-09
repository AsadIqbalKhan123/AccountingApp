package com.shashank.expensermanager.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.shashank.expensermanager.Add_Category;
import com.shashank.expensermanager.BuildConfig;
import com.shashank.expensermanager.Profile_Activity;
import com.shashank.expensermanager.Proposition_Activity;
import com.shashank.expensermanager.R;
import com.shashank.expensermanager.Report_Activity;
import com.shashank.expensermanager.SettingsActivity;
import com.shashank.expensermanager.adapters.SectionsPageAdapter;
import com.shashank.expensermanager.fragments.BalanceFragment;
import com.shashank.expensermanager.fragments.CustomBottomSheetDialogFragment;
import com.shashank.expensermanager.fragments.ExpenseFragment;

public class MainActivity extends AppCompatActivity {

    public static FloatingActionButton fab;
    private ViewPager mViewPager;
    private Toolbar toolbar;

    private DrawerLayout mainDrawerLayout;
    private NavigationView navigationView;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainDrawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.naviation_view);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);


        setUpData();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mainDrawerLayout, toolbar,
                R.string.nav_open, R.string.nav_close);
        mainDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


//        toolbar.setOnClickListener(v -> mainDrawerLayout.openDrawer(GravityCompat.START));


//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });


        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CustomBottomSheetDialogFragment().show(getSupportFragmentManager(), "Dialog");

            }
        });


        navigationView.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home_id:

                    break;
                case R.id.Categories_id:
                    Intent i = new Intent(MainActivity.this, Add_Category.class);
                    startActivity(i);
                    break;
                case R.id.logout_id:
                    new AlertDialog.Builder(MainActivity.this).setTitle("Are you sure?").setMessage("Do you want to logout?").
                            setPositiveButton("Logout", (dialog, which) -> {
//                                FirebaseAuth.getInstance().signOut();

                            }).show();

                    break;


                case R.id.setting_id:

                    Intent set = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(set);


                    // TODO OPEN PHONE APP WITH NUMBER 9999
                    break;

                case R.id.share_it_id:

                    try {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name Accounting app Download and enjoy it");
                        String shareMessage = "\nLet me recommend you this application\n\n";
                        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                        startActivity(Intent.createChooser(shareIntent, "Share Via"));
                    } catch (Exception e) {
                        //e.toString();
                    }

                    break;

                case R.id.profile_id:

                    Intent k = new Intent(MainActivity.this, Profile_Activity.class);
                    startActivity(k);

                    break;

                case R.id.report_id:

                    Intent repo = new Intent(MainActivity.this, Report_Activity.class);
                    startActivity(repo);

                    break;

                case R.id.backup_id:

                    Intent myspinner = new Intent(MainActivity.this, Proposition_Activity.class);
                    startActivity(myspinner);

                    break;

            }

            mainDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });


    }

    @Override
    public void onBackPressed() {

        if (mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    private void setUpData() {

        View view = navigationView.getHeaderView(0);

//        imageView1 = findViewById(R.id.add_imge_btn);

        view.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, Profile_Activity.class))
        );

//        view.setOnClickListener(v -> {
//            startActivity(new Intent(dashboard.this, Profile_Activity.class));
//        });


//        {
//            Glide.with(dashboard.this).load(uri).into
//                    ((CircleImageView) view.findViewById(R.id.img_profile));
//        });
    }


    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new ExpenseFragment(), "Expenses");
        adapter.addFragment(new BalanceFragment(), "Total Balance");
        viewPager.setAdapter(adapter);
    }


}

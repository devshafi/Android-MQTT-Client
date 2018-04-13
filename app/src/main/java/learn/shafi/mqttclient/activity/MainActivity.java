package learn.shafi.mqttclient.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import fragment.ConfigurationFragment;
import fragment.WorkingFragment;
import learn.shafi.mqttclient.R;

public class MainActivity extends AppCompatActivity {


    Toolbar mainToolbar;
    TabLayout mainTabLayout;
    ViewPager mainViewPager;
    SectionPagerAdapter sectionPagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainToolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);


        mainTabLayout = findViewById(R.id.mainTabLayout);
        mainViewPager = findViewById(R.id.mainViewPager);
        sectionPagerAdapter =new SectionPagerAdapter(getSupportFragmentManager());
        mainViewPager.setAdapter(sectionPagerAdapter);
        mainTabLayout.setupWithViewPager(mainViewPager);



    }


    public class SectionPagerAdapter extends FragmentPagerAdapter {

        SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new WorkingFragment();
                case 1:
                    return new ConfigurationFragment();

            }

            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {


            switch (position){
                case 0:
                    return "Connection";
                case 1:
                    return "Configuration";

            }

            return null;
        }
    }



}



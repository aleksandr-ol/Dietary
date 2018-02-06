package com.example.immortal.dietary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int DIET_REMAINDER = 0;
    public static final int WATER_REMAINDER = 1;
    public static final int FRAGMENTS = 2;

    private FragmentPagerAdapter _fragmentPagerAdapter;
    private final List<Fragment> _fragments = new ArrayList<Fragment>();

    private ViewPager _viewPager;

//    private Context context;
    ListView listView;
    RemaidsCursorAdapter cursorAdapter;
    private Cursor cursor;
    DatabaseConnector db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diet_remainder_fragment);

        listView = (ListView) findViewById(R.id.listView);


    }

    @Override
    public void onResume() {
        super.onResume();
        db = new DatabaseConnector(this);
        cursor = db.getAllRemainds();

        cursorAdapter = new RemaidsCursorAdapter(this, cursor);
        listView.setAdapter(cursorAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0, "Додати нове нагадування");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case 1:
                Intent intent = new Intent(this, CreateRemaindActivity.class);
                startActivity(intent);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

//    _fragments.add(DIET_REMAINDER, new DietRemainder());
//        _fragments.add(WATER_REMAINDER, new WaterRemainder());
//
//    _fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
//        @Override
//        public Fragment getItem(int position) {
//            return _fragments.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return FRAGMENTS;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            switch (position){
//                case DIET_REMAINDER:
//                    return "Нагадування про прийом їжі";
//                case WATER_REMAINDER:
//                    return "Нагадування про прийом води";
//                default:
//                    return null;
//            }
//        }
//    };
//
//    _viewPager = (ViewPager) findViewById(R.id.pager);
//        _viewPager.setAdapter(_fragmentPagerAdapter);
//        _viewPager.setCurrentItem(0);
}

package com.example.immortal.dietary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TableLayout;

public class DietRemainder extends Fragment {
    private Context context;
    ListView listView;
    RemaidsCursorAdapter cursorAdapter;
    private Cursor cursor;
    DatabaseConnector db;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.diet_remainder_fragment, container, false);
        context = view.getContext();

        listView = (ListView) view.findViewById(R.id.listView);

//        db = new DatabaseConnector(context);
//        cursor = db.getAllRemainds();
//
//        cursorAdapter = new RemaidsCursorAdapter(context, cursor);
//        listView.setAdapter(cursorAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        db = new DatabaseConnector(context);
        cursor = db.getAllRemainds();

        cursorAdapter = new RemaidsCursorAdapter(context, cursor);
        listView.setAdapter(cursorAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0,1,0, "Додати нове нагадування");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case 1:
                Intent intent = new Intent(context, CreateRemaindActivity.class);
                startActivity(intent);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}

package com.example.immortal.dietary;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class RemaidsCursorAdapter extends CursorAdapter {

    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public RemaidsCursorAdapter(Context context, Cursor c) {
        super(context, c);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = mLayoutInflater.inflate(R.layout.row_remaind_element, parent, false);
        return v;
    }

    @Override
    public void bindView(View v, Context context, Cursor c) {
        final String  title = c.getString(c.getColumnIndexOrThrow("title"));
        final String time = c.getString(c.getColumnIndexOrThrow("time"));
        int is_checked = c.getInt(c.getColumnIndexOrThrow("is_checked"));
        final int id = c.getInt(c.getColumnIndexOrThrow("_id"));

        TextView title_text = (TextView) v.findViewById(R.id.row_title_text_view);
        if (title_text != null) {
            title_text.setText(title);
        }


        TextView date_text = (TextView) v.findViewById(R.id.row_time_text_view);
        if (date_text != null) {
            date_text.setText(time);
        }


        final Switch row_switch = (Switch) v.findViewById(R.id.row_switch);
        row_switch.setChecked(is_checked != 0);

        if(row_switch.isChecked()){
            initNotification(id, time, title);
        }

        row_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(row_switch.isChecked()){
                    DatabaseConnector db = new DatabaseConnector(mContext);
                    db.updateRemaind(id, title, time, isChecked ? 1 : 0);
                    initNotification(id, time, title);
                }
            }
        });
    }

    private void initNotification(int id, String time, String title){
        Intent intent = new Intent(mContext, NotificationReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("id", id);

        PendingIntent pIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarm = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

        String hour = time.split(":")[0];
        String minute = time.split(":")[1];

//        hour = "5";
//        minute = "9";

        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        cal.set(Calendar.MINUTE, Integer.parseInt(minute));
        cal.set(Calendar.SECOND, 0);

//        Toast.makeText(mContext, Integer.parseInt(hour) + " " + Integer.parseInt(minute)
//                + " " + cal.getTimeInMillis(), Toast.LENGTH_LONG).show();

        long timeStart = cal.getTimeInMillis();

        if(timeStart < System.currentTimeMillis())
            timeStart += 60*60*24*1000;

        alarm.setRepeating(AlarmManager.RTC_WAKEUP, timeStart, AlarmManager.INTERVAL_DAY, pIntent);
    }
}

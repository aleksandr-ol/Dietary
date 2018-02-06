package com.example.immortal.dietary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class CreateRemaindActivity extends AppCompatActivity {
    EditText remindTitle, remindTime;
    CheckBox remindCheckBox;
    Button createButton;

    String title, time;
    int isChecked;

    DatabaseConnector db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_remaind);

        remindTitle = (EditText) findViewById(R.id.remind_title_editText);
        remindTime = (EditText) findViewById(R.id.remind_time_editText);
        remindCheckBox = (CheckBox) findViewById(R.id.remind_checkBox);
        createButton = (Button) findViewById(R.id.remind_create_button);

        db = new DatabaseConnector(this);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = remindTitle.getText().toString();
                time = remindTime.getText().toString();
                isChecked = remindCheckBox.isChecked() ? 1 : 0;
                db.insertRemaind(title, time, isChecked);
                finish();
            }
        });
    }
}

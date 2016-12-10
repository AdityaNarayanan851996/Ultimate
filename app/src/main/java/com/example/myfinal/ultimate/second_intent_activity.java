package com.example.myfinal.ultimate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class second_intent_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_intent_activity);

        TextView txtView = (TextView)findViewById(R.id.tvRead);
        Bundle bundle = getIntent().getExtras();



            String data = bundle.getString("data",null);
        if(bundle != null) {
            int age = bundle.getInt("age",0);
            txtView.setText(data+" Age- "+age);

        }
       

    }
}

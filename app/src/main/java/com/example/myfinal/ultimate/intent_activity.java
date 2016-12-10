package com.example.myfinal.ultimate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class intent_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_activity);


        final EditText editText = (EditText)findViewById(R.id.edtdata);
        final EditText editText_age = (EditText)findViewById(R.id.edt_Age);

        Button button = (Button)findViewById(R.id.passbtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),second_intent_activity.class);
                intent.putExtra("data",editText.getText().toString());

                int age = Integer.parseInt(editText_age.getText().toString());
                intent.putExtra("age",age);

                startActivity(intent);


            }
        });
    }
}

package com.example.myfinal.ultimate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class second extends AppCompatActivity {
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button btn2 = (Button)findViewById(R.id.button3);//BACK TO MAIN

        final Button action=(Button)findViewById(R.id.action);

        final ImageView fimg = (ImageView)findViewById(R.id.imageView) ;


        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Count= "+counter,Toast.LENGTH_SHORT).show();
                counter++;
                if (counter == 1) {
                    fimg.setImageResource(R.drawable.abx);
                    action.setBackgroundResource(R.drawable.abx);
                }
                if(counter==2){
                    fimg.setImageResource(R.drawable.bc);
                    action.setBackgroundResource(R.drawable.bc);
                }
                if(counter==3){
                    fimg.setImageResource(R.drawable.sa);
                    action.setBackgroundResource(R.drawable.sa);
                }
                if(counter>3)
                {
                    counter=0;
                }

            }
        });

        btn2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                startActivity(new Intent(second.this, MainActivity.class));
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}

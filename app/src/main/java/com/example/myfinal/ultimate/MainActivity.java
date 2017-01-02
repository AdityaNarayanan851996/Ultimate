package com.example.myfinal.ultimate;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

import static com.example.myfinal.ultimate.R.layout.flickr_row;
import static com.example.myfinal.ultimate.R.layout.row;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private GoogleApiClient client;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;
    private Button intents;
    private Button sgnup;
    private Button flickr;
    private Button sign_up;
    private Button json;
    private Button login1;
    private Button fragments;
    private ViewGroup mRoot;
    final Explode explode = new Explode();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_main);


        // Enable Content Transistions.




        mRoot = (ViewGroup)findViewById(R.id.activity_main);

        sign_up = (Button) findViewById(R.id.signup);
        json = (Button)findViewById(R.id.json);
        flickr = (Button)findViewById(R.id.flickr);
        intents = (Button)findViewById(R.id.intents);
        sgnup = (Button)findViewById(R.id.sgnup);
        login1 = (Button)findViewById(R.id.login2);
        fragments = (Button)findViewById(R.id.fragment);




        mRoot.setOnClickListener(this);




        fragments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,fragment.class));
            }
        });

        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this,SignIn.class));


            }
        });


        sgnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,crud.class));
                getWindow().setExitTransition(explode);

            }
        });

        intents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,intent_activity.class));
            }
        });

        json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,json_applet.class));

            }
        });

        flickr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,flickr_applet.class));
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                startActivity(new Intent(MainActivity.this, second.class));
            }
        });

        Log.i(TAG, " APP CREATED ");

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, " APP RESUME");
    }

    //ON CLOSING
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, " APP PAUSED");
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, " APP DESTROYED");
    }

    @Override
    public void onClick(View view) {
        Explode fade = new Explode();
        fade.setDuration(1000);
        TransitionManager.beginDelayedTransition(mRoot,fade);
      ;

    }



    public void toggleVisibility(View...views){
        for(View Current:views){
            if(Current.getVisibility()==View.VISIBLE){
                Current.setVisibility(View.INVISIBLE);

            }
        }

    }
}




/*
int counter= 0 ;
/**
 * ATTENTION: This was auto-generated to implement the App Indexing API.
 * See https://g.co/AppIndexing/AndroidStudio for more information.




@Override
protected void onCreate(Bundle savedInstanceState) {


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        }

@Override
public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);


        return true;
        //return super.onCreateOptionsMenu(menu);
        }

@Override
public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
        case R.id.action_text:
        Toast.makeText(getApplicationContext(),"YOU MORON",Toast.LENGTH_LONG).show();
        break;
        case R.id.action_layout:
        counter++;
        Toast.makeText(getApplicationContext(),"COUNTER IS =    "+counter,Toast.LENGTH_LONG).show();
        break;
        }
        return super.onOptionsItemSelected(item);
*/
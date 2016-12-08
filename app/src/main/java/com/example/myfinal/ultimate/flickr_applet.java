package com.example.myfinal.ultimate;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.myfinal.ultimate.models.MovieModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class flickr_applet extends AppCompatActivity {

    private Button rld;
    private TextView textView3;
    private ImageView imageView3;
    private ListView immview;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr_applet);

        //https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=d98f34e2210534e37332a2bb0ab18887&format=json&extras=url_n&nojsoncallback=1

        // Create global configuration and initialize ImageLoader with this config
        // Create default options which will be used for every
//  displayImage(...) call if no options will be passed to this method


       // immview = (ListView)findViewById(R.id.imgview);
        progressDialog  = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("LOADING...");
        imageView3 = (ImageView)findViewById(R.id.imageView3);
///////////////////////////////////////////////////////////////////////////////////////
////////////// UNIVERSAL IMAGE LOADER INITIALISATION /////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()


                .cacheInMemory(true)
                .cacheOnDisk(true)

                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())

                .defaultDisplayImageOptions(defaultOptions)

                .build();
        ImageLoader.getInstance().init(config); // Do it on Application start

        textView3 = (TextView) findViewById(R.id.textView3);
        rld = (Button) findViewById(R.id.rld);

        rld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new JSON().execute("https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=d98f34e2210534e37332a2bb0ab18887&format=json&extras=url_n&nojsoncallback=1");
            }
        });
    }


    public class JSON extends AsyncTask<String, String, String> {
        private String imageUrl;

        //   private String image_url;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }



        @Override
        protected String doInBackground(String... strings) {
            BufferedReader reader = null;
            HttpURLConnection connection = null;

            try {

                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();

                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJSON = buffer.toString();
                JSONObject parentobject = new JSONObject(finalJSON);
                parentobject = parentobject.getJSONObject("photos");                //OBJECT IN FLICKR API
                JSONArray photo = parentobject.getJSONArray("photo");               //OBJECT ARRAY IN "PHOTOS"

                StringBuffer last = new StringBuffer();                             //DUMMY STRING

                StringBuffer image = new StringBuffer();                            //IMAGE LINK STRING
                for(int i=0;i<photo.length();i++) {

                    JSONObject reqdObj = photo.getJSONObject(i);                    //reqdObj will access the photo array

                    String farm = reqdObj.getString("farm");
                    String server = reqdObj.getString("server");

                    String id = reqdObj.getString("id");
                    String secret = reqdObj.getString("secret");


                    imageUrl = "https://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+"_n.jpg"+"";



                }

                return imageUrl.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

            final ProgressBar progressBar2 = (ProgressBar)findViewById(R.id.progressBar2);
            ImageLoader.getInstance().displayImage(result, imageView3, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {

                    progressBar2.setVisibility(View.VISIBLE);

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar2.setVisibility(View.GONE);

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar2.setVisibility(View.GONE);

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar2.setVisibility(View.GONE);

                }
            });
            textView3.setText(result);

        }
    }

}
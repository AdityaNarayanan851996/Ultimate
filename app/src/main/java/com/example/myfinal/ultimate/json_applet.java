package com.example.myfinal.ultimate;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.AsyncListUtil;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfinal.ultimate.models.MovieModel;
import com.google.gson.Gson;
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
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.support.v7.appcompat.R.styleable.MenuItem;
import static android.view.View.VISIBLE;
import static com.example.myfinal.ultimate.R.id.lvMovies;

public class json_applet extends AppCompatActivity  {

    private TextView text;
    private ListView listview;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_applet);

        listview = (ListView)findViewById(lvMovies);


        progressDialog  = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("LOADING...");
        // Create global configuration and initialize ImageLoader with this config
        // Create default options which will be used for every
//  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()


                .cacheInMemory(true)
                .cacheOnDisk(true)

        .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())

        .defaultDisplayImageOptions(defaultOptions)

        .build();
        ImageLoader.getInstance().init(config); // Do it on Application start




        //https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=d98f34e2210534e37332a2bb0ab18887&format=json&extras=url_n&nojsoncallback=1


        //https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=d98f34e2210534e37332a2bb0ab18887&format=json&extras=url_n
        //http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt
        //http://jsonviewer.stack.hu/

        //MORE THAN 1 OBJECTS!//

        //http://jsonparsing.parseapp.com/jsonData/moviesDemoList.txt
        //http://jsonparsing.parseapp.com/jsonData/moviesData.txt
       // new JSON().execute("http://jsonparsing.parseapp.com/jsonData/moviesDemoList.txt");

        Button hit = (Button) findViewById(R.id.hit);
        //[ctrl, alt , shift , T ] to make it field data //
//        text = (TextView) findViewById(R.id.textView);
       hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=d98f34e2210534e37332a2bb0ab18887&format=json&extras=url_n

                //https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=d98f34e2210534e37332a2bb0ab18887&format=json&nojsoncallback=1

                //http://jsonparsing.parseapp.com/jsonData/moviesDemoItem.txt
                //http://jsonviewer.stack.hu/

                //MORE THAN 1 OBJECTS!//

                //http://jsonparsing.parseapp.com/jsonData/moviesDemoList.txt
                //http://jsonparsing.parseapp.com/jsonData/moviesData.txt
                new JSON().execute("http://jsonparsing.parseapp.com/jsonData/moviesData.txt");

            }

        });
    }


    public class JSON extends AsyncTask<String,String, List<MovieModel>>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected List<MovieModel> doInBackground(String... urls) {
            BufferedReader reader = null;
            HttpURLConnection connection = null;


            try {
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);

                }
                //  ~!~      TO GET IN PROPER FORMAT      ~!~   //
                String finalJSON = buffer.toString();
                JSONObject parentobject = new JSONObject(finalJSON);
                JSONArray parentarray = parentobject.getJSONArray("movies");



                List<MovieModel> movieList = new ArrayList<>();
                Gson gson = new Gson();
      //          StringBuffer last = new StringBuffer();
                for(int i=0;i<parentarray.length();i++) {


                    JSONObject reqdObj = parentarray.getJSONObject(i);
 //                   MovieModel movieModel = new MovieModel();

                    MovieModel movieModel = gson.fromJson(reqdObj.toString(),MovieModel.class);

  /*                  movieModel.setMovie(reqdObj.getString("movie"));
                    movieModel.setYear(reqdObj.getInt("year"));
                    movieModel.setRating((float) reqdObj.getDouble("rating"));
                    movieModel.setDuration(reqdObj.getString("duration"));
                    movieModel.setDirector(reqdObj.getString("director"));
                    movieModel.setTagline(reqdObj.getString("tagline"));
                    movieModel.setImage(reqdObj.getString("image"));
                    movieModel.setStory(reqdObj.getString("story"));



                    List<MovieModel.Cast>   castList = new ArrayList<>();

                    for(int j=0;j<reqdObj.getJSONArray("cast").length();j++) {
                        JSONObject castobj = reqdObj.getJSONArray("cast").getJSONObject(j);         //CAST JSON OBJECT

                        MovieModel.Cast cast = new MovieModel.Cast();           //OBJECT OF CAST CLASS

                        cast.setName(castobj.getString("name"));            //SET THE CLASS WITH JSON OBJECT

                        castList.add(cast);

                       // movieModel.getCastList("cast");
                    }
                    movieModel.setCastList(castList);


*/
                    movieList.add(movieModel);

    //                last.append("movie" + " - " + moviename + "\n" +
      //                          "year" + " - " + year + "\n"



                }

                    return movieList;



                //return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }





        @Override
        protected void onPostExecute(List<MovieModel> result) {
            super.onPostExecute(result);
            progressDialog.dismiss();

            MovieAdapter adapter = new MovieAdapter(getApplicationContext(),R.layout.row,result);
            listview.setAdapter(adapter);


         //   text.setText(result);

        }

    }

    public class MovieAdapter extends ArrayAdapter{

        public List<MovieModel> movieModels;
        int resource;
        private LayoutInflater inflater;

        public MovieAdapter(Context context, int resource, List<MovieModel> objects) {
            super(context, resource, objects);

            movieModels = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);


        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if(convertView==null)
            {
                convertView =  inflater.inflate(resource,null);
                holder = new ViewHolder();

                holder.imageView2 = (ImageView)convertView.findViewById(R.id.imageView2);
                holder.textView = (TextView)convertView.findViewById(R.id.textView);
                holder.textView2 = (TextView)convertView.findViewById(R.id.textView2);
                holder.ratingBar = (RatingBar)convertView.findViewById(R.id.ratingBar);
                holder.textView4 = (TextView)convertView.findViewById(R.id.textView4);
                holder.textView5 = (TextView)convertView.findViewById(R.id.textView5);

                holder.textView6 = (TextView)convertView.findViewById(R.id.textView6);
                holder.textView7 = (TextView)convertView.findViewById(R.id.textView7);

                holder.textView8 = (TextView)convertView.findViewById(R.id.textView8);
                convertView.setTag(holder);


            }
            else{
                holder = (ViewHolder)convertView.getTag();
            }
            final ProgressBar progressBar;



            progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar) ;

            ImageLoader.getInstance().displayImage(movieModels.get(position).getImage(), holder.imageView2, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar.setVisibility(View.GONE);

                }
            }); // Default options will be used


            StringBuffer buffer = new StringBuffer();

            for(MovieModel.Cast cast : movieModels.get(position).getCastList() ){
                    buffer.append(cast.getName()+ " , ");

            }

            holder.textView6.setText(buffer);
            holder.textView7.setText(movieModels.get(position).getStory());
            return convertView;


        }

        class ViewHolder{
            private  ImageView imageView2;
            private  TextView textView;
            private  TextView textView2;
            private  RatingBar ratingBar;
            private  TextView textView4;
            private  TextView textView5;
            private  TextView textView8;


            private  TextView textView6;
            private  TextView textView7;

        }
    }









}









/*
for(int i=0;i<parentarray.length();i++) {
                    JSONObject reqdObj = parentarray.getJSONObject(i);

                    String moviename = reqdObj.getString("movie");
                    int year = reqdObj.getInt("year");
                    int rating = reqdObj.getInt("rating");
                    int duration = reqdObj.getInt("duration");
                    String director = reqdObj.getString("director");
                    String tagline = reqdObj.getString("tagline");

                    // ~                 FOR CAST               ~ //


                    String castarray = buffer.toString();
                    JSONObject cast_parent = new JSONObject(castarray);
                    JSONArray cast_array = parentobject.getJSONArray("cast");
                    StringBuffer castlast = new StringBuffer();

                    for(int j=0;j<cast_array.length();j++){
                        JSONObject castnameobj = parentarray.getJSONObject(i);
                        String castename = castnameobj.getString("name");

                        castlast.append("name" + " - " + castename.toString() + "\n");

                    }



                    last.append("movie" + " - " + moviename + "\n" +
                                "year" + " - " + year + "\n" +
                                "rating" + " - " + rating + "\n" +
                                "duration" + " - " + duration + "\n" +
                                "director" + " - " + director + "\n" +
                                "tagline" + " - " + tagline + "\n" +
                                "Cast : " + "\n" + castlast
                    );

                }






 */
// return last.toString();

//  ~!~              END OF              ~!~   //




























/*

onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.menu_main,menu);


            return true;
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch(item.getItemId()){
                case R.id.action_text:
                    Toast.makeText(getApplicationContext(),"YOU MORON",Toast.LENGTH_LONG).show();
                    break;
                case R.id.action_layout:

                    Toast.makeText(getApplicationContext(),"COUNTER IS =", Toast.LENGTH_LONG).show();
                    break;
            }
            return super.onOptionsItemSelected(item);



 */
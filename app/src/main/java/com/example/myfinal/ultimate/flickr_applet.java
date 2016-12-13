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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myfinal.ultimate.models.PhotoModel;
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

import static com.example.myfinal.ultimate.R.id.textView11;

public class flickr_applet extends AppCompatActivity {

    private Button rld;
    private ProgressDialog progressDialog;

    private ListView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr_applet);


        ///////////// Start Of OnCreate /////////////////


        //  List View Initialisation    //
        listview = (ListView)findViewById(R.id.imglist);



        //  Refresh Button Initialisation   //
        rld = (Button) findViewById(R.id.rld);


        // OnClick Initialisation //
        rld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new JSON().execute("https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=d98f34e2210534e37332a2bb0ab18887&format=json&extras=url_n&nojsoncallback=1");
            }
        });

                ///...............................................///


                /////....Progress Dialog Initialisation...../////////

                        progressDialog  = new ProgressDialog(this);
                        progressDialog.setIndeterminate(false);
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("LOADING..TAKE A DEEP BREATH!");


                ///...............................................///




                //////////////////////////////////////////////////////////////////////


                ////////////// UNIVERSAL IMAGE LOADER INITIALISATION ////////////////


                        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                                .cacheInMemory(true)
                                .cacheOnDisk(true)
                                .build();
                        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                                .defaultDisplayImageOptions(defaultOptions)
                                .build();
                        ImageLoader.getInstance().init(config); // Do it on Application start



                /////////////////////////////////////////////////////////////////////



                ///////////// End Of OnCreate /////////////////
    }


    public class JSON extends AsyncTask<String, String, List<PhotoModel>> {
        // Keep in Mind the AsyncTask Returns List of Items.
        // So Argument must be list.

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected List<PhotoModel> doInBackground(String... urls) {
            BufferedReader reader = null;
            HttpURLConnection connection = null;

            try {


                URL url = new URL(urls[0]);
                // Open Up HTTP URL Connection By Using Below Code //
                connection = (HttpURLConnection) url.openConnection();

                connection.connect();
                //~Open Up Input Stream So as to read the data on web~//
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                //~ End~//

                //Create A String to store the Read Input //
                String finalJSON = buffer.toString();



                JSONObject parentobject = new JSONObject(finalJSON);
                parentobject = parentobject.getJSONObject("photos");                //OBJECT IN FLICKR API
                JSONArray photo = parentobject.getJSONArray("photo");               //OBJECT ARRAY IN "PHOTOS"
                List<PhotoModel> photoList = new ArrayList<>();                     // Initialised List of PhotoModel.

                // Using GSON //
                Gson gson = new Gson();

                for(int i=0;i<photo.length();i++) {

                    JSONObject reqdObj = photo.getJSONObject(i);                    //reqdObj will access the photo array

                   // PhotoModel photoModel = new PhotoModel();                     //Without using GSON we have to create a new Object
                    PhotoModel photoModel = gson.fromJson(reqdObj.toString(),PhotoModel.class);

/*                  photoModel.setFarm(reqdObj.getInt("farm"));                     // We have to set every
                    photoModel.setSecret(reqdObj.getString("secret"));              // parameter individually.
                    photoModel.setOwner(reqdObj.getString("owner"));                // Which is easily done by
                    photoModel.setId(reqdObj.getString("id"));                      //  GSON.
                    photoModel.setServer(reqdObj.getString("server"));              //
                    photoModel.setTitle(reqdObj.getString("title"));                //

                    photoModel.setUrl_n(photoModel.getFarm(),photoModel.getServer(),photoModel.getId(),photoModel.getSecret());
*/
                    photoList.add(photoModel);                                       // Append everything to the List

                }

                return photoList;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
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


        protected void onPostExecute(List<PhotoModel> result) {
            super.onPostExecute(result);
            PhotoAdapter adapter = new PhotoAdapter(getApplicationContext(),R.layout.flickr_row,result);
            listview.setAdapter(adapter);
            progressDialog.dismiss();

        }
    }


    //////////////////////////////////////////////////////////////////////

        ////////// CUSTOM ADAPTER DESIGN  /////////////

        public class PhotoAdapter extends ArrayAdapter{

            public List<PhotoModel> photoModels;            // Create A List of Photomodel Objects
            int resource;
            private LayoutInflater inflater;

            public PhotoAdapter(Context context, int resource, List<PhotoModel> objects) {
                super(context, resource, objects);

                photoModels = objects;
                this.resource = resource;
                inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);


            }

            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                class ViewHolder{
                    private  ImageView imageView3;      // IMAGE TO BE SHOWN
                    private   TextView textView11;      // SETTING THE TITLE OF EACH PHOTO

                }
                ViewHolder holder = null;

                if(convertView==null)
                {
                    convertView =  inflater.inflate(resource,null);
                    holder = new ViewHolder();

                    holder.imageView3 = (ImageView)convertView.findViewById(R.id.imageView3);
                    holder.textView11 = (TextView)convertView.findViewById(textView11);

                    convertView.setTag(holder);


                }
                else{
                    holder = (ViewHolder)convertView.getTag();
                }
                final ProgressBar progressBar;


                progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar2) ;

                holder.textView11.setText("Titled:" + photoModels.get(position).getTitle());

                ImageLoader.getInstance().displayImage(photoModels.get(position).getUrl_n(), holder.imageView3, new ImageLoadingListener() {
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



                return convertView;


            }


        }



    /////////////////////////////////////////////////////////////////////////




}
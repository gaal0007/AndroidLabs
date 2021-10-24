package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForcast extends AppCompatActivity {

    public static final String ACTIVITY_NAME = "WEATHER_FORCAST_ACTIVITY";
    static ProgressBar progressBar = null;
    TextView curTempView = null;
    TextView minTempView = null;
    TextView maxTempView = null;
    TextView uvRatingView = null;
    ImageView weatherPicView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forcast);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        curTempView = findViewById(R.id.curTemp);
        minTempView = findViewById(R.id.minTemp);
        maxTempView = findViewById(R.id.maxTemp);
        uvRatingView = findViewById(R.id.uvRating);
        weatherPicView = findViewById(R.id.weatherPic);

        Log.e(ACTIVITY_NAME, "In function onCreate");

        ForecastQuery query = new ForecastQuery();
        ForecastQuery uvQuery = new ForecastQuery();
        //run a query against ottawa weather url
        query.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");
        //run a query against ottawa UV index url
        uvQuery.execute("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
    }

    class ForecastQuery extends AsyncTask<String, Integer, String>
    {
        String curTemp;
        String minTemp;
        String maxTemp;
        String uvRating;
        String weather;
        Bitmap weatherPic;

        @Override
        protected String doInBackground(String... args) {
            try {

                Log.e(ACTIVITY_NAME, "In function doInBackground");
                //create a URL object of what server to contact:
                URL url = new URL(args[0]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();



                //From part 3: slide 19
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response  , "UTF-8");


                //From part 3, slide 20
                String parameter = null;

                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                while(eventType != XmlPullParser.END_DOCUMENT)
                {

                    if(eventType == XmlPullParser.START_TAG)
                    {
                        //If you get here, then you are pointing at a start tag
                        if(xpp.getName().equals("temperature")) {
                            //If you get here, then you are pointing to a <temperature> start tag
                            curTemp = xpp.getAttributeValue(null, "value");
                            publishProgress(25);
                            Log.e(ACTIVITY_NAME, "got curTemp");
                            minTemp = xpp.getAttributeValue(null, "min");
                            publishProgress(50);
                            Log.e(ACTIVITY_NAME, "got minTemp");
                            maxTemp = xpp.getAttributeValue(null, "max");
                            publishProgress(75);
                            Log.e(ACTIVITY_NAME, "got maxTemp");

                        }
                        else if(xpp.getName().equals("weather"))
                        {
                            weather = xpp.getAttributeValue(null, "icon");

                            if(fileExistance(weather + ".png"))
                            {
                                FileInputStream fis = null;
                                try { fis = openFileInput(weather + ".png"); }
                                catch(FileNotFoundException e) { e.printStackTrace(); }
                                weatherPic = BitmapFactory.decodeStream(fis);
                            }
                            else
                            {
                                //download weather icon
                                String urlString = "http://openweathermap.org/img/w/" + weather + ".png";
                                URL imgURL = new URL(urlString);
                                HttpURLConnection connection = (HttpURLConnection) imgURL.openConnection();
                                connection.connect();
                                int responseCode = connection.getResponseCode();
                                if(responseCode == 200)
                                {
                                    weatherPic = BitmapFactory.decodeStream(connection.getInputStream());
                                }

                                //build weather bmp and save it to local memory
                                FileOutputStream outputStream = openFileOutput(weather + ".png", Context.MODE_PRIVATE);
                                weatherPic.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                outputStream.flush();
                                outputStream.close();
                            }


                        }
                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable
                }

                /*
                //JSON reading:
                //Build the entire string response:
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //result is the whole string

                JSONObject jObject = new JSONObject(result);
                float value = (float) jObject.getDouble("value");
                */

            }
            catch (Exception e)
            {
                Log.e("Error", e.getMessage());
            }

            return "Done";
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            Log.e(ACTIVITY_NAME, "In function onProgressUpdate");
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.e(ACTIVITY_NAME, "In function onPostExecute");
            curTempView.setText(curTemp);
            minTempView.setText(minTemp);
            maxTempView.setText(maxTemp);
            weatherPicView.setImageBitmap(weatherPic);
            progressBar.setVisibility(View.INVISIBLE);
        }

        public boolean fileExistance(String fname)
        {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }
    }
}
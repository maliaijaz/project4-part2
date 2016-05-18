package com.zotfeed.jsonparsertutorial;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import org.apache.http.*;

import com.loopj.android.http.*;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.cookie.Cookie;


public class MainActivity extends Activity {


    public void searchQuery(String title){
        Log.d("MSG: ", "Now executing search query for the following movie title" + title);
        // create asynchonronous HTTP client
        AsyncHttpClient client = new AsyncHttpClient();
        final PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        client.setCookieStore(myCookieStore);
        RequestParams params = new RequestParams();

        title = "Spi";
        params.put("title", title);

        // CHANGE THIS URL TO CHAITRA'S FABFLIX
        client.post("http://10.0.2.2:8080/SearchServlet/SearchServlet", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("MSG: ", "status code = " + statusCode);
                if(statusCode==404){
                    Toast.makeText(getApplicationContext(),"Requested resource not found", Toast.LENGTH_LONG).show();
                }
                else if (statusCode==500){
                    Toast.makeText(getApplicationContext(),"Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Unexpected error occurred!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {
                Log.d("MSG: ", "RESPONSE IS " + response);
                List<Cookie> cookies = myCookieStore.getCookies();
                for(Cookie temp: cookies){
                    Log.d("MSG: ", temp.getName() + temp.getValue());
                }
                TextView output = (TextView) findViewById(R.id.textView1);

                // remove the newline character later
                String strJson = "[{\"title\":\"Spider Man 2\"},{\"title\":\"Spider Man 3\"},{\"title\":\"Spider Man\"},{\"title\":\"Spiderman 2\"},{\"title\":\"Spider-Man 2\"}]\n";

                String data = "";
                try {
                    JSONArray jsonArray = new JSONArray(strJson);

                    for(int i=0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String name = jsonObject.optString("title").toString();
                        data += "Movie Title: " + name + "\n";
                    }
                    output.setText(data);
                } catch (JSONException e) {e.printStackTrace();}

            }

            public void onStart(){

            }

        });
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

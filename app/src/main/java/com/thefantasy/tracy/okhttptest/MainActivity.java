package com.thefantasy.tracy.okhttptest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.sql.DriverManager.println;

public class MainActivity extends AppCompatActivity {
public Button btn;
public TextView scr;
public OkHttpClient client;
public JSONArray jary;
public JSONObject jobj;
public String data;
/*public String url="https://raw.githubusercontent.com/tracygipson/TheFantasy/master/TestImages.JSON";*/
private String[] imagesurls = new String[100];
ArrayList<String> imgs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     btn = (Button)findViewById(R.id.btn);
     scr = (TextView) findViewById(R.id.txt);
        new JsonAsynTask().execute();
     btn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             new JsonAsynTask().execute();
         }
     });
    }
    public class JsonAsynTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://raw.githubusercontent.com/tracygipson/TheFantasy/master/TestImages.JSON")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        scr.setText("fail");
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                       data = response.body().string()/*.string()*/;
                       System.out.println(data);
                    }
                });
            return data;
        }

        @Override
        protected void onPostExecute(String result){
                try {

                    jobj = new JSONObject(result);
                    jary = jobj.getJSONArray("tracy");
                    System.out.println(jary);
                    for (int i =0;i<jary.length();i++){
                        imagesurls[i] = jary.getString(i);
                        scr.setText(imagesurls[i]);
                        System.out.println(imagesurls[i]);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
     }
}


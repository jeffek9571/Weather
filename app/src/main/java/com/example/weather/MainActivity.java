package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    String result,total;
    String startTime,endTime,parameterNameUnit;
    ArrayList<Post> mpost;
    Adapter ad;
    File file,appfile,file1;
    int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1,t=0;
    StringBuilder sb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sb= new StringBuilder();
        okhttp();

        rv=findViewById(R.id.rv);
        mpost=new ArrayList<>();
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        checkPermission();
        init();
        readData();

        if(t >0){
            Toast.makeText(this,"歡迎回來",Toast.LENGTH_SHORT).show();
        }else{
            t++;
        }
        saveData();

    }

    public void Json(){

        try{

            sb.append(result);
            JSONObject jsonObject=new JSONObject(sb.toString());
            Log.d("Post", "onCreate: "+jsonObject.getString("cwbopendata"));
            JSONObject jsonObject1=new JSONObject(jsonObject.getString("cwbopendata"));
            Log.d("Post1", "onCreate: "+jsonObject1.getString("dataset"));
            JSONObject jsonObject2=new JSONObject(jsonObject1.getString("dataset"));
            Log.d("Post2", "onCreate: "+jsonObject2.getString("location"));
            JSONArray jsonArray=jsonObject2.getJSONArray("location");

            Log.d("Post3", "onCreate: "+jsonArray.getJSONObject(0).getString("locationName"));
//            Log.d("Post3", "onCreate: "+jsonArray.getJSONObject(0).getJSONArray("weatherElement"));

            for(int i=0;i<jsonArray.length();i++){

                if(jsonArray.getJSONObject(i).getString("locationName").equals("臺北市")){
                    JSONArray jsonArray1=jsonArray.getJSONObject(i).getJSONArray("weatherElement");
                    for(int j=0;j<jsonArray1.length();j++){
                        if(jsonArray1.getJSONObject(j).getString("elementName").equals("MinT")){
                            Log.d("Post4", "onCreate: "+jsonArray1.getJSONObject(j).getJSONArray("time"));
                            JSONArray jsonArray2=jsonArray1.getJSONObject(j).getJSONArray("time");
                            for(int k=0;k<jsonArray2.length();k++){


                                startTime=jsonArray2.getJSONObject(k).getString("startTime").substring(0,10)+" "+jsonArray2.getJSONObject(k).getString("startTime").substring(11,19);
                                endTime=jsonArray2.getJSONObject(k).getString("endTime").substring(0,10)+" "+jsonArray2.getJSONObject(k).getString("endTime").substring(11,19);
                                JSONObject jsonObject3=jsonArray2.getJSONObject(k).getJSONObject("parameter");
                                Log.d("Post5", "onCreate: "+jsonObject3.toString());
                                parameterNameUnit=jsonObject3.getString("parameterName")+""+jsonObject3.getString("parameterUnit");
                                total=startTime+"\n"+endTime+"\n"+parameterNameUnit;
                                Log.d("Post6", "onCreate: "+total+jsonArray2.length());
//                                        mpost.add(new Post(total));
                                mpost.add(k,new Post(total));

                                Log.d("Post7", "run: "+mpost);
                            }
                            ad = new Adapter(MainActivity.this,mpost);
                            rv.setAdapter(ad);
                        }
                    }


                }
            }


        }catch (Exception e){
            Log.d("error", "onCreate: "+e.toString());
        }
//            }
//        });
    }



    @Override
    protected void onDestroy() {
        saveData();
        super.onDestroy();
    }

    private void init(){

        file = Environment.getExternalStorageDirectory();
//        Log.v("path", file.getAbsolutePath());
//        sdroot=new File(file,"XX.txt");
        appfile = new File(file,"Android/data/"+getPackageName()+"/");
        if(!appfile.exists()){

            if(appfile.mkdir()){
                Log.v("mkdir","mkdir OK");
            }
            else{
                Log.v("mkdir","mkdir Fail");
            }
        }

            file1=new File(appfile,"Welcome.txt");


    }
    private void readData(){
        if(new File(appfile,"Welcome.txt").exists() ){
            Log.d("Pagetxt", "ok");
            try{
                BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
                String welcome;

                StringBuffer stringBuffer =new StringBuffer();
                if((welcome = bufferedReader.readLine())!=null){
                    stringBuffer.append(welcome);
                }

                bufferedReader.close();


                t=Integer.parseInt(stringBuffer.toString());


//                Log.d("pagenum", "onCreate: "+pageNum+","+limitNum);


            }catch (Exception e){
                Log.d("errorx", e.toString());
            }

        }
    }

    private void saveData(){
        try {
            FileOutputStream fileOutputStream =new FileOutputStream(file1);
            fileOutputStream.write(String.valueOf(t).getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED &
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);


        }
        else{

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            init();
        }else{
            finish();
        }

    }

    public void okhttp() {


        String url1 = "https://opendata.cwb.gov.tw/fileapi/v1/opendataapi/F-C0032-001?Authorization=CWB-30C52969-DC81-4580-968D-2D5211F1E73B&format=JSON";
        Log.d("Post", "url: " + url1);

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(url1)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {


                result = response.body().string();
//                posts = new Gson().fromJson(result, Post[].class);
                Log.d("result", "onResponse: "+sb.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Json();
                    }
                });


            }
        });
    }
}

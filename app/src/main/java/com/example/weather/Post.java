package com.example.weather;

import android.util.Log;

public class Post {
    String total;
//    String startTime,parameterNameUnit,endTime;

//    public String getStartTime() {
//        return startTime;
//    }
//
//    public String getParameterNameUnit() {
//        return parameterNameUnit;
//    }
//
//    public String getEndTime() {
//        return endTime;
//    }

    public String getTotal() {
        return total;
    }

    public Post(String all){
//        this.startTime=start;
//        this.endTime=end;
//        this.parameterNameUnit=nameunit;
        this.total=all;
        Log.d("find", "1");

    }



}

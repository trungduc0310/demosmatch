package com.example.phamtrungduc.smatch.retrofit2;

public class APIUntils {
    public static final String base_url="http://smatch.000webhostapp.com/smatch/";
    public static DataClient getData(){
        return RetrofitClient.getClient(base_url).create(DataClient.class);
    }
}

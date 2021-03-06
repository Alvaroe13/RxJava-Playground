package com.alvaro.rxjavaplayground.repo;


import com.alvaro.rxjavaplayground.datasource.ServiceGenerator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class Repository {

    private static Repository instance;

    public static Repository getInstance(){
        if(instance == null){
            instance = new Repository();
        }
        return instance;
    }


    public LiveData<ResponseBody> makeReactiveQuery(){
        return LiveDataReactiveStreams.fromPublisher(
                ServiceGenerator.getRequestApi()
                .makeObservableQuery()
                .subscribeOn(Schedulers.io())
        );
    }
}
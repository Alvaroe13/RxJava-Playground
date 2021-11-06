package com.alvaro.rxjavaplayground.ui;

import android.util.Log;

import com.alvaro.rxjavaplayground.datasource.DummyDataSource;
import com.alvaro.rxjavaplayground.model.Task;

import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModelTag";


    public Observable<Task> taskObservable;


    public void execute(){
        Log.d(TAG, "MainViewModel: triggered execute");
        taskObservable = Observable
                .fromIterable(DummyDataSource.Companion.createList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}

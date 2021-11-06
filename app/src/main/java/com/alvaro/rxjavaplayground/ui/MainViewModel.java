package com.alvaro.rxjavaplayground.ui;

import android.util.Log;

import com.alvaro.rxjavaplayground.model.Task;

import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModelTag";


    public Observable<Task> taskObservable;

    public void execute(){
        Log.d(TAG, "MainViewModel: triggered execute");
        taskObservable = Observable
                .create((ObservableOnSubscribe<Task>) emitter -> {
                    if (!emitter.isDisposed()){
                        emitter.onNext(
                                new Task("Home work description" , false , 3)
                        );
                        emitter.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}

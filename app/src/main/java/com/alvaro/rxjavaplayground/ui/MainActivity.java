package com.alvaro.rxjavaplayground.ui;

import android.os.Bundle;
import android.util.Log;

import com.alvaro.rxjavaplayground.R;
import com.alvaro.rxjavaplayground.model.Task;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import kotlin.Unit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityTag";


    private MainViewModel viewModel;
    private final CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewModel();
        subscribeObserver();
        RxBinding();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.execute();
    }


    //response from DummyLocalData
    private void subscribeObserver(){

        viewModel.taskObservable.subscribe(new Observer<List<Task>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable.add(d);
                printer("onSubscribe called");
            }

            @Override
            public void onNext(@NonNull List<Task> tasks) { //observable gate
                printer("onNext called= " + tasks);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                printer("onError called");
            }

            @Override
            public void onComplete() { // task completed sending observables
                printer("onComplete called");
            }
        });


    }

    //  RxView.clicks turns click event into RxObservable
    private void RxBinding(){
        RxView.clicks(findViewById(R.id.btn))
                .map(unit -> 1)
                .buffer(4 , TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        printer("onSubscribe called");
                    }

                    @Override
                    public void onNext(@NonNull List<Integer> integers) {
                        printer("onNext called= " + integers);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        printer("onComplete called= ");
                    }
                });
    }

    //----------------------------- response from server ---------------------------//

    //response from server
    /*private void subscribeObserver() {
        viewModel.makeQuery().observe(this, responseBody -> {
            printer("this is a live data response!");
            try {
                printer("responseBOdy= " + responseBody.string() );
            }catch (Exception e){
                printer("Error= " + e.getMessage());
            }
        });
    }*/

    void printer(String text) {
        Log.d(TAG, "printer: " + text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
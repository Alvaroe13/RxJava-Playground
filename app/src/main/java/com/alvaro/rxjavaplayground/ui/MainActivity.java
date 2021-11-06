package com.alvaro.rxjavaplayground.ui;

import android.os.Bundle;
import android.util.Log;

import com.alvaro.rxjavaplayground.R;
import com.alvaro.rxjavaplayground.model.Task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

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
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.execute();
    }


    //response from DummyLocalData
    private void subscribeObserver(){
        viewModel.taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable.add(d);
                printer("onSubscribe called");
            }

            @Override
            public void onNext(@NonNull Task task) { //observable gate
                printer("onNext called= " + task);
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
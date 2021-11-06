package com.alvaro.rxjavaplayground.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import android.os.Bundle;
import android.util.Log;

import com.alvaro.rxjavaplayground.R;
import com.alvaro.rxjavaplayground.model.Task; 

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

    private void subscribeObserver(){
        viewModel.taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable.add(d);
                printer("onSubscribe called");
            }

            @Override
            public void onNext(@NonNull Task task) {
                printer("onNext called= " + task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                printer("onError called");
            }

            @Override
            public void onComplete() {
                printer("onComplete called");
            }
        });
    }

    void printer(String text){
        Log.d(TAG, "printer: " + text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
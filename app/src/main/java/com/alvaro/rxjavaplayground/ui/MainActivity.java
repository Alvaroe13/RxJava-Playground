package com.alvaro.rxjavaplayground.ui;

import android.os.Bundle;
import android.util.Log;

import com.alvaro.rxjavaplayground.R;
import com.alvaro.rxjavaplayground.model.Task;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityTag";


    private MainViewModel viewModel;
    private final CompositeDisposable disposable = new CompositeDisposable();

    long timeSinceLastRequest = System.currentTimeMillis();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewModel();
        subscribeObserver();
        //RxBinding();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.execute();
    }


    //response from DummyLocalData
   /* private void subscribeObserver(){

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


    }*/

    // RxView.clicks turns click event into RxObservable and in this example, observable
    // are emitted every 4 seconds
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

    //----------------------------- 2nd observer server ---------------------------//

    // "debounce" operator is emitting observables after a specified time delay, in this case, 1
    // second after the user stop inserting a text in the search bar
    /*private void subscribeObserver(){

        // create the Observable
        Observable<String> observableQueryText = Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(final ObservableEmitter<String> emitter) throws Exception {

                        // Listen for text input into the SearchView
                        SearchView searchView = findViewById(R.id.search_view);
                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(final String newText) {
                                if(!emitter.isDisposed()){
                                    emitter.onNext(newText); // Pass the query to the emitter
                                }
                                return false;
                            }
                        });
                    }
                })
                .debounce(1000, TimeUnit.MILLISECONDS) // Apply Debounce() operator to limit requests
                .subscribeOn(Schedulers.io());

        // Subscribe an Observer
        observableQueryText.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable.add(d);
            }
            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: time  since last request: " + (System.currentTimeMillis() - timeSinceLastRequest));
                Log.d(TAG, "onNext: search query: " + s);

                timeSinceLastRequest= System.currentTimeMillis();

                // method for sending a request to the server
                sendRequestToServer(s);
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onComplete() {
            }
        });*/



    // "throttleFirst" operator works as a solution to prevent event spamming, in this case
    // the btn will create an Observable after 4 seconds the last Observable was created
    private void subscribeObserver(){
        RxView.clicks(findViewById(R.id.btn))
                .throttleFirst(4, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Unit>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        printer("onSubscribe called= ");
                    }

                    @Override
                    public void onNext(@NonNull Unit unit) {
                        printer("onNext called= ");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        printer("onError called= ");
                    }

                    @Override
                    public void onComplete() {
                        printer("onComplete called= ");
                    }
                });


    }

    // Fake method for sending a request to the server
    private void sendRequestToServer(String query){
        // do nothing
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
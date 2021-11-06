package com.alvaro.rxjavaplayground.ui;

import android.util.Log;

import com.alvaro.rxjavaplayground.datasource.DummyDataSource;
import com.alvaro.rxjavaplayground.model.Task;
import com.alvaro.rxjavaplayground.repo.Repository;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * In this class we placed the observables and in MainActivity the observer,
 * when commenting one out make sure to replace the Object type the Observer will be expecting
 * in the MainActivity
 * (Replace it both here in "taskObservable" object and in MainActivity)
 */

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModelTag";


    public Observable<Task> taskObservable;
    private Repository repository;

    public MainViewModel(){
        repository = Repository.getInstance();
    }

   /*
   // in this case create one single operator
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
    }*/

    /*
    //create just one observable
    public void execute(){
        Log.d(TAG, "MainViewModel: triggered execute");
        taskObservable = Observable
                .just(new Task("Home work description 2" , false , 3))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }*/

    /*
    //emits single observable every 1 sec until 5
    public void execute(){
        Log.d(TAG, "MainViewModel: triggered execute");
        taskObservable = Observable
                .interval( 1 , TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .takeWhile(aLong -> aLong <= 5) // while "aLong" is less or equal than 5 emmit it to observer
                .observeOn(AndroidSchedulers.mainThread());
    }*/

    /*
    // emits single observable object after 3 secs
    public void execute(){
        Log.d(TAG, "MainViewModel: triggered execute");
        taskObservable = Observable
                .timer( 3 , TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }*/

    /*
    // create observables from an array, it emits an observable per item in the array
    public void execute(){
        Log.d(TAG, "MainViewModel: triggered execute");
        taskObservable = Observable
                .fromArray( DummyDataSource.Companion.getArray())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    */

    /*
    // create observables from a list, it emits an observable per item in the list
    public void execute(){
        Log.d(TAG, "MainViewModel: triggered execute");
        taskObservable = Observable
                .fromIterable( DummyDataSource.Companion.getList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }*/

    // create observable/s when a callback from database it's triggered
    public void execute(){
        Log.d(TAG, "MainViewModel: triggered execute");
        taskObservable = Observable
                .fromCallable(() -> { // it doesn't pass any value as param through callback
                    return DummyDataSource.Companion.getList().get(0); //pretends it comes from Room
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public LiveData<ResponseBody> makeQuery(){
        return repository.makeReactiveQuery();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }
}

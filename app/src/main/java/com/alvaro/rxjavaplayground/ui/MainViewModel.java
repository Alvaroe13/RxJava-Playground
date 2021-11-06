package com.alvaro.rxjavaplayground.ui;

import android.util.Log;

import com.alvaro.rxjavaplayground.datasource.DummyDataSource;
import com.alvaro.rxjavaplayground.model.Task;
import com.alvaro.rxjavaplayground.repo.Repository;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
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


    public Observable<List<Task>> taskObservable;
    private final Repository repository;

    public MainViewModel(){
        repository = Repository.getInstance();
    }

    // --------------------------------- creation operator ----------------------------------//

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

    /*
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
    */

    // --------------------------------- filter operator ----------------------------------//

    // "filter" create observables when the predicate is met. In Other words operator "filter" filters out
    // the task that doesn't meet the predicate and are not sent out to the UI
    // filter operation is done in a background thread
    /*public void execute(){
        Log.d(TAG, "MainViewModel: triggered execute");
        taskObservable = Observable
                .fromIterable(DummyDataSource.Companion.getList())
                .filter(task -> task.getDescription().equals("Walk the dog")) // sends if true, if false is not sent
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    } */

    // "Distinct" filters out object when the predicate is met, in the example is "getDescription()"
    // method returns a value that already exists in the data set, it gets filter out and is not
    // sent out to MainActivity as an Observable
    /*public void execute(){
        Log.d(TAG, "MainViewModel: triggered execute");
        taskObservable = Observable
                .fromIterable(DummyDataSource.Companion.getList())
                .distinct((Function<Task, String>) task -> task.getDescription()  )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }*/

    // "take" takes a certain number of object from a data set, the number is specified through a param
    /*public void execute(){
        Log.d(TAG, "MainViewModel: triggered execute");
        taskObservable = Observable
                .fromIterable(DummyDataSource.Companion.getList())
                .take(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }*/

    // "takeWhile" tells RxJava to keep emiting object until predicate is met
    // In this case, it will emit observables until "task.isComplete()" returns true, then it stops
    /*public void execute(){
        Log.d(TAG, "MainViewModel: triggered execute");
        taskObservable = Observable
                .fromIterable(DummyDataSource.Companion.getList())
                .takeWhile(task -> task.isComplete()) // Predicate
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }*/

    // --------------------------------- transformation operator ----------------------------------//


    // "map"  operator does exactly that, a map operation. Aka, it retrieves an object type based
    // on the object pass as param. In this example it takes a Task object and it's returning a String
    /*public void execute(){
        Log.d(TAG, "MainViewModel: triggered execute");
        taskObservable = Observable
                .fromIterable(DummyDataSource.Companion.getList())
                .map(task -> task.getDescription() )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }*/

    // "buffer" operator sends out the observables in bundles or list at the time of emiting
    // to the MainActivity. In this example the expected value is Observable<List<Task>>>
    //and each bundle is of size 2
    public void execute(){
        Log.d(TAG, "MainViewModel: triggered execute");
        taskObservable = Observable
                .fromIterable(DummyDataSource.Companion.getList())
                .buffer(2) // -> size of bundle to send
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    //----------------------------- response from server ---------------------------//

    //response from server
    /*public LiveData<ResponseBody> makeQuery(){
        return repository.makeReactiveQuery();
    }*/


    @Override
    protected void onCleared() {
        super.onCleared();
    }
}

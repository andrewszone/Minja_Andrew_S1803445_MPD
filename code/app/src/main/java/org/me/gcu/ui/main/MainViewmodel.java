//S1803445
package org.me.gcu.ui.main;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import org.me.gcu.local.AppDao;
import org.me.gcu.local.AppDatabase;
import org.me.gcu.models.Channel;
import org.me.gcu.models.Item;
import org.me.gcu.remote.ApiEndpoints;
import org.me.gcu.remote.ApiService;
import org.me.gcu.utils.XmlParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewmodel extends ViewModel {

    MainViewmodel(Context context){
        AppDatabase database = Room.databaseBuilder(context, AppDatabase.class, "earthquakes_db")
                .build();
        this.dao = database.getDao();
    }

    private final AppDao dao;
    private final ApiEndpoints api = new ApiService().api;

    // to hold all our disposables....
    private final CompositeDisposable composite = new CompositeDisposable();


    private final MutableLiveData<List<Item>> _data = new MutableLiveData<>(); // for local manipulation
    public final LiveData<List<Item>> data = _data; // will be observed in the UI

    private final MutableLiveData<String> _error = new MutableLiveData<>(); //for local manipulation
    final LiveData<String> error = _error; // will be observed in the UI

    // Checks for the different status values in each of the items
    public void setItems(ArrayList<Item> items){
        if (!items.isEmpty()){
            Item north = Collections.max(items, Comparator.comparing(Item::getLat));
            Item south = Collections.min(items, Comparator.comparing(Item::getLat));
            Item east =  Collections.max(items, Comparator.comparing(Item::getLng));
            Item west = Collections.min(items, Comparator.comparing(Item::getLng));
            Item largest = Collections.max(items, Comparator.comparing(Item::getMagnitude));
            Item deepest = Collections.max(items, Comparator.comparing(Item::getDepthValue));
            Item shallowest = Collections.min(items, Comparator.comparing(Item::getDepthValue));

            for (Item item: items){
                if (item.equals(largest)) item.setStatus("largest");
                else if (item.equals(deepest)) item.setStatus("deepest");
                else if (item.equals(shallowest)) item.setStatus("shallowest");
                else if (north.equals(item)) item.setStatus("Northerly");
                else if (south.equals(item)) item.setStatus("Southerly");
                else if (east.equals(item)) item.setStatus("Easterly");
                else if (west.equals(item)) item.setStatus("Westerly");
                else item.setStatus("");
            }
        }

        _data.postValue(items);
    }

    /**
     * Get data from the network/ make a network request
     * */
    public void updateData(){
        composite.add(
            api.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    apiResponse -> { parseXml(apiResponse.byteStream()); },
                    e -> { _error.postValue(e.getMessage());}
                )
        );
    }

    /**
     * Parse the xml from network
     * */
    private void parseXml(InputStream stream){
        XmlParser parser = new XmlParser();
        composite.add(
            Observable.just(parser.parse(stream)) // create an observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    this::updateLocalData, // updateLocalData(data)
                    e -> {_error.postValue(e.getMessage());
                })
        );
    }

    /**
     * This function retrieves the data from the local database
     * */
    public void getLocalData(){
        composite.add(
            dao.getLocalData()
                .subscribeOn(Schedulers.computation()) // defines on which thread pool to run
                .observeOn(AndroidSchedulers.mainThread()) // defines on which thread to receive our data
                .subscribe(
                    data -> {_data.postValue(data.getItems()); },
                    e -> {_error.postValue(e.getMessage()); }
                )
        );
    }


    /**
     * Write the local database
     * */
    private void updateLocalData(Channel channel){
        composite.add(
            dao.updateLocalData(channel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(e -> {_error.postValue(e.getMessage());})
                .subscribe()
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        composite.dispose();
    }
}



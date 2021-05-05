package org.me.gcu.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.me.gcu.models.Channel;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface AppDao {

    @Query("SELECT * FROM channel")
    Flowable<Channel> getLocalData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable updateLocalData(Channel channel);
}

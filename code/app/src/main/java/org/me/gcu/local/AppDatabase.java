package org.me.gcu.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import org.me.gcu.models.Channel;

@TypeConverters(Converters.class)
@Database(entities = {Channel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AppDao getDao();
}

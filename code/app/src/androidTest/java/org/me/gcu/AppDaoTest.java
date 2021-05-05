//S1803445
package org.me.gcu;

import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.me.gcu.local.AppDao;
import org.me.gcu.local.AppDatabase;
import org.me.gcu.models.Channel;
import org.me.gcu.utils.XmlParser;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppDaoTest {

    private static AppDao dao;
    private static AppDatabase db;
    private static Channel channel;

    @BeforeClass
    public static void setup() throws IOException {
        // Given th context, get the database instance and dao
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        dao = db.getDao();

        // When sample data is provided
        InputStream in = context.getResources().getAssets().open("data.xml");
        XmlParser parser = new XmlParser();
        channel = parser.parse(in);
    }


    @AfterClass
    public static void tearDown(){
        db.clearAllTables();
        db.close();
    }


    @Test
    public void test_1_should_save_in_database(){
        dao.updateLocalData(channel).test()
                .assertComplete()
                .dispose();
    }

    @Test
    public void test_2_should_retrieve_data_from_database(){
        Channel data = dao.getLocalData().blockingFirst();
        assertNotNull(data);
    }
}
package vanyahuaman.com.br_challenge_app;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {StoreObject.class}, version = 1)
public abstract class StoreRoomDatabaseJava extends RoomDatabase {

    public abstract StoreDao storeDao();

    private static volatile StoreRoomDatabaseJava INSTANCE;

    static StoreRoomDatabaseJava getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StoreRoomDatabaseJava.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            StoreRoomDatabaseJava.class, "store_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final StoreDao mDao;

        PopulateDbAsync(StoreRoomDatabaseJava db) {
            mDao = db.storeDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            StoreObject store = new StoreObject(22,"NA","NA","NA","NA","NA","NA","NA","NA","NA","NA");
            mDao.insert(store);
            store = new StoreObject(33,"NA","NA","NA","NA","NA","NA","NA","NA","NA","NA");
            mDao.insert(store);
            return null;
        }
    }
}
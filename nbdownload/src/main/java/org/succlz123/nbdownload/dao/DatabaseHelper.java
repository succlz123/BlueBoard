package org.succlz123.nbdownload.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.succlz123.nbdownload.NBDownloadRequest;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by succlz123 on 15/9/2.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "nb-download.db";
    private static final int DATABASE_VERSION = 1;
    private Map<String, Dao> mDaos = new HashMap<String, Dao>();

    private static DatabaseHelper sInstance;

    public static synchronized DatabaseHelper getHelper(Context context) {
        if (sInstance == null) {
            synchronized (DatabaseHelper.class) {
                if (sInstance == null)
                    sInstance = new DatabaseHelper(context);
            }
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, NBDownloadRequest.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            //删除旧表
            TableUtils.dropTable(connectionSource, NBDownloadRequest.class, true);
            //建新表
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public Dao<NBDownloadRequest, Integer> getXxDao() throws SQLException {
//        if (xxDao == null) {
//            xxDao = getDao(xx.class);
//        }
//        return xxDao;
//    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (mDaos.containsKey(className)) {
            dao = mDaos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            mDaos.put(className, dao);
        }
        return dao;
    }

    @Override
    public void close() {
        super.close();
        for (String key : mDaos.keySet()) {
            Dao dao = mDaos.get(key);
            dao = null;
        }
    }
}

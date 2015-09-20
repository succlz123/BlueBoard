package org.succlz123.nbdownload.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import org.succlz123.nbdownload.NBDownloadRequest;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by succlz123 on 15/9/14.
 */
public class RequestDao {
    private Context mContext;
    private static RequestDao sInstance;
    private DatabaseHelper mHelper;
    private Dao<NBDownloadRequest, Integer> mRequestDao;

    private RequestDao(Context context) {
        this.mContext = context;

        if (mContext == null) {
            return;
        }

        try {
            mRequestDao = DatabaseHelper.getHelper(mContext).getDao(NBDownloadRequest.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static RequestDao newInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RequestDao(context);
        }
        return sInstance;
    }

    public int add(NBDownloadRequest request) {
        try {
            return mRequestDao.create(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void delete(NBDownloadRequest request) {
        try {
            mRequestDao.delete(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<NBDownloadRequest> query(String key, String value) {
        try {
            return mRequestDao.queryBuilder().where().eq(key, value).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NBDownloadRequest> queryForAll() {
        try {
            return mRequestDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(NBDownloadRequest request) {
        try {
            mRequestDao.update(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

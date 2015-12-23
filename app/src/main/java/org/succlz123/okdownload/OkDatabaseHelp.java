package org.succlz123.okdownload;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by succlz123 on 15/11/19.
 */
public class OkDatabaseHelp extends SQLiteOpenHelper {
    private static final String TAG = "OkDatabaseHelp";

    private static final int VERSION = 1;
    private static final String DB_NAME = "okdownload.db";
    private static final String TABLE_NAME = "downloadinfo";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_SIGN = "sign";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_FILEPATH = "filePath";
    private static final String COLUMN_STARTTIME = "startTime";
    private static final String COLUMN_FINISHTIME = "finishTime";
    private static final String COLUMN_FILESIZE = "fileSize";
    private static final String COLUMN_STATUS = "status";

    private static final String DB_INFO = "create table " + TABLE_NAME + " ("
//            + COLUMN_ID + " INTEGER primary key autoincrement,"
            + COLUMN_ID + " INTEGER primary key,"
            + COLUMN_URL + " TEXT,"
            + COLUMN_SIGN + " TEXT,"
            + COLUMN_TITLE + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_FILEPATH + " TEXT,"
            + COLUMN_STARTTIME + " INTEGER,"
            + COLUMN_FINISHTIME + " INTEGER,"
            + COLUMN_FILESIZE + " INTEGER,"
            + COLUMN_STATUS + " INTEGER"
            + ")";

    private static Context sContext;

    public static OkDatabaseHelp getInstance(Context context) {
        if (context == null) {
            return null;
        }

        if (sContext == null) {
            sContext = context;
        }

        return HelpHolder.INSTANCE;
    }

    private static class HelpHolder {
        private static final OkDatabaseHelp INSTANCE = new OkDatabaseHelp(sContext, DB_NAME, null, VERSION);
    }

    private OkDatabaseHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long execInsert(OkDownloadRequest okDownloadRequest) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_URL, okDownloadRequest.getUrl());
        cv.put(COLUMN_TITLE, okDownloadRequest.getTitle());
        cv.put(COLUMN_SIGN, okDownloadRequest.getSign());
        cv.put(COLUMN_DESCRIPTION, okDownloadRequest.getDescription());
        cv.put(COLUMN_FILEPATH, okDownloadRequest.getFilePath());
        cv.put(COLUMN_STARTTIME, okDownloadRequest.getStartTime());
        cv.put(COLUMN_FINISHTIME, okDownloadRequest.getFinishTime());
        cv.put(COLUMN_FILESIZE, okDownloadRequest.getFileSize());
        cv.put(COLUMN_STATUS, okDownloadRequest.getStatus());

        long code = db.insert(TABLE_NAME, null, cv);

        db.close();

        Log.w(TAG, "execInsert " + code + "/" + okDownloadRequest.getSign());
        return code;
    }

    public int execUpdate(OkDownloadRequest okDownloadRequest) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_URL, okDownloadRequest.getUrl());
        cv.put(COLUMN_TITLE, okDownloadRequest.getTitle());
        cv.put(COLUMN_SIGN, okDownloadRequest.getSign());
        cv.put(COLUMN_DESCRIPTION, okDownloadRequest.getDescription());
        cv.put(COLUMN_FILEPATH, okDownloadRequest.getFilePath());
        cv.put(COLUMN_STARTTIME, okDownloadRequest.getStartTime());
        cv.put(COLUMN_FINISHTIME, okDownloadRequest.getFinishTime());
        cv.put(COLUMN_FILESIZE, okDownloadRequest.getFileSize());
        cv.put(COLUMN_STATUS, okDownloadRequest.getStatus());

        int code = db.update(TABLE_NAME, cv, "sign = ?", new String[]{okDownloadRequest.getSign()});

        db.close();
        Log.w(TAG, "execUpdate " + code);

        return code;
    }

    public int execUpdateDownloadStatus(OkDownloadRequest okDownloadRequest) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_STATUS, okDownloadRequest.getStatus());

        int code = db.update(TABLE_NAME, cv, "url = ?", new String[]{okDownloadRequest.getUrl()});

        db.close();
        Log.w(TAG, "execUpdateDownloadStatus " + code);

        return code;
    }

    public ArrayList<OkDownloadRequest> execQuery(String key, String value) {
        SQLiteDatabase db = getWritableDatabase();

        ArrayList<OkDownloadRequest> requestList = new ArrayList<>();

        String sql = "select * from " + TABLE_NAME + " where " + key + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{value});

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    OkDownloadRequest okDownloadRequest = new OkDownloadRequest.Builder().build();

                    okDownloadRequest.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                    okDownloadRequest.setUrl(cursor.getString(cursor.getColumnIndex(COLUMN_URL)));
                    okDownloadRequest.setSign(cursor.getString(cursor.getColumnIndex(COLUMN_SIGN)));
                    okDownloadRequest.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                    okDownloadRequest.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                    okDownloadRequest.setFilePath(cursor.getString(cursor.getColumnIndex(COLUMN_FILEPATH)));
                    okDownloadRequest.setStartTime(cursor.getLong(cursor.getColumnIndex(COLUMN_STARTTIME)));
                    okDownloadRequest.setFinishTime(cursor.getLong(cursor.getColumnIndex(COLUMN_FINISHTIME)));
                    okDownloadRequest.setFileSize(cursor.getLong(cursor.getColumnIndex(COLUMN_FILESIZE)));
                    okDownloadRequest.setStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)));

                    requestList.add(okDownloadRequest);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
//        db.close();
        Log.w(TAG, "execQuery");

        return requestList;
    }

    public ArrayList<OkDownloadRequest> execQueryAll() {
        SQLiteDatabase db = getWritableDatabase();

        ArrayList<OkDownloadRequest> requestList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    OkDownloadRequest okDownloadRequest = new OkDownloadRequest.Builder().build();

                    okDownloadRequest.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                    okDownloadRequest.setUrl(cursor.getString(cursor.getColumnIndex(COLUMN_URL)));
                    okDownloadRequest.setSign(cursor.getString(cursor.getColumnIndex(COLUMN_SIGN)));
                    okDownloadRequest.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                    okDownloadRequest.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                    okDownloadRequest.setFilePath(cursor.getString(cursor.getColumnIndex(COLUMN_FILEPATH)));
                    okDownloadRequest.setStartTime(cursor.getLong(cursor.getColumnIndex(COLUMN_STARTTIME)));
                    okDownloadRequest.setFinishTime(cursor.getLong(cursor.getColumnIndex(COLUMN_FINISHTIME)));
                    okDownloadRequest.setFileSize(cursor.getLong(cursor.getColumnIndex(COLUMN_FILESIZE)));
                    okDownloadRequest.setStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)));

                    requestList.add(okDownloadRequest);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        Log.w(TAG, "execQueryAll");

        return requestList;
    }

    public int execDelete(String key, String value) {
        SQLiteDatabase db = getWritableDatabase();

        int code = db.delete(TABLE_NAME, key + " = ?", new String[]{value});
        Log.w(TAG, "execDelete " + code);

        return code;
    }
}
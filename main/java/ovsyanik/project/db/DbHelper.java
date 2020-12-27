package ovsyanik.project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ovsyanik.project.data.News;
import ovsyanik.project.data.TypeUser;
import ovsyanik.project.data.User;


public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "project.db";
    private static DbHelper mInstance = null;

    public static DbHelper getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new DbHelper(context);
        }
        return mInstance;
    }

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DbContract.UserTable.TABLE_NAME + "("
                + DbContract.UserTable.ID + " integer primary key autoincrement, "
                + DbContract.UserTable.NAME + " text, "
                + DbContract.UserTable.IMAGE + " text, "
                + DbContract.UserTable.MAIL + " text, "
                + DbContract.UserTable.PASSWORD + " text, "
                + DbContract.UserTable.TYPE + " text, "
                + DbContract.UserTable.IN_BLACK_LIST + " integer );");
        db.execSQL("create table " + DbContract.NewsTable.TABLE_NAME + "("
                + DbContract.NewsTable.ID + " integer primary key autoincrement, "
                + DbContract.NewsTable.TITLE + " text, "
                + DbContract.NewsTable.IMAGE + " text, "
                + DbContract.NewsTable.DESCRIPTION + " text, "
                + DbContract.NewsTable.DATE_START + " text, "
                + DbContract.NewsTable.DATE_END + " text );");
        db.execSQL("create table " + DbContract.AnnouncementTable.TABLE_NAME + "("
                + DbContract.AnnouncementTable.ID + " integer primary key autoincrement, "
                + DbContract.AnnouncementTable.TITLE + " text, "
                + DbContract.AnnouncementTable.IMAGE + " text, "
                + DbContract.AnnouncementTable.DESCRIPTION + " text, "
                + DbContract.AnnouncementTable.DATE_START + " text, "
                + DbContract.AnnouncementTable.DATE_END + " text );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + DbContract.UserTable.TABLE_NAME);
        db.execSQL("drop table " + DbContract.NewsTable.TABLE_NAME);
        db.execSQL("drop table " + DbContract.AnnouncementTable.TABLE_NAME);

        this.onCreate(db);
    }

    public void insertUser(User user) {
        SQLiteDatabase db = DbHelper.this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DbContract.UserTable.NAME, user.getName());
        cv.put(DbContract.UserTable.IMAGE, user.getImage());
        cv.put(DbContract.UserTable.MAIL, user.getMail());
        cv.put(DbContract.UserTable.PASSWORD, user.getPassword());
        cv.put(DbContract.UserTable.TYPE, user.getTypeUser());
        cv.put(DbContract.UserTable.IN_BLACK_LIST, user.isInBlackList());

        db.insert(DbContract.UserTable.TABLE_NAME, null, cv);
    }


    public void insertNews(News news) {
        SQLiteDatabase db = DbHelper.this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DbContract.NewsTable.TITLE, news.getTitle());
        cv.put(DbContract.NewsTable.IMAGE, news.getImage());
        cv.put(DbContract.NewsTable.DESCRIPTION, news.getDescription());
        cv.put(DbContract.NewsTable.DATE_START, news.getDateStart());
        cv.put(DbContract.NewsTable.DATE_END, news.getDateEnd());

        db.insert(DbContract.NewsTable.TABLE_NAME, null, cv);
    }

    public void deleteNewsLessThanToday(String currentDay) {
        SQLiteDatabase db = DbHelper.this.getWritableDatabase();

        db.delete(
                DbContract.NewsTable.TABLE_NAME,
                DbContract.NewsTable.DATE_END + " < ?",
                new String[] { currentDay }
                );
    }

    public void deleteAnnouncementLessThanToday(String currentDay) {
        SQLiteDatabase db = DbHelper.this.getWritableDatabase();

        db.delete(
                DbContract.AnnouncementTable.TABLE_NAME,
                DbContract.AnnouncementTable.DATE_END + " < ?",
                new String[] { currentDay }
        );
    }
}

package ovsyanik.project.db.async;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ovsyanik.project.data.News;
import ovsyanik.project.db.DbContract;
import ovsyanik.project.db.DbHelper;

public class GetNews extends AsyncTask<String, Void, List<News>> {
    private Context context;
    SQLiteDatabase db = null;

    public GetNews(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        db = DbHelper.getInstance(context).getReadableDatabase();
    }

    @Override
    protected List<News> doInBackground(String... params) {
        List<News> newsList = new ArrayList<>();

        Cursor cursor = db.query(DbContract.NewsTable.TABLE_NAME,
                null,
                DbContract.NewsTable.DATE_START + " <= ? and "
                        + DbContract.NewsTable.DATE_END + " >= ?",
                new String[] { params[0], params[0] },
                null,
                null,
                null);
        Log.d("123", Integer.toString(cursor.getCount()));
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                News news = new News(
                        cursor.getInt(cursor.getColumnIndex(DbContract.NewsTable.ID)),
                        cursor.getString(cursor.getColumnIndex(DbContract.NewsTable.TITLE)),
                        cursor.getString(cursor.getColumnIndex(DbContract.NewsTable.IMAGE)),
                        cursor.getString(cursor.getColumnIndex(DbContract.NewsTable.DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(DbContract.NewsTable.DATE_START)),
                        cursor.getString(cursor.getColumnIndex(DbContract.NewsTable.DATE_END))
                );
                newsList.add(news);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return newsList;
    }

    @Override
    protected void onPostExecute(List<News> news) {
        super.onPostExecute(news);

        db.close();
    }
}

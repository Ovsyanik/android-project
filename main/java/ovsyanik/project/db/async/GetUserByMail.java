package ovsyanik.project.db.async;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import ovsyanik.project.data.TypeUser;
import ovsyanik.project.data.User;
import ovsyanik.project.db.DbContract;
import ovsyanik.project.db.DbHelper;

public class GetUserByMail extends AsyncTask<String, Void, User> {
    private Context context;
    SQLiteDatabase db = null;

    public GetUserByMail(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        db = DbHelper.getInstance(context).getReadableDatabase();
    }

    @Override
    protected User doInBackground(String... params) {
        Cursor cursor = db.query(DbContract.UserTable.TABLE_NAME,
                null,
                DbContract.UserTable.MAIL + " = ? and " + DbContract.UserTable.PASSWORD + " = ?",
                new String[] { params[0], params[1] },
                null,
                null,
                null);
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            return new User(
                    cursor.getInt(cursor.getColumnIndex(DbContract.UserTable.ID)),
                    cursor.getString(cursor.getColumnIndex(DbContract.UserTable.NAME)),
                    cursor.getString(cursor.getColumnIndex(DbContract.UserTable.IMAGE)),
                    cursor.getString(cursor.getColumnIndex(DbContract.UserTable.MAIL)),
                    cursor.getString(cursor.getColumnIndex(DbContract.UserTable.PASSWORD)),
                    TypeUser.valueOf(cursor.getString(cursor.getColumnIndex(DbContract.UserTable.TYPE))),
                    cursor.getInt(cursor.getColumnIndex(DbContract.UserTable.IN_BLACK_LIST))
            );
        } else try {
            throw new Exception("Пользователя с таким логином не существует");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(User user) {
        super.onPostExecute(user);

        db.close();
    }
}

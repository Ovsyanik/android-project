package ovsyanik.project.sql;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ovsyanik.project.data.News;

public class InsertNews extends AsyncTask<News, String, Boolean> {
    private static final String TAG = InsertNews.class.getSimpleName();

    private static final String IP = "80.94.168.145";
    private static final String DBNAME = "OvsyanikProject";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "Pa$$w0rd";

    @Override
    protected Boolean doInBackground(News... news) {
        Connection connection ;
        String connectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            Log.d(TAG, "Driver is registered");

            connectionURL = "jdbc:jtds:sqlserver://" + IP + "/" + DBNAME + ";user="
                    + USERNAME + ";password=" + PASSWORD + ";";
            connection = DriverManager.getConnection(connectionURL);
            PreparedStatement preparedStatement = null;

            preparedStatement = connection.prepareStatement("insert into newsTable(title, image, description, dateStart, dateEnd) " +
                    "values(?, ?, ?, ?, ?)");

            preparedStatement.setString(1, news[0].getTitle());
            preparedStatement.setBytes(2, news[0].getmImage());
            preparedStatement.setString(3, news[0].getDescription());
            preparedStatement.setString(4, news[0].getDateStart());
            preparedStatement.setString(5, news[0].getDateEnd());
            preparedStatement.addBatch();

            preparedStatement.executeBatch();

        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
            Log.e(TAG, throwable.getMessage());
        }

        return true;
    }
}

package ovsyanik.project.sql;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ovsyanik.project.data.Announcement;

public class InsertAnnouncement  extends AsyncTask<Announcement, String, Boolean> {
    private static final String TAG = InsertAnnouncement.class.getSimpleName();

    private static final String IP = "80.94.168.145";
    private static final String DBNAME = "OvsyanikProject";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "Pa$$w0rd";

    @Override
    protected Boolean doInBackground(Announcement... announcement) {
        Connection connection ;
        String connectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            Log.d(TAG, "Driver is registered");

            connectionURL = "jdbc:jtds:sqlserver://" + IP + "/" + DBNAME + ";user="
                    + USERNAME + ";password=" + PASSWORD + ";";
            connection = DriverManager.getConnection(connectionURL);
            PreparedStatement preparedStatement = null;

            preparedStatement = connection.prepareStatement("insert into announcementTable(title, image, description, dateStart, dateEnd) " +
                    "values(?, ?, ?, ?, ?)");

            preparedStatement.setString(1, announcement[0].getTitle());
            preparedStatement.setBytes(2, announcement[0].getmImage());
            preparedStatement.setString(3, announcement[0].getDescription());
            preparedStatement.setString(4, announcement[0].getDateStart());
            preparedStatement.setString(5, announcement[0].getDateEnd());
            preparedStatement.addBatch();

            preparedStatement.executeBatch();

        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
            Log.e(TAG, throwable.getMessage());
        }

        return true;
    }
}

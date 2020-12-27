package ovsyanik.project.sql;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ovsyanik.project.data.Announcement;

public class GetAnnouncement extends AsyncTask<String, String, List<Announcement>> {
    private static final String TAG = GetAnnouncement.class.getSimpleName();

    private static final String IP = "80.94.168.145";
    private static final String DBNAME = "OvsyanikProject";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "Pa$$w0rd";

    @Override
    protected List<Announcement> doInBackground(String... params) {
        Connection connection = null;
        String connectionURL = null;
        List<Announcement> list = new ArrayList<>();
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            Log.d(TAG, "Driver is registered");

            connectionURL = "jdbc:jtds:sqlserver://" + IP + "/" + DBNAME + ";user="
                    + USERNAME + ";password=" + PASSWORD + ";";
            connection = DriverManager.getConnection(connectionURL);

            String query = "select * from announcementTable where dateStart  <= '" + params[0] + "'";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Announcement announcement = new Announcement(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getBytes(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6)
                );
                list.add(announcement);
            }
            resultSet.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
            Log.e(TAG, throwable.getMessage());
        }
        return list;
    }
}

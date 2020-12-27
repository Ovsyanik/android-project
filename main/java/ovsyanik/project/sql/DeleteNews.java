package ovsyanik.project.sql;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteNews extends AsyncTask<Integer, String, Void> {
    private static final String TAG = DeleteNews.class.getSimpleName();

    private static final String IP = "80.94.168.145";
    private static final String DBNAME = "OvsyanikProject";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "Pa$$w0rd";

    @Override
    protected Void doInBackground(Integer... params) {
        Connection connection = null;
        String connectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            Log.d(TAG, "Driver is registered");

            connectionURL = "jdbc:jtds:sqlserver://" + IP + "/" + DBNAME + ";user="
                    + USERNAME + ";password=" + PASSWORD + ";";
            connection = DriverManager.getConnection(connectionURL);

            String query2 = "delete from newsTable where id = " + params[0] ;
            PreparedStatement statement2 = connection.prepareStatement(query2);
            statement2.execute();
            connection.close();
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
            Log.e(TAG, throwable.getMessage());
        }

        return null;
    }
}

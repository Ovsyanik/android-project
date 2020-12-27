package ovsyanik.project.sql;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ovsyanik.project.data.User;

public class InsertUser extends AsyncTask<User, String, Boolean> {
    private static final String TAG = InsertUser.class.getSimpleName();

    private static final String IP = "80.94.168.145";
    private static final String DBNAME = "OvsyanikProject";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "Pa$$w0rd";

    @Override
    protected Boolean doInBackground(User... user) {
        Connection connection ;
        String connectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            Log.d(TAG, "Driver is registered");

            connectionURL = "jdbc:jtds:sqlserver://" + IP + "/" + DBNAME + ";user="
                    + USERNAME + ";password=" + PASSWORD + ";";
            connection = DriverManager.getConnection(connectionURL);
            PreparedStatement preparedStatement = null;

            preparedStatement = connection.prepareStatement("insert into userTable(name, image, mail, password, typeUser, inBlackList) " +
                    "values(?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, user[0].getName());
            preparedStatement.setBytes(2, user[0].getmImage());
            preparedStatement.setString(3, user[0].getMail());
            preparedStatement.setString(4, user[0].getPassword());
            preparedStatement.setString(5, user[0].getTypeUser());
            preparedStatement.setInt(6, user[0].isInBlackList());
            preparedStatement.addBatch();

            preparedStatement.executeBatch();

        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
            Log.e(TAG, throwable.getMessage());
        }

        return true;
    }
}


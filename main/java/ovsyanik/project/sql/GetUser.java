package ovsyanik.project.sql;

import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ovsyanik.project.data.TypeUser;
import ovsyanik.project.data.User;

public class GetUser extends AsyncTask<String, String, User> {
    private static final String TAG = GetUser.class.getSimpleName();

    private static final String IP = "80.94.168.145";
    private static final String DBNAME = "OvsyanikProject";
    private static final String USERNAME = "student";
    private static final String PASSWORD = "Pa$$w0rd";

    @Override
    protected User doInBackground(String... params) {
        Connection connection = null;
        String connectionURL = null;
        User user = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");

            Log.d(TAG, "Driver is registered");

            connectionURL = "jdbc:jtds:sqlserver://" + IP + "/" + DBNAME + ";user="
                    + USERNAME + ";password=" + PASSWORD + ";";
            connection = DriverManager.getConnection(connectionURL);

            String query = "select * from userTable where CONVERT(VARCHAR, mail) = '" + params[0] + "' and CONVERT(VARCHAR, password) = '" + params[1]+ "'";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setmImage(resultSet.getBytes(3));
                user.setMail(resultSet.getString(4));
                user.setPassword(resultSet.getString(5));
                user.setTypeUser(TypeUser.valueOf(resultSet.getString(6)));
                user.setInBlackList(resultSet.getInt(7));
            }
            resultSet.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
            Log.e(TAG, throwable.getMessage());
        }
        return user;
    }
}

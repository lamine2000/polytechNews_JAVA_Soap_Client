package com.db;

import com.service.SQLException_Exception;
import com.service.User;
import com.service.UserManager;
import com.service.UserManagerService;

import java.sql.*;

public class AdminUtility {
    private static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:mysql://194.163.171.45:3306/mglsi_news", "mglsi_user", "verycomplicatedpassword");
    }

    public static Boolean isAdmin(User user) throws SQLException_Exception, SQLException {
        UserManager um = new UserManagerService().getUserManagerPort();
        if (!um.authenticate(user))
            return false;

        PreparedStatement sql = getConnection().prepareStatement("select type from User where login = ?");
        sql.setString(1, user.getLogin());
        ResultSet r = sql.executeQuery();
        r.next();

        return r
                .getString("type")
                .equalsIgnoreCase("ADMINISTRATOR");
    }
}

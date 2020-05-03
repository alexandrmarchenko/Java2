package networkserver.auth;

import java.sql.*;
import java.util.Map;
import java.util.Objects;

public class BaseAuthService implements AuthService {

    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement pstmt;

//    private static class AuthEntry {
//        private String login;
//        private String password;
//
//        public AuthEntry(String login, String password) {
//            this.login = login;
//            this.password = password;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            AuthEntry authEntry = (AuthEntry) o;
//            return Objects.equals(login, authEntry.login) &&
//                    Objects.equals(password, authEntry.password);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(login, password);
//        }
//    }
//
//    private static final Map<AuthEntry, String> NICKNAME_BY_LOGIN_AND_PASS = Map.of(
//            new AuthEntry("login1","pass1"), "nickname1",
//            new AuthEntry("login2","pass2"), "nickname2",
//            new AuthEntry("login3","pass3"), "nickname3"
//    );


    @Override
    public void authLog(String login, String message) throws SQLException {
        pstmt = connection.prepareStatement("insert into auth_logs(login, message) values(?, ?)");
        pstmt.setString(1, login);
        pstmt.setString(2, message);
        pstmt.executeUpdate();
    }

    @Override
    public String getNickByLoginAndPassword(String login, String password) throws SQLException {
        pstmt = connection.prepareStatement("select nickname from users where login=? and password=?");
        pstmt.setString(1, login);
        pstmt.setString(2, password);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("nickname");
        }
        return null;
        //return NICKNAME_BY_LOGIN_AND_PASS.get(new AuthEntry(login, password));
    }

    @Override
    public void start() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        stmt = connection.createStatement();
        System.out.println("Auth service has been started");
    }

    @Override
    public void stop() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

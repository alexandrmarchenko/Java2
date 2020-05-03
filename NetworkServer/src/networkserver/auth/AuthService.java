package networkserver.auth;

import java.sql.SQLException;

public interface AuthService {

    String getNickByLoginAndPassword(String login, String password) throws SQLException;

    void authLog(String login, String message) throws SQLException;

    void start() throws ClassNotFoundException, SQLException;

    void stop();

}

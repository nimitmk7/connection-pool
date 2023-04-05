package src.nimit.swimmingPool;

import jdk.nashorn.internal.objects.annotations.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDBConnection {
  private final Connection connection;

  public MySQLDBConnection() throws SQLException, ClassNotFoundException {
    connection = getServicesDbConnection();
  }

  private Connection getServicesDbConnection() throws SQLException, ClassNotFoundException {
    Class.forName("com.mysql.jdbc.Driver");
    return DriverManager.getConnection("jdbc:mysql://localhost:3306/services?autoReconnect=true&user=root&password=abcd1234&useSSL=false&useServerPrepStmts=false&rewriteBatchedStatements=true&useAffectedRows=true");
  }

  public Connection getConnection() {
    return this.connection;
  }
}

package src.nimit.swimmingPool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SwimmingPool {

  private final MySQLDBConnectionPool mySQLDBConnectionPool;
  public SwimmingPool() throws SQLException, ClassNotFoundException {
    mySQLDBConnectionPool = new MySQLDBConnectionPool();
  }

  private void runPoolCheck() throws InterruptedException, SQLException {
    int i = 1;
    while(i <= 20) {
      MySQLDBConnection mySQLDBConnection = mySQLDBConnectionPool.getConnectionFromBlockingQueue();
      runQuery(mySQLDBConnection, i);
      mySQLDBConnectionPool.replenishConnectionToBlockingQueue(mySQLDBConnection);
      i++;
    }
  }


  private void runQuery(MySQLDBConnection mySQLDBConnection, int i) throws SQLException {
    Statement statement = mySQLDBConnection.getConnection().createStatement();
    ResultSet rs = statement.executeQuery(String.format("SELECT %d", i));
    if (rs != null) {
      System.out.println(i);
    }
  }

  public static void main(String[] args) throws SQLException, InterruptedException, ClassNotFoundException {
    SwimmingPool swimmingPool = new SwimmingPool();
    swimmingPool.runPoolCheck();
  }
}

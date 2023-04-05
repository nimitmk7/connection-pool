package src.nimit.swimmingPool;

import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class MySQLDBConnectionPool {

  protected BlockingQueue<MySQLDBConnection> connectionBlockingQueue;

  public MySQLDBConnectionPool() throws SQLException, ClassNotFoundException {
    connectionBlockingQueue = new LinkedBlockingDeque<>(10);
    addMySQLConnectionsToBlockingQueue(connectionBlockingQueue);
  }

  private void addMySQLConnectionsToBlockingQueue(BlockingQueue<MySQLDBConnection>
                                                    connectionBlockingQueue) throws SQLException, ClassNotFoundException {
    while(connectionBlockingQueue.remainingCapacity() > 0) {
      connectionBlockingQueue.add(new MySQLDBConnection());
    }
  }

  public MySQLDBConnection getConnectionFromBlockingQueue() throws InterruptedException {
    return connectionBlockingQueue.poll(30, TimeUnit.SECONDS);
  }

  public boolean replenishConnectionToBlockingQueue(MySQLDBConnection mySQLDBConnection) {
    try {
      return connectionBlockingQueue.add(mySQLDBConnection);
    } catch (IllegalStateException e) {
      throw new RuntimeException("Can't add connection to queue due to capacity restrictions", e);
    }
  }
}

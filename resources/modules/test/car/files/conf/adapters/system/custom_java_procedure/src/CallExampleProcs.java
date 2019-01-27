/**
 * Copyright (c) 2004, 2008
 * Composite Software, Inc.
 * All Rights Reserved
 */

/**
 * Custom Procedure Examples
 */
import java.lang.reflect.*;
import java.sql.*;

public class CallExampleProcs
{
  private static final String CS_URL =
   "jdbc:compositesw:dbapi@localhost:9401?domain=composite&dataSource=tutorial";

  private static final String[] TEST_NAMES = {
    "expr",
    "externalUpdate",
    "hookCursor",
    "hookPhoneFax",
    "nonTransactional",
    "outputCursor",
    "simpleProcInvoke",
    "simpleQuery",
    "simpleUpdate"
  };

  public static void main(String[] args) {
    try {
      Class.forName("cs.jdbc.driver.CompositeDriver");
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
      return;
    }

    try {
      Connection conn =
        DriverManager.getConnection(CS_URL, "admin", "admin");
      for (int i=0; i<TEST_NAMES.length; i++)
        invokeTest(conn, TEST_NAMES[i]);
      conn.close();
    }
    catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  private static void invokeTest(Connection conn, String testName) {
    System.out.println("");
    System.out.println("RUN TEST: " + testName);
    try {
      Method testMethod =
        CallExampleProcs.class.getMethod(testName,
                                         new Class[] {Connection.class});
      testMethod.invoke(null, new Object[] {conn});
      System.out.println("SUCCESS");
    } catch (NoSuchMethodException ex) {
      ex.printStackTrace();
      System.out.println("FAILURE");
    } catch (IllegalAccessException ex) {
      ex.printStackTrace();
      System.out.println("FAILURE");
    } catch (InvocationTargetException ex) {
      ex.getCause().printStackTrace();
      System.out.println("FAILURE");
    }
  }

  private static void setForumParameters(CallableStatement stmt)
    throws SQLException
  {
    stmt.setInt(1, 23);
    stmt.setString(2, "Michael");
    stmt.setString(3, "Krasny");
    stmt.setString(4, "KQED");
    stmt.setString(5, "(866) SF-FORUM");
  }

  private static void printResultSet(ResultSet rs)
    throws SQLException
  {
    ResultSetMetaData metaData = rs.getMetaData();
    int rowIndex = 0;
    while (rs.next()) {
      System.out.println("Row " + rowIndex++);
      for (int i=1; i<=metaData.getColumnCount(); i++) {
        System.out.println("  Column " + i + " " + metaData.getColumnName(i) +
                           " " + rs.getString(i));
      }
    }
  }

  public static void expr(Connection conn)
    throws SQLException
  {
    String sql = "{CALL expr(?, '*', ?, ?)}";
    CallableStatement stmt = conn.prepareCall(sql);
    stmt.setInt(1, 123);
    stmt.setInt(2, 456);
    stmt.registerOutParameter(3, Types.INTEGER);
    stmt.execute();
    System.out.println(sql + " = " + stmt.getInt(3));
    stmt.close();
  }

  public static void externalUpdate(Connection conn)
    throws SQLException
  {
    String sql = "{CALL ExternalUpdate(?, ?, ?, ?, ?)}";
    CallableStatement stmt = conn.prepareCall(sql);
    setForumParameters(stmt);
    stmt.execute();
    System.out.println(sql);
    System.out.println("update count = " + stmt.getUpdateCount());
    stmt.close();
  }

  public static void nonTransactional(Connection conn)
    throws SQLException
  {
    String sql = "{CALL NonTransactional(?, ?, ?, ?, ?)}";
    CallableStatement stmt = conn.prepareCall(sql);
    setForumParameters(stmt);
    stmt.execute();
    System.out.println(sql);
    System.out.println("update count = " + stmt.getUpdateCount());
    stmt.close();
  }

  public static void hookCursor(Connection conn)
    throws SQLException
  {
    String sql = "SELECT * FROM customers WHERE City = 'Harbin'";
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery(sql);
    System.out.println(sql);
    printResultSet(rs);
    stmt.close();
  }

  public static void hookPhoneFax(Connection conn)
    throws SQLException
  {
    String sql =
      "UPDATE customers SET FaxNumber = '(415) 555-1212' " +
      "WHERE CustomerID = 23";
    Statement stmt = conn.createStatement();
    int updateCount = stmt.executeUpdate(sql);
    System.out.println(sql);
    System.out.println("update count = " + updateCount);
    stmt.close();
  }

  public static void outputCursor(Connection conn)
    throws SQLException
  {
    String sql = "{CALL OutputCursor(?)}";
    CallableStatement stmt = conn.prepareCall(sql);
    stmt.registerOutParameter(1, Types.OTHER);
    stmt.execute();
    System.out.println(sql);
    printResultSet((ResultSet)stmt.getObject(1));
    stmt.close();
  }

  public static void simpleProcInvoke(Connection conn)
    throws SQLException
  {
    String sql = "{CALL SimpleProcInvoke(?, '*', ?, ?)}";
    CallableStatement stmt = conn.prepareCall(sql);
    stmt.setInt(1, 123);
    stmt.setInt(2, 456);
    stmt.registerOutParameter(3, Types.INTEGER);
    stmt.execute();
    System.out.println(sql + " = " + stmt.getInt(3));
    stmt.close();
  }

  public static void simpleQuery(Connection conn)
    throws SQLException
  {
    String sql = "{CALL SimpleQuery(23, ?)}";
    CallableStatement stmt = conn.prepareCall(sql);
    stmt.registerOutParameter(1, Types.OTHER);
    stmt.execute();
    System.out.println(sql);
    printResultSet((ResultSet)stmt.getObject(1));
    stmt.close();
  }

  public static void simpleUpdate(Connection conn)
    throws SQLException
  {
    String sql = "{CALL SimpleUpdate(?, ?, ?, ?, ?)}";
    CallableStatement stmt = conn.prepareCall(sql);
    setForumParameters(stmt);
    stmt.execute();
    System.out.println(sql);
    System.out.println("update count = " + stmt.getUpdateCount());
    stmt.close();
  }
}

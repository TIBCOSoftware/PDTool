/**
 * Custom Procedure Examples
 */
package com.p6.projects;

import com.compositesw.extension.*;
import com.p6.activities.P6APIActivitiesConnect;

import java.sql.*;
/**
 * This custom procedure executes a simple query statement
 */
public class P6APIProjects
  implements CustomProcedure
{
  private CustomCursor cc = null;
  private P6APIProjectsConnect p6 = null;
  public P6APIProjects() { }
  /**
   * This is called once just after constructing the class.  The
   * environment contains methods used to interact with the server.
   */
  public void initialize(ExecutionEnvironment qenv) {
	  cc = null;
	  p6 = null;

  }
  
  public static void main(String[] args) {
	  
	  P6APIProjects app = new P6APIProjects();

	  Object[] inputParams = new String[]{"admin","admin","C:/P6IntegrationAPI_1",null};
	  try {
		app.invoke(inputParams);
		
		Object[] objOut = app.getOutputValues();
		System.out.println("record size " + objOut.length);
		System.out.println(objOut.toString());
		
		app.close();
		
		
	} catch (CustomProcedureException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  
  }
  
  /**
   * Called during introspection to get the description of the input
   * and output parameters.  Should not return null.
   */
  public ParameterInfo[] getParameterInfo() {
    return new ParameterInfo[] {
      new ParameterInfo("username", Types.VARCHAR, DIRECTION_IN),
      new ParameterInfo("password", Types.VARCHAR, DIRECTION_IN),
      new ParameterInfo("bootstrap", Types.VARCHAR, DIRECTION_IN),
      new ParameterInfo("where_condition", Types.VARCHAR, DIRECTION_IN),
      new ParameterInfo("result", TYPED_CURSOR, DIRECTION_OUT,
        new ParameterInfo[] {
    	  new ParameterInfo("ObjectId", Types.INTEGER, DIRECTION_NONE), 
          new ParameterInfo("ProjectId", Types.VARCHAR, DIRECTION_NONE), 
          new ParameterInfo("ProjectName", Types.VARCHAR, DIRECTION_NONE),
          new ParameterInfo("StartDate", Types.DATE, DIRECTION_NONE),
          new ParameterInfo("FinishDate", Types.DATE, DIRECTION_NONE),
          new ParameterInfo("DataDate", Types.DATE, DIRECTION_NONE),
          new ParameterInfo("Status", Types.VARCHAR, DIRECTION_NONE)
        }
      )
    };
  }
  /**
   * Called to invoke the stored procedure.  Will only be called a
   * single time per instance.  Can throw CustomProcedureException or
   * SQLException if there is an error during invoke.
   */
  public void invoke(Object[] inputValues)
    throws CustomProcedureException, SQLException
  {
	  String username = null;
	  String password = null;
	  String bootstrap = null;
	  String where = null;
	  
	  if (inputValues[0] != null)
		  username = String.valueOf(inputValues[0]);
	  if (inputValues[1] != null)
		  password = String.valueOf(inputValues[1]);
	  if (inputValues[2] != null)
		  bootstrap = String.valueOf(inputValues[2]);
	  if (inputValues[3] != null)
		  where = String.valueOf(inputValues[3]);
	  
	  p6 = new P6APIProjectsConnect(username, password, bootstrap);
	  
	  if (p6.isConnected()) {
		  cc = p6.getCustomCursor(where);  
	  } else {
		  cc = null;
	  }

}
  /**
   * Called to retrieve the number of rows that were inserted,
   * updated, or deleted during the execution of the procedure. A
   * return value of -1 indicates that the number of affected rows is
   * unknown.  Can throw CustomProcedureException or SQLException if
   * there is an error when getting the number of affected rows.
   */
  public int getNumAffectedRows() {
    return 0;
  }
  /**
   * Called to retrieve the output values.  The returned objects
   * should obey the Java to SQL typing conventions as defined in the
   * table above.  Output cursors can be returned as either
   * CustomCursor or java.sql.ResultSet.  Can throw
   * CustomProcedureException or SQLException if there is an error
   * when getting the output values.  Should not return null.
   */

  public Object[] getOutputValues() {
    return new Object[] { cc };
  }
  /**
   * Called when the procedure reference is no longer needed.  Close
   * may be called without retrieving any of the output values (such
   * as cursors) or even invoking, so this needs to do any remaining
   * cleanup.  Close may be called concurrently with any other call
   * such as "invoke" or "getOutputValues".  In this case, any pending
   * methods should immediately throw a CustomProcedureException.
   */
  public void close() throws SQLException {
    if (cc != null) {
      try {
		cc.close();
		
	} catch (CustomProcedureException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
    if (p6 != null) {
        try {
  		p6.close();
  		
  	} catch (CustomProcedureException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}
      }
  }
  //
  // Introspection methods
  //
  /**
   * Called during introspection to get the short name of the stored
   * procedure.  This name may be overridden during configuration.
   * Should not return null.
   */
  public String getName() {
    return "P6APIProjects";
  }
  /**
   * Called during introspection to get the description of the stored
   * procedure.  Should not return null.
   */
  public String getDescription() {
    return "This procedure performs a simple query operation";
  }
//
  // Transaction methods
  //
  /**
   * Returns true if the custom procedure uses transactions.  If this
   * method returns false then commit and rollback will not be called.
   */
  public boolean canCommit() {
    return false;
  }
  /**
   * Commit any open transactions.
   */
  public void commit() { }
  /**
   * Rollback any open transactions.
   */
  public void rollback() { }
  /**
   * Returns true if the transaction can be compensated.
   */
  public boolean canCompensate() {
    return false;
  }
  /**
   * Compensate any committed transactions (if supported).
   */
  public void compensate(ExecutionEnvironment qenv) { }
}
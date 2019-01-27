/**
 * Copyright (c) 2004, 2008
 * Composite Software, Inc.
 * All Rights Reserved
 */

import com.compositesw.extension.*;
import com.compositesw.server.customproc.cvt.CustomCursor2ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

/**
 * Custom hook used to filter query results based on the current user name.
 */
public class CapsHook
  implements CustomProcedure
{
  private ExecutionEnvironment qenv;
  private ProcedureReference nextHook;

  /**
   * This is called once just after constructing the class.  The
   * environment contains methods used to interact with the server.
   */
  public void initialize(ExecutionEnvironment qenv)
    throws SQLException
  {
    this.qenv = qenv;
    this.qenv.log(LOG_INFO, "CapsHook initialized.");

  }

  /**
   * Called during introspection to get the description of the input
   * and output parameters.  Should not return null.
   */
  public ParameterInfo[] getParameterInfo() {
    return new ParameterInfo[] {
      new ParameterInfo("type", Types.INTEGER, DIRECTION_IN),
      new ParameterInfo("text", Types.VARCHAR, DIRECTION_IN),
      new ParameterInfo("params", Types.OTHER, DIRECTION_IN),
      new ParameterInfo("cursor", GENERIC_CURSOR, DIRECTION_OUT),
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
    int type = ((Integer)inputValues[0]).intValue();
    String sql = (String)inputValues[1];
    Object[] args = (Object[])inputValues[2];
    
  //  this.qenv.log(LOG_INFO, "CapsHook - Input Value: " + sql);
    
    inputValues[1] = sql.toUpperCase();

    nextHook = qenv.lookupNextHook();
    nextHook.invoke(inputValues);
  }

  /**
   * Called to retrieve the number of rows that were inserted,
   * updated, or deleted during the execution of the procedure. A
   * return value of -1 indicates that the number of affected rows is
   * unknown.  Can throw CustomProcedureException or SQLException if
   * there is an error when getting the number of affected rows.
   */
  public int getNumAffectedRows()
    throws CustomProcedureException, SQLException
  {
    return nextHook.getNumAffectedRows();
  }

  /**
   * Called to retrieve the output values.  The returned objects
   * should obey the Java to SQL typing conventions as defined in the
   * table above.  Output cursors can be returned as either
   * CustomCursor or java.sql.ResultSet.  Can throw
   * CustomProcedureException or SQLException if there is an error
   * when getting the output values.  Should not return null.
   */
  public Object[] getOutputValues()
    throws CustomProcedureException, SQLException
  {
    Object[] outputValues;
    
    outputValues = nextHook.getOutputValues();
    CustomCursor2ResultSet ccrs = (CustomCursor2ResultSet) outputValues[0];
    ResultSetMetaData rsmd = ccrs.getMetaData();
    int iCol = rsmd.getColumnCount();
    
    TempCursor tc = new TempCursor(rsmd, qenv);

    for (int x = 0; x < outputValues.length; x++)
    {
    	Object[] row;
    	ccrs = (CustomCursor2ResultSet) outputValues[x];
    	
    	while(ccrs.next()){
    		row = new Object[iCol];
        	for (int y = 1; y < iCol + 1; y++)
        	{
        		Object oTemp = ccrs.getObject(y);
        		row[y - 1] = oTemp;  
        		if (rsmd.getColumnType(y) == java.sql.Types.VARCHAR)
        		{
        			if (ccrs.getString(y) != null)
        			{
        			//	this.qenv.log(LOG_INFO, ccrs.getString(y));
        				oTemp = ccrs.getString(y).toUpperCase();
        			//	this.qenv.log(LOG_INFO, oTemp.toString());
        				row[y - 1] = oTemp; 
        			}
        		}
        	}
        	tc.addRecord(row);
    	}
    }

    return new Object[]{tc};
  }

  /**
   * Called when the procedure reference is no longer needed.  Close
   * may be called without retrieving any of the output values (such
   * as cursors) or even invoking, so this needs to do any remaining
   * cleanup.  Close may be called concurrently with any other call
   * such as "invoke" or "getOutputValues".  In this case, any pending
   * methods should immediately throw a CustomProcedureException.
   */
  public void close()
    throws CustomProcedureException, SQLException
  {
	//if (outputCursor != null) outputCursor.close();
   // nextHook.close();
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
    return "CapsHook";
  }

  /**
   * Called during introspection to get the description of the stored
   * procedure.  Should not return null.
   */
  public String getDescription() {
    return "Capitalizes all VARCHAR column data.";
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
  public void commit()
    throws SQLException
  { }

  /**
   * Rollback any open transactions.
   */
  public void rollback()
    throws SQLException
  { }

  /**
   * Returns true if the transaction can be compensated.
   */
  public boolean canCompensate() {
    return false;
  }

  /**
   * Compensate any committed transactions (if supported).
   */
  public void compensate(ExecutionEnvironment qenv)
    throws SQLException
  { }
  
  
class TempCursor implements CustomCursor {
	
	private ParameterInfo[] pInfo = null;
	private ArrayList<Object[]> alRows = null;
	private int curr_row = 0;
	private ExecutionEnvironment qenv;
	
	public TempCursor(ResultSetMetaData rsmd, ExecutionEnvironment qenv){
		this.alRows = new ArrayList<Object[]>();
		this.qenv = qenv;
		int iCol = 0;
		try {
			iCol = rsmd.getColumnCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.qenv.log(LOG_ERROR, e.getMessage());
		}
		
		pInfo = new ParameterInfo[iCol];
		for (int x = 1; x < iCol + 1; x++) {
			try {
				pInfo[x - 1] = new ParameterInfo(rsmd.getColumnName(x), rsmd.getColumnType(x),ProcedureConstants.DIRECTION_NONE);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.qenv.log(LOG_ERROR, e.getMessage());
			}
		}
	}
	
	public void addRecord(Object[] row) {
		alRows.add(row);
	}

	@Override
	public void close() throws CustomProcedureException, SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public ParameterInfo[] getColumnInfo() {
		// TODO Auto-generated method stub
		return pInfo;
	}

	@Override
	public Object[] next() throws CustomProcedureException, SQLException {
		// TODO Auto-generated method stub
		Object[] objResult = null;
		
		if (alRows.size() > curr_row) {
			objResult = alRows.get(curr_row);
			curr_row++;
		}
		
		return objResult;
	}
	
}
}

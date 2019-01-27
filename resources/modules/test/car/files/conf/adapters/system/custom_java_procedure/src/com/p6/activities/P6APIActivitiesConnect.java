package com.p6.activities;

import com.compositesw.extension.CustomCursor;
import com.compositesw.extension.CustomProcedureException;
import com.compositesw.extension.ParameterInfo;
import com.primavera.integration.client.bo.object.Activity;
import com.primavera.integration.client.Session;
import com.primavera.integration.common.DatabaseInstance;
import com.primavera.integration.client.RMIURL;
import com.primavera.integration.client.bo.BOIterator;
import java.sql.*;
import com.compositesw.extension.ProcedureConstants;

public class P6APIActivitiesConnect {
	
    private com.primavera.integration.client.Session session;
    private String uname = "admin";
    private String upass = "admin";
    private boolean bConnected = false;
    
    public P6APIActivitiesConnect(String username, String password, String bootstrap) {
    	
    	uname = username;
    	upass = password;
    	
    	System.setProperty("primavera.bootstrap.home", bootstrap);

    	 try {
    		 login(uname, upass);
    		 bConnected = true;
    	 } catch (Exception ex){
    		 bConnected = false;
    	 }
    }
    
    public boolean isConnected() {
    	return bConnected;
    }

    public void login(String uname, String upass) throws Exception 
    {
        int iDB = 0;

        DatabaseInstance[] dbInstances = Session.getDatabaseInstances(
            RMIURL.getRmiUrl(RMIURL.LOCAL_SERVICE));
        
        session = Session.login(RMIURL.getRmiUrl(RMIURL.LOCAL_SERVICE),
            dbInstances[iDB].getDatabaseId(), uname, upass);
    }
    
    public void logout()
    {
    	if (session != null) session.logout();
        session = null;
    }
    
	public void close() throws CustomProcedureException, SQLException {
		// TODO Auto-generated method stub
		logout();
		
	}
    
    public CustomCursor getCustomCursor(String where) {
    	
    	CustomCursor cc = null;
    	
    	String[] fields = new String[]{"ProjectObjectId","ProjectId","Id","Name","StartDate","FinishDate",
				"AtCompletionDuration","Status"};
    	try {
    		BOIterator<Activity> boiActivities = session.getEnterpriseLoadManager().loadActivities(fields, where, null);
    		cc = new ActivityCursor(boiActivities);
    	} catch (Exception ex) {
    		ex.printStackTrace(System.out);
    	}
    	return cc;
    }
    
class ActivityCursor implements CustomCursor {
	
	private BOIterator<Activity> boiActivities;
	
	public ActivityCursor(BOIterator<Activity> boi){
		boiActivities = boi;
	}

	@Override
	public void close() throws CustomProcedureException, SQLException {
		// TODO Auto-generated method stub
		logout();
		
	}

	@Override
	public ParameterInfo[] getColumnInfo() {
		// TODO Auto-generated method stub
		return new ParameterInfo[] {
			      new ParameterInfo("ProjectObjectId", Types.INTEGER, ProcedureConstants.DIRECTION_NONE),
		          new ParameterInfo("ProjectId", Types.VARCHAR, ProcedureConstants.DIRECTION_NONE),
		          new ParameterInfo("ActivityId", Types.VARCHAR, ProcedureConstants.DIRECTION_NONE),
		          new ParameterInfo("ActivityName", Types.VARCHAR, ProcedureConstants.DIRECTION_NONE),
		          new ParameterInfo("StartDate", Types.DATE, ProcedureConstants.DIRECTION_NONE),
		          new ParameterInfo("FinishDate", Types.DATE, ProcedureConstants.DIRECTION_NONE),
		          new ParameterInfo("Duration", Types.DOUBLE, ProcedureConstants.DIRECTION_NONE),
		          new ParameterInfo("Status", Types.VARCHAR, ProcedureConstants.DIRECTION_NONE)
		        };
	}

	@Override
	public Object[] next() throws CustomProcedureException, SQLException {
		// TODO Auto-generated method stub
		Object[] objResult = null;
		
		try
		{
			if (boiActivities.hasNext())
			{
				Activity act = boiActivities.next();
				
				objResult = new Object[8];
				objResult[0] = act.getProjectObjectId().getPrimaryKeyObject();
				objResult[1] = act.getProjectId();
				objResult[2] = act.getId();
				objResult[3] = act.getName();
				objResult[4] = (Date) act.getStartDate();
				objResult[5] = (Date) act.getFinishDate();
				objResult[6] = new Double(act.getAtCompletionDuration().doubleValue());
				objResult[7] = act.getStatus().getDescription();
			}
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
		return objResult;
	}
	
}
    
}
package com.p6.projects;

import com.compositesw.extension.CustomCursor;
import com.compositesw.extension.CustomProcedureException;
import com.compositesw.extension.ParameterInfo;
import com.primavera.integration.client.bo.object.Activity;
import com.primavera.integration.client.bo.object.Project;
import com.primavera.integration.client.Session;
import com.primavera.integration.common.DatabaseInstance;
import com.primavera.integration.client.RMIURL;
import com.primavera.integration.client.bo.BOIterator;
import java.sql.*;
import com.compositesw.extension.ProcedureConstants;

public class P6APIProjectsConnect {
	
    private com.primavera.integration.client.Session session;
    private String uname = "admin";
    private String upass = "admin";
    private boolean bConnected = false;
    
    public P6APIProjectsConnect(String username, String password, String bootstrap) {
    	
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
    	
    	String[] fields = new String[]{"ObjectId","Id","Name","StartDate","FinishDate",
				"DataDate","Status"};
    	try {
    		BOIterator<Project> boiProjects = session.getGlobalObjectManager().loadProjects(fields, where, null);
    		cc = new ProjectCursor(boiProjects);
    	} catch (Exception ex) {
    		ex.printStackTrace(System.out);
    	}
    	return cc;
    }
    
class ProjectCursor implements CustomCursor {
	
	private BOIterator<Project> boiProjects;
	
	public ProjectCursor(BOIterator<Project> boi){
		boiProjects = boi;
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
				  new ParameterInfo("ObjectId", Types.INTEGER, ProcedureConstants.DIRECTION_NONE),
		          new ParameterInfo("ProjectId", Types.VARCHAR, ProcedureConstants.DIRECTION_NONE),
		          new ParameterInfo("ProjectName", Types.VARCHAR, ProcedureConstants.DIRECTION_NONE),
		          new ParameterInfo("StartDate", Types.DATE, ProcedureConstants.DIRECTION_NONE),
		          new ParameterInfo("FinishDate", Types.DATE, ProcedureConstants.DIRECTION_NONE),
		          new ParameterInfo("DataDate", Types.DATE, ProcedureConstants.DIRECTION_NONE),
		          new ParameterInfo("Status", Types.VARCHAR, ProcedureConstants.DIRECTION_NONE)
		        };
	}

	@Override
	public Object[] next() throws CustomProcedureException, SQLException {
		// TODO Auto-generated method stub
		Object[] objResult = new Object[]{null, null, null, null, null, null, null};
		
		try
		{
			if (boiProjects.hasNext())
			{
				Project proj = boiProjects.next();
				
				objResult = new Object[]{null, null, null, null, null, null, null};
				
				objResult[0] = proj.getObjectId().getPrimaryKeyObject();
				objResult[1] = proj.getId();
				objResult[2] = proj.getName();
				if (proj.getStartDate() != null)
					objResult[3] = (Date) proj.getStartDate();
				if (proj.getFinishDate() != null)
					objResult[4] = (Date) proj.getFinishDate();
				if (proj.getDataDate() != null)
					objResult[5] = (Date) proj.getDataDate();
				if (proj.getStatus() != null)
					objResult[6] = proj.getStatus().getDescription();
			}
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
		return objResult;
	}
	
}
    
}
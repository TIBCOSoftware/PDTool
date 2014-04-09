/*******************************************************************************
* Copyright (c) 2014 Cisco Systems
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* PDTool project commiters - initial release
*******************************************************************************/
/**
 * (c) 2014 Cisco and/or its affiliates. All rights reserved.
 */
package com.cisco.dvbu.cmdline.vcs.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.cisco.dvbu.ps.common.exception.ApplicationException;
import com.compositesw.cmdline.archive.ArchiveUtil;
import com.compositesw.cmdline.archive.PackageCommandConstants;
import com.compositesw.cdms.webapi.WArchive;
import com.compositesw.cdms.webapi.WSession;
import com.compositesw.cdms.webapi.support.WResourceEntry;
import com.compositesw.common.vcs.primitives.FilePrimitives;
import com.compositesw.common.StackTraceUtil;
import com.compositesw.common.archive.ArchiveConstants;
import com.compositesw.common.vcs.primitives.ZipPrimitives;
import com.compositesw.common.vcs.primitives.IOPrimitives.EmptyAwareFileOutputStream;

public class ExportCommand implements ArchiveConstants, PackageCommandConstants  {

    private static final String VCS_EXPORT_ZIP = "vcs_export.zip";
    
    public static void startCommand(String baseDir, String homeDir, String[] args) throws Exception {
        ExportOptions state = new ExportOptions(args);
        
        int exitCode = PackageCommandConstants.EXIT_WITH_NO_ERRORS;
        OutputStream outFile = null;
        EmptyAwareFileOutputStream eafos = null;
        File exportZipFile = new File(state.tempDirFile, VCS_EXPORT_ZIP);
        WSession session = null;
        WArchive archive = null;
        String errorMessage = null;

        try { 
            // Open local output
            eafos = new EmptyAwareFileOutputStream(exportZipFile);
            outFile = new BufferedOutputStream(eafos);

            // Open network session
            session = ArchiveUtil.connectToServer(state.server, state.port,
                                                  state.encrypt, state.domain, state.username, state.password);

            // Perform the actual export
            Map<Object, Object> settings = new HashMap<Object, Object>();
            settings.put(KEY_VCS_EXPORT, null);

            WResourceEntry[] entries = new WResourceEntry[1];
            // TODO: WResourceEntry is under-specified. Resource type is missing.
            entries[0] = new WResourceEntry(-1, state.namespacePath, state.resourceTypeType, true);
            
            archive = session.createExportArchive(settings, entries);
            if (archive == null) throw new IllegalStateException("Error during package file export. Export archive is null.");            

            archive.downloadStream(outFile);
            outFile.flush();

        } catch (Throwable ex) {
            exitCode = PackageCommandConstants.PKGFILE_EXCEPTION;
            
            errorMessage = "Encountered problem during vcs export (export): " + FilePrimitives.LS +
            	ex.getMessage() + FilePrimitives.LS + StackTraceUtil.getExceptionStackTrace(ex);
            System.err.println(errorMessage);
            
        } finally {
            // release remote resources
            if (archive != null) {
                try {
                    archive.close();
                }
                catch (Exception e) {
                	errorMessage = "Encountered problem while closing archive: " + exportZipFile + FilePrimitives.LS +
                    	StackTraceUtil.getExceptionStackTrace(e);
                    System.err.println(errorMessage);
                }
                archive = null;
            }

            // close session
            if (session != null) {
                ArchiveUtil.closeSession(session);
                session = null;
            }
            
            // release local resources
            if (outFile != null) {
                try {
                    outFile.close();
                    outFile = null;
                } catch (Exception e) {
                    // ignore
                }
            }
        }
        
        try {
            // explode locally
            if (eafos != null && eafos.isEmpty()) {
                exportZipFile.delete();
            }
            else {
                ZipPrimitives.explode(exportZipFile);    
            }
        } catch (Throwable ex) {
            exitCode = PackageCommandConstants.PKGFILE_EXCEPTION;
            
            errorMessage = "Encountered problem during vcs export (unzip): " + FilePrimitives.LS +
            	ex.getMessage() + FilePrimitives.LS + StackTraceUtil.getExceptionStackTrace(ex);
            System.err.println(errorMessage);            
        }
        
        if (exitCode != PackageCommandConstants.EXIT_WITH_NO_ERRORS)
        	throw new ApplicationException(errorMessage);
        	//System.exit(exitCode);
    }
}

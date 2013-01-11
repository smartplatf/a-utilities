/**
 * Utilities - Utilities used by anon
 *
 * Copyright (C) 2012 Individual contributors as indicated by
 * the @authors tag
 *
 * This file is a part of Utilities.
 *
 * Utilities is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Utilities is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * */
 
/**
 * ************************************************************
 * HEADERS
 * ************************************************************
 * File:                org.anon.utilities.codecontrol.CodeControl
 * Author:              rsankar
 * Revision:            1.0
 * Date:                03-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * Controls the code usage
 *
 * ************************************************************
 * */

package org.anon.utilities.codecontrol;

import java.security.Permission;

public class CodeControl
{
    //Throws this exception when exit is used in the code
    public static class ExitTrappedException extends SecurityException {}

    /**
     * This class provides the security manager that overrides the checkExit call.
     * If the exit is called, this throws the ExitTrappedException. 
     *
     * */
    public static class StopExitSecurityManager extends SecurityManager
    {
        private SecurityManager _prevMgr = System.getSecurityManager();

        public void checkPermission(Permission perm)
        {
        }

        public void checkExit(int status)
        {
            super.checkExit(status);
            throw new ExitTrappedException();
        }

        public SecurityManager getPreviousMgr() { return _prevMgr; }
    }

    //this class cannot be created.
    private CodeControl()
    {
    }

    /**
     * Call this function in the beginning of the code from where exit calls should be
     * blocked. Once the code block where exit should be called is done, call the enableSystemExitCall
     * function. THis sets up the security manager for the piece of code as the StopExitSecurityManager
     *
     * */
    public static void forbidSystemExitCall() 
    {
        SecurityManager securityManager = new StopExitSecurityManager();
        System.setSecurityManager(securityManager) ;
    }

    /**
     * Call this function to enable exit calls. This restores the security Manger to the previous
     * security manager. Please note, if the forbidSystemExitCall was not called to stop exit
     * calls, this does not do anything by set the security manager to null.
     * */
    public static void enableSystemExitCall() 
    {
        SecurityManager mgr = System.getSecurityManager();
        if ((mgr != null) && (mgr instanceof StopExitSecurityManager))
        {
            StopExitSecurityManager smgr = (StopExitSecurityManager)mgr;
            System.setSecurityManager(smgr.getPreviousMgr());
        }
        else
            System.setSecurityManager(null);
    }
}


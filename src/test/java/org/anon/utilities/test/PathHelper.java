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
 * File:                org.anon.utilities.test.PathHelper
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A helper that creates the path for this specific environment
 *
 * ************************************************************
 * */

package org.anon.utilities.test;

public class PathHelper
{
    public PathHelper()
    {
    }

    public static String getBasePath(boolean withproto)
    {
        String home = System.getenv("HOME");
        String mavenhome = System.getProperty("maven.repository");
        String deploypath = System.getProperty("deployment.group");
        String proto = "";
        if (withproto) proto = "file://";
        String jarFile = proto + home + "/" + mavenhome + deploypath;
        return jarFile;
    }

    public static String getDependantPath(boolean withproto, String jar)
    {
        String home = System.getenv("HOME");
        String mavenhome = System.getProperty("maven.repository");
        String proto = "";
        if (withproto) proto = "file://";
        String jarFile = proto + home + "/" + mavenhome;
        return jarFile + jar;
    }

    public static String getJar(boolean withproto, String which)
    {
        String app = System.getProperty(which);
        String jarFile = getBasePath(withproto) + app;
        return jarFile;
    }

    public static String getProjectBuildPath()
        throws Exception
    {
        String projectHome = System.getProperty("user.dir");
        java.io.File fi = new java.io.File(projectHome);
        java.net.URL fu = fi.toURL();
        return fu.toString() + "/target/classes/";
    }

    public static String getProjectTestBuildPath()
        throws Exception
    {
        String projectHome = System.getProperty("user.dir");
        java.io.File fi = new java.io.File(projectHome);
        java.net.URL fu = fi.toURL();
        return fu.toString() + "/target/test-classes/";
    }
}


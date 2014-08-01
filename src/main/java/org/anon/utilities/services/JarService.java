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
 * File:                org.anon.utilities.services.JarService
 * Author:              rsankar
 * Revision:            1.0
 * Date:                26-03-2014
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A set of services related to jars
 *
 * ************************************************************
 * */

package org.anon.utilities.services;

import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;
import java.util.Enumeration;

import org.anon.utilities.exception.CtxException;

import static org.anon.utilities.services.ServiceLocator.*;

public class JarService extends ServiceLocator.Service
{
    public JarService()
    {
        super();
    }

    public void extractJar(String jarFile, String dir, String createdir)
        throws CtxException
    {
        try
        {
            File check = new File(dir);
            assertion().assertTrue(check.exists(), "Please provide a directory that exists.");
            assertion().assertTrue(check.isDirectory(), "Cannot extract into a file. Please provide a directory that exists.");

            File fcheck = new File(jarFile);
            assertion().assertTrue(fcheck.exists(), "Please provide an existing jar/ear file.");

            String destDir = dir + File.separator + createdir;
            File cdir = new File(destDir);
            if (!cdir.exists())
                cdir.mkdirs();

            JarFile jar = new JarFile(jarFile);
            Enumeration ejar = jar.entries();
            while (ejar.hasMoreElements()) 
            {
                JarEntry file = (JarEntry) ejar.nextElement();
                String name = destDir + File.separator + file.getName();
                File f = new File(destDir + File.separator + file.getName());
                if (file.isDirectory()) 
                { // if its a directory, create it
                    f.mkdirs();
                }
            }

            ejar = jar.entries();
            while (ejar.hasMoreElements())
            {
                JarEntry file = (JarEntry) ejar.nextElement();
                String name = destDir + File.separator + file.getName();
                File f = new File(destDir + File.separator + file.getName());
                if (!file.isDirectory())
                {
                    InputStream is = jar.getInputStream(file); // get the input stream
                    FileOutputStream fos = new FileOutputStream(f);
                    while (is.available() > 0) 
                    {  // write contents of 'is' to 'fos'
                        fos.write(is.read());
                    }
                    fos.close();
                    is.close();
                }
            }
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Cannot extract.", e.getMessage()));
        }
    }
}


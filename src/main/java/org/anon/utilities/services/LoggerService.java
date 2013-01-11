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
 * File:                org.anon.utilities.services.LoggerService
 * Author:              rsankar
 * Revision:            1.0
 * Date:                05-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A service to create and use logging facilities
 *
 * ************************************************************
 * */

package org.anon.utilities.services;

import org.anon.utilities.logger.Logger;
import org.anon.utilities.loader.CrossLinkRelatedObject;
import org.anon.utilities.loader.RelatedUtils;

public class LoggerService extends ServiceLocator.Service
{
    LoggerService()
    {
        super();
    }

    Logger logger(Object obj, String module)
    {
        CrossLinkRelatedObject robj = null;
        if (obj != null)
            robj = RelatedUtils.getCLRelatedObject(obj);
        String name = "";
        ClassLoader loader = this.getClass().getClassLoader();
        try
        {
            if (robj != null)
            {
                name = robj.getName();
                loader = robj.getRelatedLoader();
            }
        }
        catch (Exception e)
        {
            name = ""; //get the global
        }

        if ((module == null) && (obj != null))
            module = obj.getClass().getName();
        Logger logger = new Logger(name, module, loader);
        return logger;
    }

    public Logger rlog(Object obj)
    {
        return logger(obj, null);
    }

    public Logger rlog(Object obj, String mod)
    {
        return logger(obj, mod);
    }

    public Logger glog(String mod)
    {
        return logger(null, mod);
    }
}


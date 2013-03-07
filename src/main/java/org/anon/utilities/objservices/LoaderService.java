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
 * File:                org.anon.utilities.objservices.LoaderService
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A service that provides loader related services
 *
 * ************************************************************
 * */

package org.anon.utilities.objservices;

import org.anon.utilities.loader.RelatedLoader;
import org.anon.utilities.loader.CrossLinkRelatedObject;
import org.anon.utilities.exception.CtxException;

public class LoaderService extends ObjectServiceLocator.ObjectService
{
    public LoaderService()
    {
        super();
    }

    public CrossLinkRelatedObject relatedObject()
        throws CtxException
    {
        CrossLinkRelatedObject clr = null;
        ClassLoader ldr = this.getClass().getClassLoader();
        if (ldr instanceof RelatedLoader)
        {
            Object relatedTo = ((RelatedLoader)ldr).relatedTo();
            if (relatedTo != null)
                clr = new CrossLinkRelatedObject(relatedTo);
        }

        return clr;
    }

    public String relatedName()
        throws CtxException
    {
        String name = "owner";
        CrossLinkRelatedObject obj = relatedObject();
        if (obj != null)
            name = obj.getName();
        return name;
    }
}


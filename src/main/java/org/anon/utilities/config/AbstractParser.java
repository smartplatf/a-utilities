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
 * File:                org.anon.utilities.config.AbstractParser
 * Author:              rsankar
 * Revision:            1.0
 * Date:                23-12-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An abstract parser that provides basic functions
 *
 * ************************************************************
 * */

package org.anon.utilities.config;

import java.util.List;
import java.util.ArrayList;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.crosslink.CrossLinkAny;
import org.anon.utilities.exception.CtxException;

public abstract class AbstractParser implements ValueParser
{
    protected AbstractParser()
    {
    }

    protected abstract String separator();
    protected abstract int[] convertIntoInt(String[] vals);

    public int[] asInt(String val)
        throws CtxException
    {
        if ((val == null) || (val.length() <= 0))
            return new int[0];

        String[] vals = asString(val);
        return convertIntoInt(vals);
    }

    public String[] asString(String val)
        throws CtxException
    {
        if ((val == null) || (val.length() <= 0))
            return new String[0];

        String[] strs = val.split(separator());
        for (int i = 0; i < strs.length; i++)
            strs[i] = strs[i].trim();
        return strs;
    }

    public <T extends ParsedObject> List<T> asObject(String value, Class<T> clazz)
        throws CtxException
    {
        try
        {
            List<T> ret = new ArrayList<T>();
            if ((value == null) || (value.length() <= 0))
                return ret;
            String[] vals = asString(value);
            for (int i = 0; i < vals.length; i++)
            {
                CrossLinkAny any = new CrossLinkAny(clazz.getName());
                T obj = (T)any.create();
                obj.populateFrom(vals[i]);
                ret.add(obj);
            }
            return ret;
        }
        catch (Exception e)
        {
            except().rt(this, e, new CtxException.Context("Error parsing: " + value + ":" + clazz, e.getMessage()));
        }

        return null;
    }
}


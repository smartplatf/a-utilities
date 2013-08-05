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
 * File:                org.anon.utilities.gconcurrent.execute.PDescriptor
 * Author:              rsankar
 * Revision:            1.0
 * Date:                16-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A descriptor for parameters
 *
 * ************************************************************
 * */

package org.anon.utilities.gconcurrent.execute;

import java.util.List;
import java.util.ArrayList;

import static org.anon.utilities.services.ServiceLocator.*;

import org.anon.utilities.exception.CtxException;

public class PDescriptor implements java.io.Serializable, PConstants
{
    private ParamType _type;
    private String _attribute;
    private String[] _desc;

    public PDescriptor()
    {
        _type = ParamType.defaultType();
    }

    public ParamType ptype() { return _type; }
    public String attribute() { return _attribute; }
    public String[] links() { return _desc; }

    private static void fillPDescriptor(String[] types, PDescriptor pdesc)
        throws CtxException
    {
        //if it is test, then types.length == 1, so cnt = 0, _type = null, so type = constant, attribute = test
        //if it is config.test, then types.length = 2 cnt = 1, _type = config, attribute = test
        //if it is link.order.orderitem, then types.length = 3, cnt = 2, _type = link, _attribute = orderitem, _desc = [order]
        String attribute = null;
        int cnt = (types.length - 1);
        pdesc._type = ParamType.valueOf(types[0]);
        assertion().assertTrue(((types.length == 1) || (pdesc._type != null)), "Invalida syntax. Cannot recognize " + types[0]);
        if (pdesc._type == null)
        {
            pdesc._type = ParamType.defaultType();
            attribute = types[cnt];
        }
        else
            attribute = types[cnt];

        if (attribute.equals(NULL_STRING))
            attribute = null;

        pdesc._attribute = attribute;
        if (cnt > 2)
        {
            pdesc._desc = new String[types.length - 2];
            for (int i = 1; i < (types.length - 1); i++)
                pdesc._desc[i - 1] = types[i];
        }
    }

    public static List<PDescriptor> parseParamDesc(String parms)
        throws CtxException
    {
        parms = parms.trim();
        assertion().assertTrue(parms.startsWith(PARAM_START_CHAR), "Parameter syntax is not correct. " + parms);
        assertion().assertTrue(parms.endsWith(PARAM_END_CHAR), "Parameter syntax is not correct. " + parms);

        List<PDescriptor> paramret = new ArrayList<PDescriptor>();
        parms = parms.substring(1, (parms.length() - 1));
        String[] sparm = parms.split(PARAM_SEPARATOR);
        for (int i = 0; i < sparm.length; i++)
        {
            PDescriptor pdesc = new PDescriptor();
            sparm[i] = sparm[i].trim();
            String[] types = sparm[i].split(OBJECT_SEPARATOR);
            assertion().assertTrue(types.length > 0, "Parameter syntax is not correct. " + parms);
            fillPDescriptor(types, pdesc);
            paramret.add(pdesc);
        }

        return paramret;
    }
}


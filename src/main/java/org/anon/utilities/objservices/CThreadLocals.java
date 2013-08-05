/**
 * SMART - State Machine ARchiTecture
 *
 * Copyright (C) 2012 Individual contributors as indicated by
 * the @authors tag
 *
 * This file is a part of SMART.
 *
 * SMART is a free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SMART is distributed in the hope that it will be useful,
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
 * File:                org.anon.utilities.objservices.CThreadLocals
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-06-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A set of locals for cthread
 *
 * ************************************************************
 * */

package org.anon.utilities.objservices;

import java.util.Map;
import java.util.HashMap;

public class CThreadLocals
{
    private static final ThreadLocal<Object> THREADLOCALS = new ThreadLocal<Object>();

    public CThreadLocals()
    {
    }

    public static void addToLocals(String name, Object val)
    {
        Map<String, Object> locals = (Map<String, Object>)getLocals();
        if (locals == null)
            locals = new HashMap<String, Object>();

        locals.put(name, val);
        setupLocals(locals);
    }

    public static void setupLocals(Map<String, Object> locals)
    {
        THREADLOCALS.set(locals);
    }

    public static Object getLocals()
    {
        return THREADLOCALS.get();
    }

}


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
 * File:                org.anon.utilities.gconcurrent.execute.ParamType
 * Author:              rsankar
 * Revision:            1.0
 * Date:                16-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A parameter type
 *
 * ************************************************************
 * */

package org.anon.utilities.gconcurrent.execute;

import java.util.Map;
import java.util.HashMap;

public class ParamType implements java.io.Serializable, PConstants
{
    private static final Map<String, ParamType> _types = new HashMap<String, ParamType>();

    static
    {
        //by default constant is defined
        registerProbe(CONSTANT, new ConstantProbe(), true);
    }

    private boolean _explicit;
    private PProbe _probe;

    private ParamType(boolean explicit, PProbe probe)
    {
        _explicit = explicit;
        _probe = probe;
    }

    public PProbe myProbe() { return _probe; }
    public boolean isExplicit() { return _explicit; }

    public static void registerProbe(String name, PProbe probe, boolean explicit)
    {
        _types.put(name, new ParamType(explicit, probe));
    }

    public static ParamType valueOf(String name)
    {
        return _types.get(name);
    }

    public static ParamType defaultType()
    {
        return valueOf(CONSTANT);
    }

    public static ParamType[] types() { return _types.values().toArray(new ParamType[0]); }
}


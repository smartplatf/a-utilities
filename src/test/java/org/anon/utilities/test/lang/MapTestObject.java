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
 * File:                org.anon.utilities.test.lang.MapTestObject
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A test object with maps
 *
 * ************************************************************
 * */

package org.anon.utilities.test.lang;

import java.util.Map;
import java.util.HashMap;

public class MapTestObject
{
    Map<String, String> _simpleMap;
    Map<String, SimpleTestObject> _simpleObject;
    Map<SimpleTestObject, ComplexTestObject> _complexMap;

    public MapTestObject()
    {
        _simpleMap = new HashMap<String, String>();
        for (int i = 0; i < 5; i++)
            _simpleMap.put("SimpleX" + i, "SimpleY" + i);
    }

    public MapTestObject(int complex)
    {
        this();
        _simpleObject = new HashMap<String, SimpleTestObject>();
        for (int i = 0; i < 5; i++)
            _simpleObject.put("SimpleObject" + i, new SimpleTestObject());
        if (complex > 1)
        {
            _complexMap = new HashMap<SimpleTestObject, ComplexTestObject>();
            for (int i = 0; i < 5; i++)
                _complexMap.put(new SimpleTestObject(), new ComplexTestObject());
        }
    }

    public String toString()
    {
        return "_simpleMap:" + _simpleMap +
            ":_simpleMap:" + _simpleObject +
            ":_complexMap:" + _complexMap;
    }
}


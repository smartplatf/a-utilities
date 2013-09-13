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
 * File:                org.anon.utilities.test.reflect.ListTestObject
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * a test object with list
 *
 * ************************************************************
 * */

package org.anon.utilities.test.reflect;

import java.util.List;
import java.util.ArrayList;

public class ListTestObject implements java.io.Serializable
{
    List<SimpleTestObject> _simpleObjs;
    List<ComplexTestObject> _complexObjs;
    List<List<ComplexTestObject>> _listObjs;

    public ListTestObject()
    {
        _simpleObjs = new ArrayList<SimpleTestObject>();

        for (int i = 0; i < 100; i++)
            _simpleObjs.add(new SimpleTestObject(i));
    }

    public ListTestObject(int complex)
        throws Exception
    {
        this();
        _complexObjs = new ArrayList<ComplexTestObject>();
        for (int i = 0; i < 100; i++)
            _complexObjs.add(new ComplexTestObject());

        if (complex > 1)
        {
            _listObjs = new ArrayList<List<ComplexTestObject>>();
            for (int i = 0; i < 100; i++)
            {
                List<ComplexTestObject> tst = new ArrayList<ComplexTestObject>();
                if (complex > 2)
                    tst.add(_complexObjs.get(i));
                else
                    tst.add(new ComplexTestObject());
                _listObjs.add(tst);
            }
        }
    }

    public String toString()
    {
        return "_simpleobjs:" + _simpleObjs + 
            ":_compleObjs:" + _complexObjs + 
            ":_listObjs:" + _listObjs;
    }

}


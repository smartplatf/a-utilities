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
 * File:                org.anon.utilities.test.lang.ComplexTestObject
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An object that contains another object
 *
 * ************************************************************
 * */

package org.anon.utilities.test.lang;

import java.util.Date;

public class ComplexTestObject implements java.io.Serializable
{
    public class InnerClass
    {
        private String tst;
        private transient Date isThisignored;
    }

    Double _double;
    transient Date _dt;
    InnerClass _cls;
    Date _trydt;
    SimpleTestObject _simple;
    SimpleTestObject _simpleDuplicate;

    public ComplexTestObject()
    {
        _simple = new SimpleTestObject();
        _double = new Double(10.123);
        _dt = new Date();
        _trydt = new Date();
        _simpleDuplicate = _simple;
        _cls = new InnerClass();
        _cls.tst = "Trying";
        _cls.isThisignored = new Date();
    }

    public String toString()
    {
        return "_double:" + _double +
            ":_dt:" + _dt +
            ":_trydt:" + _trydt +
            ":_simple:" + _simple +
            "_simpleDuplicate" + _simpleDuplicate;
    }
}


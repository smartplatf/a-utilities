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
 * File:                org.anon.utilities.test.serial.SimpleTestObject
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A simple object to traverse
 *
 * ************************************************************
 * */

package org.anon.utilities.test.serial;

import java.math.BigDecimal;
import java.util.UUID;

public class SimpleTestObject implements java.io.Serializable
{
    int _integer;
    String _string;
    BigDecimal _decimal;
    UUID _uuid;
    Float _float;

    public SimpleTestObject(int i)
    {
        _integer = 10 + i;
        _string = "SimpleTestObject" + i;
        _decimal = new BigDecimal(10 + i);
        _uuid = UUID.randomUUID();
        _float = new Float(10.00 + i);
    }

    public SimpleTestObject(SimpleTestObject obj)
    {
        _integer = obj._integer;
        _string = obj._string;
        _decimal = new BigDecimal(obj._decimal.doubleValue());
        _uuid = UUID.fromString(obj._uuid.toString());
        _float = new Float(obj._float.floatValue());
    }

    public String toString()
    {
        return "_integer:" + _integer +
            ":_string:" + _string +
            ":_decimal:" + _decimal + 
            ":_uuid:" + _uuid + 
            ":_float:" + _float;
    }
}


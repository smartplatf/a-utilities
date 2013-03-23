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
 * File:                org.anon.utilities.config.EnvvarParser
 * Author:              rsankar
 * Revision:            1.0
 * Date:                21-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A parser for environment variables
 *
 * ************************************************************
 * */

package org.anon.utilities.config;

import java.util.List;

import org.anon.utilities.exception.CtxException;

public class EnvvarParser implements ValueParser
{
    public EnvvarParser()
    {
    }

    private String valueString(String value)
    {
        String val = value;
        if (val.startsWith("$"))
        {
            String envvar = val.substring(1);
            val = System.getenv(envvar);
        }
        return val;
    }

    public String[] asString(String value)
        throws CtxException
    {
        String p = valueString(value);
        return new String[] { p };
    }

    public int[] asInt(String value)
        throws CtxException
    {
        return null;
    }

    public <T extends ParsedObject> List<T> asObject(String value, Class<T> clazz)
        throws CtxException
    {
        return null;
    }

    public boolean recognizesFormat(String value)
    {
        return (value.startsWith("$"));
    }
}


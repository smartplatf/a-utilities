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
 * File:                org.anon.utilities.objservices.ValueService
 * Author:              rsankar
 * Revision:            1.0
 * Date:                24-12-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A service that allows to parse values of different standard formats
 *
 * ************************************************************
 * */

package org.anon.utilities.objservices;

import org.anon.utilities.config.ListParser;
import org.anon.utilities.config.RangeParser;
import org.anon.utilities.config.ParamsParser;
import org.anon.utilities.config.ValueParser;
import org.anon.utilities.exception.CtxException;

public class ValueService extends ObjectServiceLocator.ObjectService
{
    enum valuestrategy
    {
        list(new ListParser()),
        range(new RangeParser()),
        params(new ParamsParser());

        private ValueParser _parser;

        private valuestrategy(ValueParser parser)
        {
            _parser = parser;
        }

        ValueParser parser() { return _parser; }
    }

    ValueService()
    {
        super();
    }

    public String[] listAsString(String val)
        throws CtxException
    {
        return valuestrategy.list.parser().asString(val);
    }

    public int[] listAsInt(String val)
        throws CtxException
    {
        return valuestrategy.list.parser().asInt(val);
    }

    public String[] rangeAsString(String val)
        throws CtxException
    {
        return valuestrategy.range.parser().asString(val);
    }

    public int[] rangeAsInt(String val)
        throws CtxException
    {
        return valuestrategy.range.parser().asInt(val);
    }

}


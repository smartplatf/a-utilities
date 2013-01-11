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
 * File:                org.anon.utilities.config.RangeParser
 * Author:              rsankar
 * Revision:            1.0
 * Date:                23-12-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A parser to parse range of values
 *
 * ************************************************************
 * */

package org.anon.utilities.config;

public class RangeParser extends AbstractParser implements Constants
{
    public RangeParser()
    {
    }

    protected String separator() { return RANGE_SEPARATOR; };

    protected int[] convertIntoInt(String[] vals)
    {
        if (vals.length >= 2)
        {
            int start = Integer.parseInt(vals[0]);
            int end = Integer.parseInt(vals[1]);
            int len = (end - start);
            int ret[] = new int[len];
            for (int i = 0; i < len; i++)
                ret[i] = i + start;
            return ret;
        }

        return new int[0];
    }

    public boolean recognizesFormat(String val)
    {
        return ((val.indexOf(RANGE_SEPARATOR) > 0) && (val.indexOf(LIST_SEPARATOR) < 0));
    }
}


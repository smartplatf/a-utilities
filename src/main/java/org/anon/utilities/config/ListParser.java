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
 * File:                org.anon.utilities.config.ListParser
 * Author:              rsankar
 * Revision:            1.0
 * Date:                23-12-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A parser for list values
 *
 * ************************************************************
 * */

package org.anon.utilities.config;


public class ListParser extends AbstractParser implements Constants
{
    public ListParser()
    {
    }

    public String separator() { return LIST_SEPARATOR; }

    public int[] convertIntoInt(String[] vals)
    {
        int len = vals.length;
        int[] ret = new int[vals.length];
        for (int i = 0; i < vals.length; i++)
        {
            ret[i] = Integer.parseInt(vals[i]);
        }

        return ret;
    }

    public boolean recognizesFormat(String val)
    {
        return ((val.indexOf(LIST_SEPARATOR) > 0) && (val.indexOf(RANGE_SEPARATOR) < 0));
    }

}


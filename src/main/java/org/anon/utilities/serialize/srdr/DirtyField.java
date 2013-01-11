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
 * File:                org.anon.utilities.serialize.srdr.DirtyField
 * Author:              rsankar
 * Revision:            1.0
 * Date:                07-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A dirty field that has been modified
 *
 * ************************************************************
 * */

package org.anon.utilities.serialize.srdr;

import java.util.List;

public class DirtyField
{
    private String _name;
    private int _index;
    private List<DirtyField> _subFields;

    public DirtyField(String name)
    {
        _name = name;
        _index = -1;
    }

    public DirtyField(String name, int index)
    {
        _name = name;
        _index = index;
    }

    public void setDirtySubFields(List<DirtyField> flds)
    {
        _subFields = flds;
    }

    public String getFieldName()
    {
        return _name;
    }

    public int getArrayIndex()
    {
        return _index;
    }

    public List<DirtyField> getSubFields()
    {
        return _subFields;
    }

    public String toString()
    {
        return ":name: " + _name +
            ":index:" + _index +
            ":subfields:" + _subFields;
    }
}


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
 * File:                org.anon.utilities.test.reflect.DataLegend
 * Author:              rsankar
 * Revision:            1.0
 * Date:                27-08-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A legend to be embedded in smat object
 *
 * ************************************************************
 * */

package org.anon.utilities.test.reflect;

import java.util.UUID;

public class DataLegend implements java.io.Serializable
{
    private UUID _id;
    private String _group;
    private long _createdOn;
    private long _lastModifiedOn;
    private String _ownedBy;
    private String _lastModifiedBy;

    public DataLegend()
    {
        _id = UUID.randomUUID();
        _createdOn = System.currentTimeMillis();
        _lastModifiedOn = System.currentTimeMillis();
    }
}


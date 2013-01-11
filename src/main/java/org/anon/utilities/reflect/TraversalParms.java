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
 * File:                org.anon.utilities.reflect.TraversalParms
 * Author:              rsankar
 * Revision:            1.0
 * Date:                07-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A set of parameters that helps create traversal types
 *
 * ************************************************************
 * */

package org.anon.utilities.reflect;

import java.util.List;

import org.anon.utilities.utils.RepeaterVariants;

public class TraversalParms implements RepeaterVariants
{
    DataContext _context;
    boolean _modify;
    TVisitor _perculateTo;
    List<ObjectTraversal.myTraverser> _alreadyTraversed;

    public TraversalParms(DataContext ctx, TVisitor visitor, boolean mod, List<ObjectTraversal.myTraverser> at)
    {
        _context = ctx;
        _modify = mod;
        _perculateTo = visitor;
        _alreadyTraversed = at;
    }

    public TraversalParms(DataContext ctx, TVisitor visitor)
    {
        _context = ctx;
        _perculateTo = visitor;
    }
}


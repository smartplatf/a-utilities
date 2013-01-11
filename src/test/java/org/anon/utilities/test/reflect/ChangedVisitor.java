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
 * File:                org.anon.utilities.test.reflect.ChangedVisitor
 * Author:              rsankar
 * Revision:            1.0
 * Date:                06-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A visitor that only visits fields that have changed
 *
 * ************************************************************
 * */

package org.anon.utilities.test.reflect;

import static org.anon.utilities.services.ServiceLocator.*;
import static org.anon.utilities.objservices.ObjectServiceLocator.*;

import org.anon.utilities.reflect.TVisitor;
import org.anon.utilities.reflect.DataContext;
import org.anon.utilities.exception.CtxException;

public class ChangedVisitor implements TVisitor
{
    private TVisitor _visitor;

    public ChangedVisitor(TVisitor visit)
    {
        _visitor = visit;
    }

    public Object visit(DataContext ctx)
        throws CtxException
    {
        Object ret = null;
        Object check = null;
        Object fromobj = null;
        Object[] against = null;
        if (ctx.field() != null)
        {
            check = ctx.fieldVal();
            against = ctx.coFieldVals();
        }
        else
        {
            check = ctx.traversingObject();
            against = ctx.coTraversingObjects();
        }

        if ((against == null) || (against.length <= 0))
            except().te("Need to be traversing two objects atleast to compare");

        fromobj = against[0];
        boolean same = serial().same(check, fromobj);
        if (!same)
            ret = _visitor.visit(ctx);

        return ret;
    }
}


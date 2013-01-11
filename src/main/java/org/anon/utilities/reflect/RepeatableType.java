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
 * File:                org.anon.utilities.reflect.RepeatableType
 * Author:              rsankar
 * Revision:            1.0
 * Date:                08-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A base repeatabletype 
 *
 * ************************************************************
 * */

package org.anon.utilities.reflect;

import java.lang.reflect.Field;
import java.util.List;

import org.anon.utilities.utils.Repeatable;
import org.anon.utilities.utils.RepeaterVariants;
import org.anon.utilities.exception.CtxException;

public abstract class RepeatableType implements RecognizedType
{
    protected TVisitor _perculateTo;
    protected boolean _modify;
    protected DataContext _context;
    protected List<ObjectTraversal.myTraverser> _alreadyTraversed;
    protected boolean _object = true;

    protected RepeatableType()
    {
    }

    protected abstract RepeatableType createme()
        throws CtxException;

    protected abstract boolean handles(Class cls)
        throws CtxException;

    protected abstract Object traverse(Traversal traverse, DataContext ctx, TVisitor visit, boolean mod, List<ObjectTraversal.myTraverser> at, 
            Class cls, Object primary, Object ... cotraverse)
        throws CtxException;

    protected Object traverse(Traversal traverse, DataContext ctx, TVisitor visit, boolean mod, List<ObjectTraversal.myTraverser> at)
        throws CtxException
    {
        return traverse.traverse(ctx, visit, at, mod);
        /*if (_object)
        {
            //ObjectTraversal traverse = new ObjectTraversal(ctx, visit, at, mod);
            return traverse.traverse(ctx, visit, at, mod);
        }
        else
        {
            //ClassTraversal traverse = new ClassTraversal(ctx, visit);
            return traverse.traverse(ctx, visit, at, mod);
        }*/
    }

    public Repeatable repeatMe(RepeaterVariants var)
        throws CtxException
    {
        RepeatableType type = createme();
        /* This is not used, if it is, there should be a Nullpointer exception
        TraversalParms vars = (TraversalParms)var;
        type._modify = vars._modify;
        type._perculateTo = vars._perculateTo;
        type._context = vars._context;
        type._alreadyTraversed = vars._alreadyTraversed;
        */
        return type;
    }

    public boolean handlesType(DataContext ctx)
        throws CtxException
    {
        Class check = ctx.fieldType();
        if (check == null)
            check = ctx.traversingClazz();
        return handles(check);
    }

    public Object visit(Traversal ptraverse, DataContext data, TVisitor visitor, boolean mod, List<ObjectTraversal.myTraverser> at)
        throws CtxException
    {
        Field fld = data.field();
        Object traverse = data.fieldVal();
        if (fld == null)
            traverse = data.traversingObject();

        Object[] cotraverse = data.coFieldVals();
        if (fld == null)
            cotraverse = data.coTraversingObjects();

        if ((traverse == null) && (_object)) //need to change this if we r traversing a null class..??
            return null;

        Class cls = data.fieldType();
        if (fld == null)
            cls = data.traversingClazz();

        if (cotraverse != null)
            return traverse(ptraverse, data, visitor, mod, at, cls, traverse, cotraverse);
        else
            return traverse(ptraverse, data, visitor, mod, at, cls, traverse);
    }

    public Object visit(DataContext data)
        throws CtxException
    {
        //this should not be used.
        return null;
    }
}


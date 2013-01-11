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
 * File:                org.anon.utilities.reflect.ClassTraversal
 * Author:              rsankar
 * Revision:            1.0
 * Date:                29-12-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A traversal of classes
 *
 * ************************************************************
 * */

package org.anon.utilities.reflect;

import java.util.List;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.exception.CtxException;
import org.anon.utilities.utils.Repeatable;
import org.anon.utilities.utils.RepeaterVariants;

public class ClassTraversal implements Traversal
{
    enum recognizedclasses
    {
        object(new ObjectClass()),
        collection(new CollectionClass()),
        map(new MapClass());

        private RecognizedType _visitor;

        private recognizedclasses(RecognizedType visitor)
        {
            _visitor = visitor;
        }

        RecognizedType getVisitor()
            throws CtxException
        {
            return _visitor;
        }

        public static RecognizedType canRecognize(DataContext ctx, ClassTraversal traverse)
            throws CtxException
        {
            RecognizedType rt = null;
            for (recognizedclasses rtype : recognizedclasses.values())
            {
                if (rtype._visitor.handlesType(ctx))
                {
                    //RepeaterVariants parms = new TraversalParms(ctx, traverse._visitor);
                    //rt = (RecognizedType)rtype._visitor.repeatMe(parms); 
                    rt = rtype._visitor;
                    break;
                }
            }

            return rt;
        }
    }

    private Class _traverseClass;
    private TVisitor _visitor;
    private DataContext _context;

    public ClassTraversal(Class cls, CVisitor visit)
        throws CtxException
    {
        _traverseClass = cls;
        _visitor = visit;
        _context = new DataContext(Boolean.TRUE, cls);
    }

    /*
    public ClassTraversal(DataContext ctx, TVisitor visit)
    {
        _traverseClass = ctx.traversingClazz();
        _visitor = visit;
        _context = ctx;
    }
    */

    public Object traverse()
        throws CtxException
    {
        return traverse(_context, _visitor, null, true);
        /*
        RecognizedType visitor = recognizedclasses.canRecognize(_context, this);
        Object obj = null;
        obj = _visitor.visit(_context);
        if ((visitor != null) && (obj != null))
            obj = visitor.visit(_context, _visitor, true, null);

        _context.modify(obj);
        return obj;
        */
    }

    public Object traverse(DataContext ctx, TVisitor visit, List<ObjectTraversal.myTraverser> alreadyTraversed, boolean modify)
        throws CtxException
    {
        RecognizedType visitor = recognizedclasses.canRecognize(ctx, this);
        Object obj = null;
        obj = visit.visit(ctx);
        if ((visitor != null) && (obj != null))
            obj = visitor.visit(this, ctx, visit, true, null);

        ctx.modify(obj);
        return obj;
    }
}


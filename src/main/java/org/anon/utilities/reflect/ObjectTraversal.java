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
 * File:                org.anon.utilities.reflect.ObjectTraversal
 * Author:              rsankar
 * Revision:            1.0
 * Date:                06-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A traversal of all the fields in any object
 *
 * ************************************************************
 * */

package org.anon.utilities.reflect;

import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.exception.CtxException;
import org.anon.utilities.utils.Repeatable;
import org.anon.utilities.utils.RepeaterVariants;

public class ObjectTraversal implements Traversal
{
    static class myTraverser
    {
        private Object _object;

        myTraverser(Object obj)
        {
            _object = obj;
        }

        public boolean equals(Object o1)
        {
            boolean ret = false;
            if (o1 instanceof myTraverser)
            {
                myTraverser m1 = (myTraverser)o1;
                ret = (m1._object == _object);
            }
            return ret;
        }
    }

    enum recognizedtypes
    {
        object(new ObjectType()),
        collection(new CollectionType()),
        map(new MapType());

        private RecognizedType _visitor;

        private recognizedtypes(RecognizedType visitor)
        {
            _visitor = visitor;
        }

        RecognizedType getVisitor()
            throws CtxException
        {
            return _visitor;
        }

        public static RecognizedType canRecognize(DataContext ctx, ObjectTraversal traverse)
            throws CtxException
        {
            RecognizedType rt = null;
            //perf().checkpointHere("start recognize");
            for (recognizedtypes rtype : recognizedtypes.values())
            {
                if (rtype._visitor.handlesType(ctx))
                {
                    //RepeaterVariants parms = new TraversalParms(ctx, traverse._visitor, traverse._modify, traverse._alreadyTraversed);
                    //rt = (RecognizedType)rtype._visitor.repeatMe(parms); 
                    rt = rtype._visitor;
                    break;
                }
            }
            //perf().checkpointHere("end recognize");

            return rt;
        }
    }


    protected TVisitor _visitor;
    protected boolean _modify;
    protected boolean _ignoreTransients;
    protected DataContext _context;
    protected List<myTraverser> _alreadyTraversed;

    public ObjectTraversal(TVisitor visit, Object primary, boolean modify, List<myTraverser> alreadyTraversed, Object ... cotraverse)
        throws CtxException
    {
        this(visit, primary, false, modify, alreadyTraversed, cotraverse);
    }

    public ObjectTraversal(TVisitor visit, Object primary, boolean notrans, boolean modify, List<myTraverser> alreadyTraversed, Object ... cotraverse)
        throws CtxException
    {
        _visitor = visit;
        _ignoreTransients = notrans;
        assertion().assertNotNull(primary, "Cannot traverse a null object");
        if ((cotraverse != null) && (cotraverse.length > 0))
        {
            assertion().assertSameType(primary, cotraverse[0], "Cannot traverse multiple non-homogeneous objects");
            assertion().assertHomogeneous(this, cotraverse, "Cannot traverse multiple non-homogeneous objects");
        }
        //_context = new DataContext(primary, cotraverse);
        setupContext(primary, cotraverse);
        _modify = modify;
        _alreadyTraversed = alreadyTraversed;
        if (alreadyTraversed == null)
            _alreadyTraversed = new ArrayList<myTraverser>();
    }

    protected void setupContext(Object primary, Object ... cotraverse)
        throws CtxException
    {
        if (_ignoreTransients)
            _context = new ITDataContext(primary, cotraverse);
        else
            _context = new DataContext(primary, cotraverse);
    }

    /*public ObjectTraversal(DataContext ctx, TVisitor visit, List<myTraverser> alreadyTraversed, boolean modify)
        throws CtxException
    {
        _visitor = visit;
        _context = ctx;
        _modify = modify;
        _alreadyTraversed = alreadyTraversed;
        if (alreadyTraversed == null)
            _alreadyTraversed = new ArrayList<myTraverser>();
    }*/

    public Object traverse()
        throws CtxException
    {
        return traverse(_context, _visitor, _alreadyTraversed, _modify);
    }

    public Object traverse(DataContext ctx, TVisitor visit, List<myTraverser> alreadyTraversed, boolean modify)
        throws CtxException
    {
        if (ctx.alreadyTraversed(alreadyTraversed))
            return ctx.getObject();

        RecognizedType visitor = recognizedtypes.canRecognize(ctx, this);
        Object obj = null;
        //if it is a recognized type allow the custom visitor to know it also.
        ctx.setBefore();
        obj = visit.visit(ctx);
        if ((visitor != null) && (obj != null))
        {
            ctx.setTraversed(alreadyTraversed);
            obj = visitor.visit(this, ctx, visit, modify, alreadyTraversed);
            ctx.setAfter();
            obj = visit.visit(ctx); //visit after the recognized visitor also??
            //obj = visitor.visit(_context);
        }

        if (modify && (obj != null)) 
            ctx.modify(obj);
        return obj;
    }
}


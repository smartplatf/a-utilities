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
 * File:                org.anon.utilities.reflect.ChangedVisitor
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

package org.anon.utilities.reflect;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import static org.anon.utilities.services.ServiceLocator.*;
import static org.anon.utilities.objservices.ObjectServiceLocator.*;

import org.anon.utilities.serialize.srdr.DirtyField;
import org.anon.utilities.reflect.TVisitor;
import org.anon.utilities.reflect.DataContext;
import org.anon.utilities.exception.CtxException;

public class ChangedVisitor implements TVisitor
{
    private DirtyField _dirty;

    public ChangedVisitor()
    {
    }

    private DirtyField getDirtyField(String path, DirtyField fld)
    {
        if (path.indexOf(".") <= 0)
            return fld;


        if (fld != null)
        {
            String p1 = path.substring(0, path.indexOf("."));
            String p2 = path.substring(path.indexOf(".") + 1);
            List<DirtyField> sub = fld.getSubFields();
            for (int i = 0; (sub != null) && (i < sub.size()); i++)
            {
                DirtyField f = sub.get(i);
                if (f.getFieldName().equals(p1))
                    return getDirtyField(p2, f);
            }
        }

        return null;
    }


    public Object visit(DataContext ctx)
        throws CtxException
    {
        Object ret = null;
        Object check = null;
        Object fromobj = null;
        Object[] against = null;
        String fldname = "";
        if (ctx.field() != null)
        {
            fldname = ctx.field().getName();
            check = ctx.fieldVal();
            against = ctx.coFieldVals();
            //System.out.println("Comparing: " + check + ":" + against.length + ":" + fldname);
        }
        else
        {
            check = ctx.traversingObject();
            against = ctx.coTraversingObjects();
            //System.out.println("Comparing: " + check + ":" + against.length + ":" + fldname + ":");
        }

        if ((against == null) || (against.length <= 0))
            except().te("Need to be traversing two objects atleast to compare");


        if (ctx.before())
        {
            fromobj = against[0];
            boolean same = serial().same(check, fromobj);
            //System.out.println("Comparing: " + check + ":" + fromobj + ":" + same);
            if (!same)
            {
                int ind = -1;
                String t = ctx.getType();
                if ((t != null) && (t.length() > 0))
                    ind = Integer.parseInt(t);
                DirtyField thisfld = new DirtyField(fldname, ind);
                if ((_dirty == null) && (fldname.length() <= 0)) //only on the starting object
                {
                    //System.out.println("Replacing: " + thisfld);
                    _dirty = thisfld;
                }
                else
                {
                    String path = ctx.fieldpath();
                    DirtyField dfld = getDirtyField(path, _dirty);
                    //System.out.println(this + ": Got DirtyField for: " + path + ":" + dfld + ":" + _dirty);
                    assertion().assertNotNull(dfld, "Parent should have been dirty??");
                    List<DirtyField> sflds = dfld.getSubFields();
                    if (sflds == null)
                        sflds = new ArrayList<DirtyField>();
                    sflds.add(thisfld);
                    dfld.setDirtySubFields(sflds);
                }

                if ((check instanceof Collection) || (check instanceof Map))
                    ret = null; //do not go in and check changes within collection or map, shd just save fully?
                else
                    ret = check;
            }
        }

        return ret;
    }

    public List<DirtyField> getDirtyFields()
    {
        System.out.println("The visitor: " + this + ":" + _dirty);
        List<DirtyField> ret = null;
        if (_dirty != null)
            ret = _dirty.getSubFields();

        return ret;
    }
}


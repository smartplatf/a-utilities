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
 * File:                org.anon.utilities.reflect.DataContext
 * Author:              rsankar
 * Revision:            1.0
 * Date:                06-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A data context that contains the data related to the field
 *
 * ************************************************************
 * */

package org.anon.utilities.reflect;

import java.lang.reflect.Field;
import java.util.List;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.exception.CtxException;

public class DataContext
{
    private Field _field;
    private Object[] _fieldVal;
    private Class _traversingClass;
    private Object[] _traversingObject;
    private Object _primaryObject;
    private Object _primaryFieldVal;
    private Object _custom;
    private String _path;
    private boolean _before;
    private boolean _after;
    private String _type;

    public DataContext(Object primary, Object ... traversing)
        throws CtxException
    {
        _path = "";
        if (primary instanceof Boolean)
        {
            _traversingClass = (Class)traversing[0];
        }
        else
        {
            assertion().assertNotNull(primary, "Cannot traverse a null primary");
            if ((traversing != null) && (traversing.length > 0))
                assertion().assertHomogeneous(this, traversing, "Cannot create ctx from non-homogeneous objects");
            _primaryObject = primary;
            _traversingObject = traversing;
            _traversingClass = primary.getClass();
        }
    }

    public DataContext(DataContext pctx, String parPath, Field fld, Object primary, Object ... traversing)
        throws CtxException
    {
        this(primary, traversing);
        try
        {
            assertion().assertNotNull(fld, "Cannot traverse a null field");
            _field = fld;
            String add = "";
            if (parPath.length() > 0)
                add = ".";
            _path = parPath + add + _field.getName();
            fld.setAccessible(true);
            _primaryFieldVal = fld.get(primary);
            _fieldVal = new Object[traversing.length];
            for (int i = 0; (traversing != null) && (i < traversing.length); i++)
                _fieldVal[i] = fld.get(traversing[i]);
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("CreateContext: ", fld.getName()));
        }
    }

    void setParentPath(String parPath) { _path = parPath; }

    void setPrimaryObject(Object obj) { _primaryObject = obj; }

    public Field field() { return _field; }
    public Class fieldType() { if (_field != null) return _field.getType();  else return null;}
    public Object fieldVal() { return _primaryFieldVal; }
    public Object traversingObject() { return _primaryObject; }
    public Class traversingClazz() { return _traversingClass; }

    public Object[] coFieldVals() { return _fieldVal; }
    public Object[] coTraversingObjects() { return _traversingObject; }
    public void modify(Object obj)
        throws CtxException
    {
        try
        {
            //for primitives do not set null.
            if ((obj == null) && (_field != null) && (type().checkPrimitive(_field.getType())))
                return;

            if ((_field != null) && (_primaryObject != null))
                _field.set(_primaryObject, obj);
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("Set:" + _field.getName(), "Setting on: " + _primaryObject));
        }
    }

    Object getObject()
    {
        Object obj = fieldVal();
        if (_field == null)
            obj = traversingObject();
        return obj;
    }

    public boolean alreadyTraversed(List<ObjectTraversal.myTraverser> traversed)
        throws CtxException
    {
        Object obj = getObject();
        if (obj == null)
            return false;

        ObjectTraversal.myTraverser traverse = new ObjectTraversal.myTraverser(obj);
        return (traversed.contains(traverse));
    }

    public void setTraversed(List<ObjectTraversal.myTraverser> traversed)
    {
        Object obj = getObject();
        ObjectTraversal.myTraverser traverse = new ObjectTraversal.myTraverser(obj);
        if (!traversed.contains(traverse))
            traversed.add(traverse);
    }

    public void setCustom(Object val)
    {
        _custom = val;
    }

    public Object getCustom() { return _custom; }
    public String fieldpath() { return _path; }
    public boolean shouldTraverse(Field fld) { return true; }

    public DataContext createContext(DataContext pctx, String parPath, Field fld, Object primary, Object ... cotraverse)
        throws CtxException
    {
        DataContext ctx = new DataContext(pctx, parPath, fld, primary, cotraverse);
        return ctx;
    }

    public void setBefore() { _after = false; _before = true; }
    public boolean before() { return _before; }
    public void setAfter() { _after = true; _before = false;  }
    public boolean after() { return _after; }
    public void setType(String t) { _type = t; }
    public String getType() { return _type; }
}


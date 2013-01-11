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
 * File:                org.anon.utilities.serialize.srdr.StreamComparator
 * Author:              rsankar
 * Revision:            1.0
 * Date:                07-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * a comparator of two SerialStreams
 *
 * ************************************************************
 * */

package org.anon.utilities.serialize.srdr;

import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import java.io.ObjectStreamField;
import java.io.ObjectStreamClass;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.exception.CtxException;

public class StreamComparator
{
    private SerialStreamReader _compare;
    private SerialStreamReader _to;

    private Stack<String> _traverse;

    public StreamComparator(SerialStreamReader compare, SerialStreamReader to)
    {
        _compare = compare;
        _to = to;
        _traverse = new Stack<String>();
    }

    private int pushClassDescriptor(Class fld, int index, List<String> addcls)
        throws CtxException
    {
        int added = 0;
        List<String> clses = _compare.nextClassDescriptor();
        List<String> tclses = _to.nextClassDescriptor();
        if (!clses.equals(tclses))
            except().te(this, "The two streams are not of the same object");

        if ((fld != null) && ((clses.size() > 0) || (index >= 0)))
        {
            Class c = fld;
            while ((c != null) && (!c.equals(Object.class)))
            {
                _traverse.push(c.getName());
                added++;
                c = c.getSuperclass();
            }
        }
        else
        {
            for (int i = 0; i < clses.size(); i++)
            {
                _traverse.push(clses.get(i));
                added++;
                if (addcls != null)
                    addcls.add(clses.get(i));
            }
        }
        return added;
    }

    public List<DirtyField> dirtyFields()
        throws CtxException
    {
        List<DirtyField> dirty = new ArrayList<DirtyField>();
        int pushed = pushClassDescriptor(null, -1, null);
        while (!_traverse.empty())
        {
            List<DirtyField> flds = nextOnStack(-1);
            if (flds != null)
                dirty.addAll(flds);
        }
        return dirty;
    }

    private Class resolveClass(String clsname)
        throws CtxException
    {
        //TODO: need a class resolver
        try
        {
            Class cls = Class.forName(clsname, false, this.getClass().getClassLoader());
            return cls;
        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("StreamComparator.resolveClass", "Exception"));
        }
        return null;
    }

    private List<DirtyField> nextOnStack(int index)
        throws CtxException
    {
        String cls = _traverse.pop();
        return compareClassData(cls, index);
    }

    private void compareAddPrimitive(String fld, char type, int index, List<DirtyField> dirty)
    {
        DirtyField dfld = new DirtyField(fld, index);
        Object comp = _compare.nextPrimitive(type);
        Object to = _to.nextPrimitive(type);
        if (!comp.equals(to))
        {
            dirty.add(dfld);
        }
    }

    private void compareAddString(String fld, int index, List<DirtyField> dirty)
    {
        DirtyField dfld = new DirtyField(fld, index);
        String compare = _compare.stringBlock();
        String to = _to.stringBlock();
        if (!compare.equals(to))
            dirty.add(dfld);
    }

    private void compareAddArray(String fldname, String clsname, int index, List<DirtyField> flds)
        throws CtxException
    {
        DirtyField dfld = new DirtyField(fldname, index);
        List<String> clses = _compare.nextClassDescriptor();
        List<String> tclses = _to.nextClassDescriptor();
        int len = _compare.nextInt();
        int tlen = _to.nextInt();
        boolean different = (len != tlen);
        //assumption here is that the sequence is the same between the two, else it has changed
        int compare = (len < tlen) ? len : tlen;
        String name = clsname.substring(1);
        Class acls = null;
        if (name.charAt(0) == 'L') //object representation so reduuce
        {
            name = name.substring(1, (name.length() - 1));
            name = name.replaceAll("\\/", ".");
            acls = resolveClass(name);
        }
        List<DirtyField> sub = new ArrayList<DirtyField>();
        char empty = '-';
        for (int i = 0; i < compare; i++)
            handleType(empty, name, acls, fldname, i, sub);

        //TODO: need to read the longer one and ignore??
        if (sub.size() > 0)
        {
            dfld.setDirtySubFields(sub);
            different = true;
        }

        if (different)
            flds.add(dfld);
    }

    private void compareAddDate(String fldname, Class fldcls, int index, List<DirtyField> flds)
        throws CtxException
    {
        DirtyField dfld = new DirtyField(fldname, index);
        List<String> clses = _compare.nextClassDescriptor();
        List<String> tclses = _to.nextClassDescriptor();
        if (!clses.equals(tclses))
            except().te("Mismatch in classes?"); //can this just mean a difference?
        long dt = _compare.blockLong();
        long tdt = _to.blockLong();

        if (dt != tdt)
            flds.add(dfld);
    }

    private void compareAddArrayList(String fldname, Class fldcls, int index, List<DirtyField> flds)
        throws CtxException
    {
        DirtyField dfld = new DirtyField(fldname, index);
        List<String> clses = _compare.nextClassDescriptor();
        List<String> tclses = _to.nextClassDescriptor();
        if (!clses.equals(tclses))
            except().te("Mismatch in classes?"); //can this just mean a difference?
        int len = _compare.nextInt();
        int tlen = _to.nextInt();
        boolean different = (len != tlen);
        if (clses.get(0).equals("java.util.ArrayList"))
        {
            //assumption is that we will use the parameterized type?
            int elemlen = _compare.blockInt();
            int telemlen = _to.blockInt();
            int compare = (len < tlen) ? len : tlen;
            List<DirtyField> subflds = new ArrayList<DirtyField>();
            Class itemcls = null;
            for (int i = 0; i < compare; i++)
            {
                String clsname = handleType('L', "", itemcls, fldname, i, subflds);
                if ((clsname != null) && (clsname.length() > 0))
                    itemcls = resolveClass(clsname);
            }
            if (subflds.size() > 0)
            {
                dfld.setDirtySubFields(subflds);
                different = true;
            }
        }
        else
        {
            //need to read this?
        }

        if (different)
            flds.add(dfld);
    }

    private String compareAddObject(String fldname, Class fldcls, int index, List<DirtyField> flds)
        throws CtxException
    {
        DirtyField dfld = new DirtyField(fldname, index);
        List<String> addcls = new ArrayList<String>();
        int added = pushClassDescriptor(fldcls, index, addcls);
        List<DirtyField> dflds = new ArrayList<DirtyField>();
        for (int i = 0; i < added; i++)
        {
            List<DirtyField> subflds = nextOnStack(index);
            if (subflds.size() > 0)
                dflds.addAll(subflds);
        }

        if (dflds.size() > 0)
        {
            dfld.setDirtySubFields(dflds);
            flds.add(dfld);
        }

        if (addcls.size() > 0)
            return addcls.get(0);
        else
            return "";
    }

    private String handleType(char type, String clsname, Class fldcls, String fldname, int index, List<DirtyField> flds)
        throws CtxException
    {
        String cls = "";
        boolean isPrimitive = false;
        boolean isArray = (type == '[');
        String chkPrimitive = clsname;
        if (type != '-')
            chkPrimitive = new String(new char[] { type });
        else
            type = clsname.charAt(0);
        isPrimitive = BytesStreamReader.isPrimitive(chkPrimitive);
        if (isPrimitive)
        {
            compareAddPrimitive(fldname, type, index, flds);
        }
        else if ((fldcls != null) && (fldcls.equals(String.class)))
        {
            compareAddString(fldname, index, flds);
        }
        else if (isArray)
        {
            compareAddArray(fldname, clsname, index, flds);
        }
        else if ((fldcls != null) && (List.class.isAssignableFrom(fldcls)))
        {
            compareAddArrayList(fldname, fldcls, index, flds);
        }
        else if ((fldcls != null) && (java.util.Date.class.isAssignableFrom(fldcls)))
        {
            compareAddDate(fldname, fldcls, index, flds);
        }
        else
        {
            cls = compareAddObject(fldname, fldcls, index, flds);
        }
        return cls;
    }

    private List<DirtyField> compareClassData(String clsname, int index)
        throws CtxException
    {
        List<DirtyField> dirty = new ArrayList<DirtyField>();
        Class cls = resolveClass(clsname);
        ObjectStreamClass ostr = ObjectStreamClass.lookup(cls);
        ObjectStreamField[] flds = ostr.getFields();
        for (int i = 0; i < flds.length; i++)
        {
            handleType(flds[i].getTypeCode(), flds[i].getTypeString(), flds[i].getType(), flds[i].getName(), index, dirty);
        }
        return dirty;
    }
}


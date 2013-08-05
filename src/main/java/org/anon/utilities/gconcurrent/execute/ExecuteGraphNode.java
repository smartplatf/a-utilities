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
 * File:                org.anon.utilities.gconcurrent.execute.ExecuteGraphNode
 * Author:              rsankar
 * Revision:            1.0
 * Date:                16-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * An execution of the graph node
 *
 * ************************************************************
 * */

package org.anon.utilities.gconcurrent.execute;

import java.util.List;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.InvocationTargetException;

import static org.anon.utilities.services.ServiceLocator.*;
import static org.anon.utilities.objservices.ObjectServiceLocator.*;

import org.anon.utilities.gconcurrent.GraphRuntimeNode;
import org.anon.utilities.exception.CtxException;

public abstract class ExecuteGraphNode
{
    protected GraphContext _context;
    protected GraphRuntimeNode _grtNode;
    private Method _method;
    private Class _clazz;
    private Parameters _parameters;

    protected ExecuteGraphNode(GraphRuntimeNode nde, ProbeParms parms)
    {
        _context = parms.context();
        _grtNode = nde;
        _method = nde.method();
        _clazz = nde.clazz();
        _parameters = new Parameters(_grtNode.descriptors(), parms);
    }

    protected abstract Object runtimeObject(Class cls)
        throws CtxException;

    protected abstract boolean successOrFailure(Object ret)
        throws CtxException;

    protected abstract void done()
        throws CtxException;

    protected Object[] parametersFor()
        throws CtxException
    {
        Class[] clsparms = _method.getParameterTypes();
        Type[] genericparms = _method.getGenericParameterTypes();
        int plen = clsparms.length;
        Object[] parms = new Object[plen];
        for (int i = 0; i < plen; i++)
        {
            parms[i] = _parameters.parameterFor(clsparms[i], genericparms[i], i);
        }

        return parms;
    }

    private Object invokeExecute(Method mthd1, Object obj1, Object[] parms1)
        throws Exception
    {
        Object obj = obj1;
        Method mthd = mthd1;
        Object[] parms = parms1;
        Object ret = mthd.invoke(obj, parms);
        return ret;
    }


    public Object runGraphNode()
        throws CtxException
    {
        Object runWith = runtimeObject(_clazz);
        Object[] parms = parametersFor();
        try
        {
            control().disableSystemExit();
            Object ret = invokeExecute(_method, runWith, parms);
            successOrFailure(ret);
        }
        catch (InvocationTargetException ie)
        {
            String msg = ie.getMessage();
            if (ie.getCause() != null)
                msg = ie.getCause().getMessage();
            except().rt(ie, new CtxException.Context("Error: ", msg));

        }
        catch (Exception e)
        {
            except().rt(e, new CtxException.Context("ExecuteGraphNode.runGraphNode", e.getMessage()));
        }
        finally
        {
            control().enableSystemExit();
            done();
            _parameters.release(parms);
            _context.nodeDone();
        }
        return runWith;
    }
}


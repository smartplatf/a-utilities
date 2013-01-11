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
 * File:                org.anon.utilities.services.AssertionService
 * Author:              rsankar
 * Revision:            1.0
 * Date:                06-08-2012
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A service to assert. If not asserted, it throws exception
 *
 * ************************************************************
 * */

package org.anon.utilities.services;

import org.apache.commons.lang.ClassUtils;

import static org.anon.utilities.services.ServiceLocator.*;
import org.anon.utilities.exception.CtxException;

public class AssertionService extends ServiceLocator.Service
{
    protected AssertionService()
    {
        super();
    }

    public void assertTrue(boolean cond, String msg)
        throws CtxException
    {
        if (!cond)
            except().te("AssertionService.assertTrue", msg);
    }

    public void assertTrue(boolean cond, Object obj, String msg)
        throws CtxException
    {
        if (!cond)
            except().te(obj, "AssertionService.assertTrue", msg);
    }

    public void assertFalse(boolean cond, String msg)
        throws CtxException
    {
        if (cond)
            except().te("AssertionService.assertFalse", msg);
    }

    public void assertFalse(boolean cond, Object obj, String msg)
        throws CtxException
    {
        if (cond)
            except().te(obj, "AssertionService.assertFalse", msg);
    }

    public void assertEquals(Object obj1, Object obj2, String msg)
        throws CtxException
    {
        if ((obj1 != null) && (obj2 != null) && (!obj1.equals(obj2)))
            except().te(obj1, "AssertionService.assertEquals", msg);
        if (((obj1 == null) && (obj2 != null)) || ((obj1 != null) && (obj2 == null)))
            except().te(obj1, "AssertionService.assertEquals", msg);
    }

    public void assertHomogeneous(Object[] aobj, String msg)
        throws CtxException
    {
        assertTrue((aobj != null), msg);
        Class prevcls = null;
        for (int i = 0; i < aobj.length; i++)
        {
            assertTrue((aobj[i] != null), msg);
            Class cls = aobj[i].getClass();
            if ((prevcls != null) && (!prevcls.equals(cls)))
                except().te("AssertionService.assertHomogeneous", msg);
            prevcls = cls;
        }
    }

    public void assertHomogeneous(Object obj, Object[] aobj, String msg)
        throws CtxException
    {
        assertTrue((aobj != null), obj, msg);
        Class prevcls = null;
        for (int i = 0; i < aobj.length; i++)
        {
            assertTrue((aobj[i] != null), obj, msg);
            Class cls = aobj[i].getClass();
            if ((prevcls != null) && (!prevcls.equals(cls)))
                except().te(obj, "AssertionService.assertHomogeneous", msg);
            prevcls = cls;
        }
    }

    public void assertNotNull(Object chk, String msg)
        throws CtxException
    {
        if (chk == null)
            except().te("AssertionService.assertNotNull", msg);
    }

    public void assertNotNull(Object chk, Object obj, String msg)
        throws CtxException
    {
        if (chk == null)
            except().te(obj, "AssertionService.assertNotNull", msg);
    }

    public void assertNotNull(Object[] objs, String msg)
        throws CtxException
    {
        assertTrue((objs != null), msg);
        for (int i = 0; i < objs.length; i++)
        {
            if (objs[i] == null)
                except().te("AssertionService.assertNotNull", msg);
        }
    }

    public void assertNotNull(Object obj, Object[] objs, String msg)
        throws CtxException
    {
        assertTrue((objs != null), obj, msg);
        for (int i = 0; i < objs.length; i++)
        {
            if (objs[i] == null)
                except().te(obj, "AssertionService.assertNotNull", msg);
        }
    }

    public void assertSameType(Object obj1, Object obj2, String msg)
        throws CtxException
    {
        if ((obj1 != null) && (obj2 != null) && (!obj1.getClass().equals(obj2.getClass())))
            except().te(obj1, "AssertionService.assertSameType", msg);

    }

    public void assertType(Object obj, Class cls, String msg)
        throws CtxException
    {
        if ((obj != null) && (!obj.getClass().equals(cls)))
            except().te(obj, "AssertionService.assertType", msg);
    }

    public void assertAssignable(Class cls, Class tocls, String msg)
        throws CtxException
    {
        if (!ClassUtils.isAssignable(cls, tocls, true))
            except().te("AsssertionService.assertAssignable", msg);
    }

    public void assertAssignable(Class cls, Class tocls, Object obj, String msg)
        throws CtxException
    {
        if (!ClassUtils.isAssignable(cls, tocls, true))
            except().te(obj, "AsssertionService.assertAssignable", msg);
    }
}


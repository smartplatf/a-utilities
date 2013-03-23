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
 * File:                org.anon.utilities.lang.json.JSONTranslator
 * Author:              rsankar
 * Revision:            1.0
 * Date:                09-01-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A translator for json related transfer
 *
 * ************************************************************
 * */

package org.anon.utilities.lang.json;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.PrintWriter;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import static org.anon.utilities.services.ServiceLocator.*;

import org.anon.utilities.lang.Translator;
import org.anon.utilities.reflect.ObjectTraversal;
import org.anon.utilities.reflect.ClassTraversal;
import org.anon.utilities.exception.CtxException;

public class JSONTranslator implements Translator
{
    public JSONTranslator()
    {
    }

    public void translate(Object obj, OutputStream ostr)
        throws CtxException
    {
        JSONCreator creator = new JSONCreator();
        ObjectTraversal traversal = new ObjectTraversal(creator, obj, true, false, null);
        traversal.traverse();
        JSON j = creator.json();
        PrintWriter writer = new PrintWriter(ostr);
        j.write(writer);
        writer.flush();
    }

    public <T> T translate(InputStream istr, Class<T> type)
        throws CtxException
    {
        StringBuffer buff = io().readStream(istr);
        JSON j = JSONSerializer.toJSON(buff.toString());
        System.out.println("Got JSON string: " + buff.toString() + ":" + j);
        ObjectCreator creator = new ObjectCreator(j);
        ClassTraversal ctraversal = new ClassTraversal(type, creator);
        Object ret = ctraversal.traverse();
        return type.cast(ret);
    }
}


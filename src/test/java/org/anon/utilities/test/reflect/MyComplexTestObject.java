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
 * File:                org.anon.utilities.test.reflect.MyComplexTestObject
 * Author:              vjaasti
 * Revision:            1.0
 * Date:                Mar 21, 2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * <Purpose>
 *
 * ************************************************************
 * */

package org.anon.utilities.test.reflect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyComplexTestObject implements Serializable {
	Double _double;
    Date _dt;
    long _long;
    SimpleTestObject _simple;
    List<SimpleTestObject> _simpleObjList;
    Set<SimpleTestObject> _simpleObjSet;
    
   public MyComplexTestObject()
   {
	   _simple = new SimpleTestObject();
       _double = new Double(10.123);
       _dt = new Date();
       _long = 1L;
       
	   _simpleObjList = new ArrayList<SimpleTestObject>();
	   _simpleObjList.add(new SimpleTestObject());
	   _simpleObjList.add(new SimpleTestObject());
	   
	   _simpleObjSet = new HashSet<SimpleTestObject>();
	   _simpleObjSet.add(new SimpleTestObject());
	   _simpleObjSet.add(new SimpleTestObject());
   }
   
   @Override
   public String toString()
   {
       return "_double:" + _double +
           ":_dt:" + _dt +
           ":_long:"+_long +
           ":_simple:" + _simple +
           "_simpleObjeList:" + getString(_simpleObjList) +
           " _simpleObjSet:" + getString(_simpleObjSet);
   }
   
   private String getString( Collection<SimpleTestObject>  list){
	   if (list == null) return null;
	   StringBuffer str = new StringBuffer();
	   str.append("[");
	   for(SimpleTestObject o : list)
		   str.append(o+";");
	   str.append("]");
	   return str.toString();
   }
}

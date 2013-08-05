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
 * File:                org.anon.utilities.crypt.Constants
 * Author:              rsankar
 * Revision:            1.0
 * Date:                31-05-2013
 *
 * ************************************************************
 * REVISIONS
 * ************************************************************
 * A set of constants for encryption
 *
 * ************************************************************
 * */

package org.anon.utilities.crypt;

public interface Constants
{
    public static int KEY_LENGTH = 256;
    public static int ITERATION_COUNT = 50;
    public static String ALGORITHM = "PBEWithSHA256And256BitAES-CBC-BC";
    public static String ENCRYPTION = "AES";
}


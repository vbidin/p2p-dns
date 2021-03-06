/**
 * $Id: GUID.java,v 1.2 2005/11/07 16:56:40 rschmidt Exp $
 *
 * Copyright (c) 2002 The P-Grid Team,
 *                    All Rights Reserved.
 *
 * This file is part of the P-Grid package.
 * P-Grid homepage: http://www.p-grid.org/
 *
 * The P-Grid package is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License (GPL) as
 * published by the Free Software Foundation; either version 2 of
 * the License, or (at your option) any later version.
 *
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; see the file LICENSE.
 * If not you can find the GPL at http://www.gnu.org/copyleft/gpl.html
 */

package pgrid.util.guid;

import java.io.Serializable;

/**
 * This class stores unique IDs and provides some basic access methods.
 *
 * @author <a href="mailto:Roman Schmidt <Roman.Schmidt@epfl.ch>">Roman Schmidt</a>
 * @version 1.0.0
 */
public class GUID implements Comparable, Serializable {

	/**
	 * The default length of a GUID.
	 */
	public static final int LENGTH = 20;

	/**
	 * the unique ID.
	 */
	protected byte[] theId = null;

	/**
	 * The string representing the id.
	 */
	protected String theIdString = null;

	/**
	 * Construct a new GUID.
	 * This constructor should only be used to create GUIDs, which were never used by other objects again.
	 */
	public GUID() {
		theId = generate();
	}

	/**
	 * Constructs an unique ID object from the given string.
	 * This constructor should only be used to create GUIDs, which were never used by other objects again.
	 *
	 * @param v a string representing a GUID
	 */
	public GUID(String v) {
		int l = v.length();
		theId = new byte[(l + 1) / 2];
		try {
			int j = 0;
			for (int i = 0; i < l; i += 2) {
				String str = v.substring(i, i + 2);
				theId[j++] = (Integer.valueOf(str, 16)).byteValue();
			}
		} catch (StringIndexOutOfBoundsException e) {
			System.err.println("'" + v + "' is not a valid unique id.");
		} catch (NumberFormatException e) {
			System.err.println("'" + v + "' is not a hexadecimal number.");
		}
	}

	/**
	 * Constructs an unique byte array.
	 *
	 * @return the generated byte array.
	 */
	private byte[] generate() {
		return GUIDGenerator.sharedInstance().generate();
	}

	/**
	 * Returns the value of the unique ID.
	 *
	 * @return returns a byte array the represents the unique ID.
	 */
	public byte[] getBytes() {
		return theId;
	}

	/**
	 * Sets the value of the unique ID.
	 *
	 * @param newId the new value of the unique ID.
	 */
	public void setId(byte[] newId) {
		this.theId = newId;
		theIdString = null;
	}

	/**
	 * Returns a unique string representation of this unique ID. The byte array
	 * that represents the unique ID is stepped through byte per byte and each
	 * byte is converted into its hex representation (padded with leading
	 * zeros).
	 *
	 * @return the unique string representation of the unique ID.
	 */
	public String toString() {
		if (theIdString != null)
			return theIdString;
		StringBuffer suid = new StringBuffer();
		int range =
				Math.abs((new Byte(Byte.MAX_VALUE)).intValue()) +
				Math.abs((new Byte(Byte.MIN_VALUE)).intValue()) + 1;
		int dec;
		StringBuffer hex;
		int maxdigits = (Integer.toHexString(range - 1)).length();

		if ((theId == null) || (theId.length == 0)) {
			return "null";
		}

		for (int i = 0; i < theId.length; i++) {
			dec = new Byte(theId[i]).intValue();
			if (dec < 0) {
				dec = range + dec;
			}
			hex = new StringBuffer(Integer.toHexString(dec));
			for (int j = hex.length(); j < maxdigits; j++) {
				suid.append('0');
			}
			suid.append(hex);
		}
		theIdString = suid.toString().toUpperCase();
		return theIdString;
	}

	/**
	 * Compares this object with the specified object for order. Returns a negative integer, zero, or a positive integer
	 * as this object is less than, equal to, or greater than the specified object.
	 *
	 * @param obj the Object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the
	 *         specified object.
	 */
	public int compareTo(Object obj) {
		return toString().compareTo(((GUID)obj).toString());
	}

	/**
	 * Compares a unique ID's value with this unique ID's value for
	 * equality. Does a simple byte compare.
	 *
	 * @param idB the unique ID to compare with this unique ID.
	 * @return true if the unique IDs are equal, false otherwise.
	 */
	private boolean isEqual(byte[] idB) {

		// it's the same object or both are null
		if (theId == idB) {
			return true;
		}
		// one is null the other isn't
		if ((theId == null) || (idB == null)) {
			return false;
		}
		// are the lengths identical
		int l = theId.length;
		if (l != idB.length) {
			return false;
		}
		// do a bytewise comparison otherwise
		for (int i = 0; i < l; i++) {
			if (theId[i] != idB[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Compares a unique ID with this unique ID for equality. Does a
	 * simple byte compare.
	 *
	 * @param idB the unique ID to compare with this unique ID.
	 * @return true if the unique IDs are equal, false otherwise.
	 */
	private boolean isEqual(GUID idB) {
		if (this == idB) {
			return true;
		}
		return this.isEqual(idB.getBytes());
	}

	/**
	 * Compares two unique IDs for equality. The result is <code>true</code> if
	 * and only if the argument is not null and is a <code>GUID</code> object
	 * that represents the same unique id as this object.
	 *
	 * @param obj the object to compare with.
	 * @return <code>true</code> if the objects are the same; false otherwise.
	 */
	public boolean equals(GUID obj) {
		return this.isEqual(obj);
	}

	/**
	 * Compares two unique IDs for equality. The result is <code>true</code> if
	 * and only if the argument is not null and is a <code>GUID</code> object
	 * that represents the same unique id as this object.
	 *
	 * @param obj the object to compare with.
	 * @return <code>true</code> if the objects are the same; false otherwise.
	 */
	public boolean equals(Object obj) {
		return this.isEqual((GUID)obj);
	}

	/**
	 * Returns a hash code value for this unique id based on its value.
	 *
	 * @return a hash code value for this unique id.
	 */
	public int hashCode() {
		return (this.toString()).hashCode();
	}

	/**
	 * Returns the size of this GUID.
	 *
	 * @return the size of this GUID.
	 */
	public int getSize() {
		return LENGTH;
	}

}
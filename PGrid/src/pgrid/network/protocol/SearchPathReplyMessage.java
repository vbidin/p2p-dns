/**
 * $Id: SearchPathReplyMessage.java,v 1.2 2005/11/07 16:56:38 rschmidt Exp $
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

package pgrid.network.protocol;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import p2p.basic.GUID;
import pgrid.Constants;
import pgrid.XMLizable;
import pgrid.core.storage.DBDataTable;
import pgrid.core.storage.DataTable;
import pgrid.core.RoutingTable;
import pgrid.core.XMLRoutingTable;
import pgrid.interfaces.basic.PGridP2P;

import java.io.UnsupportedEncodingException;

/**
 * This class represents a search path reply message.
 *
 * @author @author <a href="mailto:Roman Schmidt <Roman.Schmidt@epfl.ch>">Roman Schmidt</a>
 * @version 1.0.0
 */
public class SearchPathReplyMessage extends pgrid.util.LexicalDefaultHandler implements PGridMessage, XMLizable {

	/**
	 * The search path reply code Path Changed.
	 */
	public static final int CODE_PATH_CHANGED = 400;

	/**
	 * The search path reply code OK.
	 */
	public static final int CODE_OK = 200;

	/**
	 * A part of the XML string.
	 */
	public static final String XML_SEARCH_PATH_REPLY = "SearchPathReply";

	/**
	 * A part of the XML string.
	 */
	private static final String XML_SEARCH_PATH_REPLY_CODE = "Code";

	/**
	 * A part of the XML string.
	 */
	private static final String XML_SEARCH_PATH_REPLY_GUID = "GUID";

	/**
	 * A part of the XML string.
	 */
	private static final String XML_SEARCH_PATH_REPLY_PATH = "Path";

	/**
	 * The Query Hit reply code.
	 */
	private int mCode = -1;

	/**
	 * The data table.
	 */
	private DBDataTable mDataTable = null;

	/**
	 * The message GUID.
	 */
	private GUID mGUID = null;

	/**
	 * The message header.
	 */
	private MessageHeader mHeader = null;

	/**
	 * The path of the host.
	 */
	private String mPath = null;

	/**
	 * The routing table.
	 */
	private XMLRoutingTable mRoutingTable = null;

	/**
	 * The temporary variable during parsing.
	 */
	private XMLizable mParsedObject = null;

	/**
	 * The data table as XML.
	 */
	private XMLDataTable mXMLDataTable = null;

	/**
	 * Creates a search path reply message with the given header.
	 *
	 * @param header the message header.
	 */
	public SearchPathReplyMessage(MessageHeader header) {
		mHeader = header;
	}

	/**
	 * Creates a new search path reply message with given values.
	 *
	 * @param guid the GUID of the Query Reply.
	 */
	public SearchPathReplyMessage(GUID guid) {
		mHeader = new MessageHeader(Constants.PGRID_PROTOCOL_VERSION, -1, PGridP2P.sharedInstance().getLocalHost());
		mGUID = guid;
		mCode = CODE_PATH_CHANGED;
	}

	/**
	 * Creates a new search path reply message with given values.
	 *
	 * @param guid         the GUID of the Query Reply.
	 * @param path         the path of the host.
	 * @param routingTable the routing table of the host.
	 * @param dataTable    the dataTable items of the host.
	 */
	public SearchPathReplyMessage(GUID guid, String path, XMLRoutingTable routingTable, DBDataTable dataTable) {
		mHeader = new MessageHeader(Constants.PGRID_PROTOCOL_VERSION, -1, PGridP2P.sharedInstance().getLocalHost());
		mGUID = guid;
		mPath = path;
		mRoutingTable = routingTable;
		mDataTable = dataTable;
		mXMLDataTable = new XMLDataTable(dataTable);
		mCode = CODE_OK;
	}

	/**
	 * Returns the pong message as array of bytes.
	 *
	 * @return the message bytes.
	 */
	public byte[] getBytes() {
		byte[] bytes=null;

		try {
			bytes = toXMLString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		return bytes;
	}

	/**
	 * Returns the message code.
	 *
	 * @return the code.
	 */
	public int getCode() {
		return mCode;
	}

	/**
	 * Returns the data items.
	 *
	 * @return the data items.
	 */
	public DataTable getDataTable() {
		return mDataTable;
	}

	/**
	 * Sets the data items.
	 *
	 * @param dataTable the data items.
	 */
	public void setDataItems(DBDataTable dataTable) {
		mDataTable = dataTable;
	}

	/**
	 * Returns a desricptor for the type of message.
	 *
	 * @return the message descriptor.
	 */
	public int getDesc() {
		return PGridMessage.DESC_SEARCH_PATH_REPLY;
	}

	/**
	 * Returns the representation string for a descriptor of a message.
	 *
	 * @return the message descriptor string.
	 */
	public String getDescString() {
		return PGridMessage.DESC_SEARCH_PATH_REPLY_STRING;
	}

	/**
	 * Returns the message GUID.
	 *
	 * @return the message GUID.
	 */
	public GUID getGUID() {
		return mGUID;
	}

	/**
	 * Get the message content.
	 *
	 * @return a binary representation of the message
	 */
	public byte[] getData() {
		return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
	}

	/**
	 * Returns the message header.
	 *
	 * @return the header.
	 */
	public MessageHeader getHeader() {
		return mHeader;
	}

	/**
	 * Returns the host path.
	 *
	 * @return the host path.
	 */
	public String getPath() {
		return mPath;
	}

	/**
	 * Sets the host path.
	 *
	 * @param path the host path.
	 */
	public void setPath(String path) {
		mPath = path;
	}

	/**
	 * Returns the routing table.
	 *
	 * @return the routing table.
	 */
	public RoutingTable getRoutingTable() {
		return mRoutingTable;
	}

	/**
	 * Sets the routing table.
	 *
	 * @param routingTable the routing table.
	 */
	public void setRoutingTable(XMLRoutingTable routingTable) {
		mRoutingTable = routingTable;
	}

	/**
	 * Returns the message size.
	 *
	 * @return the message size.
	 */
	public int getSize() {
		return toXMLString().length();
	}

	/**
	 * Tests if this query hit message is valid.
	 *
	 * @return <code>true</code> if valid.
	 */
	public boolean isValid() {
		if (mHeader == null) {
			return false;
		} else {
			if (!mHeader.isValid()) {
				return false;
			}
		}
		if (mGUID == null)
			return false;
		if (mCode == -1)
			return false;
		if ((mCode == CODE_OK) && (mPath == null))
			return false;
		if ((mCode != CODE_OK) && (mCode != CODE_PATH_CHANGED))
			return false;
		return true;
	}

	/**
	 * Sets the message header.
	 *
	 * @param header the header.
	 */
	public void setHeader(MessageHeader header) {
		mHeader = header;
	}

	/**
	 * The Parser will call this method to report each chunk of character data. SAX parsers may return all contiguous
	 * character data in a single chunk, or they may split it into several chunks; however, all of the characters in any
	 * single event must come from the same external entity so that the Locator provides useful information.
	 *
	 * @param ch     the characters from the XML document.
	 * @param start  the start position in the array.
	 * @param length the number of characters to read from the array.
	 * @throws SAXException any SAX exception, possibly wrapping another exception.
	 */
	public synchronized void characters(char[] ch, int start, int length) throws SAXException {
		if (mParsedObject != null) {
			mParsedObject.characters(ch, start, length);
		}
	}

	/**
	 * The SAX parser will invoke this method at the end of every element in the XML document; there will be a
	 * corresponding startElement event for every endElement event (even when the element is empty).
	 *
	 * @param uri   the Namespace URI.
	 * @param lName the local name (without prefix), or the empty string if Namespace processing is not being performed.
	 * @param qName the qualified name (with prefix), or the empty string if qualified names are not available.
	 * @throws SAXException any SAX exception, possibly wrapping another exception.
	 */
	public synchronized void endElement(String uri, String lName, String qName) throws SAXException {
		if (qName.equals(XMLRoutingTable.XML_ROUTING_TABLE)) {
			mParsedObject.endElement(uri, lName, qName);
			mParsedObject = null;
		} else if (qName.equals(XMLDataTable.XML_DATA_TABLE)) {
			mParsedObject.endElement(uri, lName, qName);
			mParsedObject = null;
		} else if (mParsedObject != null) {
			mParsedObject.endElement(uri, lName, qName);
		}
	}

	/**
	 * The Parser will invoke this method at the beginning of every element in the XML document; there will be a
	 * corresponding endElement event for every startElement event (even when the element is empty). All of the element's
	 * content will be reported, in order, before the corresponding endElement event.
	 *
	 * @param uri   the Namespace URI.
	 * @param lName the local name (without prefix), or the empty string if Namespace processing is not being performed.
	 * @param qName the qualified name (with prefix), or the empty string if qualified names are not available.
	 * @param attrs the attributes attached to the element. If there are no attributes, it shall be an empty Attributes
	 *              object.
	 * @throws SAXException any SAX exception, possibly wrapping another exception.
	 */
	public synchronized void startElement(String uri, String lName, String qName, Attributes attrs) throws SAXException {
		if (qName.equals(XML_SEARCH_PATH_REPLY)) {
			mGUID = pgrid.GUID.getGUID(attrs.getValue(XML_SEARCH_PATH_REPLY_GUID));
			mPath = attrs.getValue(XML_SEARCH_PATH_REPLY_PATH);
			mCode = Integer.parseInt(attrs.getValue(XML_SEARCH_PATH_REPLY_CODE));
		} else if (qName.equals(XMLRoutingTable.XML_ROUTING_TABLE)) {
			mRoutingTable = new XMLRoutingTable();
			mRoutingTable.startElement(uri, lName, qName, attrs);
			mParsedObject = mRoutingTable;
		} else if (qName.equals(XMLDataTable.XML_DATA_TABLE)) {
			mDataTable = new DBDataTable(mHeader.getHost());
			mXMLDataTable = new XMLDataTable(mDataTable);
			mXMLDataTable.startElement(uri, lName, qName, attrs);
			mParsedObject = mXMLDataTable;
		} else if (mParsedObject != null) {
			mParsedObject.startElement(uri, lName, qName, attrs);
		}
	}

	/**
	 * Returns a string represantation of this message.
	 *
	 * @return a string represantation of this message.
	 */
	public String toXMLString() {
		return toXMLString(XML_TAB, XML_NEW_LINE);
	}

	/**
	 * Returns the XML representation of this object.
	 *
	 * @param prefix  the XML prefix before each element in a new line.
	 * @param newLine the new line string.
	 * @return the XML string.
	 */
	public String toXMLString(String prefix, String newLine) {
		StringBuffer strBuff;
		if (mDataTable == null)
			strBuff = new StringBuffer(100);
		else
			strBuff = new StringBuffer(mDataTable.count() * 100);
		strBuff.append(prefix + XML_ELEMENT_OPEN + XML_SEARCH_PATH_REPLY); // {prefix}<SearchPathReply
		strBuff.append(XML_SPACE + XML_SEARCH_PATH_REPLY_GUID + XML_ATTR_OPEN + mGUID.toString() + XML_ATTR_CLOSE); // _GUID="GUID"
		strBuff.append(XML_SPACE + XML_SEARCH_PATH_REPLY_PATH + XML_ATTR_OPEN + mPath + XML_ATTR_CLOSE); // _Path="PATH"
		strBuff.append(XML_SPACE + XML_SEARCH_PATH_REPLY_CODE + XML_ATTR_OPEN + mCode + XML_ATTR_CLOSE + XML_ELEMENT_CLOSE + newLine); // _Code="CODE">{newLine}

		// routing table
		if ((mCode == CODE_OK) && (mRoutingTable != null))
			strBuff.append(mRoutingTable.toXMLString(prefix + XML_TAB, newLine, true, true, true));

		// data table
		if ((mCode == CODE_OK) && (mDataTable != null))
			strBuff.append(mXMLDataTable.toXMLString(prefix + XML_TAB, newLine));

		strBuff.append(prefix + XML_ELEMENT_OPEN_END + XML_SEARCH_PATH_REPLY + XML_ELEMENT_CLOSE + newLine); // {prefix}</SearchPathReply>{newLine}
		return strBuff.toString();
	}

}
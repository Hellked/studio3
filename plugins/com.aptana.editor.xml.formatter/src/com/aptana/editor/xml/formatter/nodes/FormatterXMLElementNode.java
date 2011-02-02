/**
 * Aptana Studio
 * Copyright (c) 2005-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the GNU Public License (GPL) v3 (with exceptions).
 * Please see the license.html included with this distribution for details.
 * Any modifications to this file must keep this entire header intact.
 */
package com.aptana.editor.xml.formatter.nodes;

import java.util.Set;

import com.aptana.editor.xml.formatter.XMLFormatterConstants;
import com.aptana.formatter.IFormatterContext;
import com.aptana.formatter.IFormatterDocument;
import com.aptana.formatter.nodes.FormatterBlockWithBeginEndNode;

/**
 * A default tag node formatter is responsible of the formatting of a tag that has a begin and end, however, should not
 * be indented.
 */
public class FormatterXMLElementNode extends FormatterBlockWithBeginEndNode
{
	private String element;
	private boolean children;

	/**
	 * @param document
	 */
	public FormatterXMLElementNode(IFormatterDocument document, String element, boolean hasChildrenInAST)
	{
		super(document);
		this.element = element;
		this.children = hasChildrenInAST;
	}

	/*
	 * (non-Javadoc)
	 * @see com.aptana.formatter.nodes.FormatterBlockNode#isIndenting()
	 */
	protected boolean isIndenting()
	{
		Set<String> set = getDocument().getSet(XMLFormatterConstants.INDENT_EXCLUDED_TAGS);
		return !set.contains(element);
	}

	/*
	 * (non-Javadoc)
	 * @see com.aptana.formatter.nodes.FormatterBlockNode#isAddingBeginNewLine()
	 */
	protected boolean isAddingBeginNewLine()
	{
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.aptana.formatter.nodes.FormatterBlockNode#isAddingEndNewLine()
	 */
	protected boolean isAddingEndNewLine()
	{
		Set<String> set = getDocument().getSet(XMLFormatterConstants.NEW_LINES_EXCLUDED_TAGS);
		return (!set.contains(element) || children);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * com.aptana.formatter.nodes.FormatterBlockWithBeginEndNode#getBlankLinesAfter(com.aptana.formatter.IFormatterContext
	 * )
	 */
	protected int getBlankLinesAfter(IFormatterContext context)
	{
		return getInt(XMLFormatterConstants.LINES_AFTER_ELEMENTS);
	}
}

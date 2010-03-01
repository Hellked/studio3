package com.aptana.editor.html;

import junit.framework.TestCase;

import com.aptana.editor.html.parsing.HTMLParseState;
import com.aptana.editor.html.parsing.HTMLParser;
import com.aptana.parsing.ast.IParseNode;

public class HTMLParserTest extends TestCase
{

	private HTMLParser fParser;
	private HTMLParseState fParseState;

	protected void setUp() throws Exception
	{
		fParser = new HTMLParser();
		fParseState = new HTMLParseState();
	}

	protected void tearDown() throws Exception
	{
		fParser = null;
	}

	public void testSelfClosing() throws Exception
	{
		String source = "<html/>\n";
		parseTest(source, "<html></html>\n");
	}

	public void testTags() throws Exception
	{
		String source = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n"
				+ "<html><head></head><body><p>Text</html>\n";
		parseTest(source, "<html><head></head><body><p></p></body></html>\n");
	}

	public void testEmptyTagInXHTML() throws Exception
	{
		String source = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n"
				+ "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" + "<body><br /><table></table></body>\n";
		parseTest(source, "<body><br></br><table></table></body>\n");
	}

	public void testQuotedPair() throws Exception
	{
		String source = "<html><head>shouldn't</head><body>can't</body></html>\n";
		parseTest(source, "<html><head></head><body></body></html>\n");
	}

	public void testAmpersand() throws Exception
	{
		String source = "<body><p>Gifts&nbsp; & Wish Lists</p><h3></h3></body>\n";
		parseTest(source, "<body><p></p><h3></h3></body>\n");
	}

	public void testOutlineAttributes() throws Exception
	{
		String source = "<html id=\"aptana\" class=\"cool\" height=\"100\">";
		fParseState.setEditState(source, source, 0, 0);

		IParseNode result = fParser.parse(fParseState);
		IParseNode[] children = result.getChildren();
		assertEquals(1, children.length);
		assertEquals("html#aptana.cool", children[0].getText());
	}

	public void testStyle() throws Exception
	{
		String source = "<html><head><style>html {color: red;}</style></head></html>\n";
		parseTest(source);
	}

	public void testScript() throws Exception
	{
		String source = "<html><head><script>var one = 1;</script></head></html>\n";
		parseTest(source);
	}

	protected void parseTest(String source) throws Exception
	{
		parseTest(source, source);
	}

	protected void parseTest(String source, String expected) throws Exception
	{
		fParseState.setEditState(source, source, 0, 0);
		IParseNode result = fParser.parse(fParseState);

		StringBuilder text = new StringBuilder();
		IParseNode[] children = result.getChildren();
		for (IParseNode child : children)
		{
			text.append(child).append("\n");
		}
		assertEquals(expected, text.toString());
	}
}

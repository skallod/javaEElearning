package ru.galuzin;
import com.atlassian.renderer.wysiwyg.converter.DefaultWysiwygConverter;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        String htmlString = "This is <em>emphasized</em> and <b>bold</b>";
        DefaultWysiwygConverter converter = new DefaultWysiwygConverter();
        String wikiMarkupString = converter.convertXHtmlToWikiMarkup(htmlString);
        System.out.println("wikiMarkupString = " + wikiMarkupString);
        //Assert.assertEquals("This is _emphasized_ and *bold*", wikiMarkupString);
    }
}

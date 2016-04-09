package rss;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RSSHandler extends DefaultHandler {

    private RSSFeed feed;
    private RSSItem item;
    static final int RSS_TITLE = 1;
    static final int RSS_LINK = 2;
    static final int RSS_DESCRIPTION = 3;
    static final int RSS_CATEGORY = 4;
    static final int RSS_PUBDATE = 5;
    static final int RSS_GUID = 6;

    private int currentstate = 0;

    /*
     * Constructor
     */
    RSSHandler() {
    }

    /*
     * getFeed - this returns our feed when all of the parsing is complete
     */
    public RSSFeed getFeed() {
        return feed;
    }

    /**
     * @see DefaultHandler#startDocument()
     */
    @Override
    public void startDocument() throws SAXException {
        // initialize our RSSFeed object - this will hold our parsed contents
        feed = new RSSFeed();
        // initialize the RSSItem object - you will use this as a crutch to grab
        // the info from the channel
        // because the channel and items have very similar entries..
        item = new RSSItem();

    }

    /**
     * @see DefaultHandler#endDocument()
     */

    @Override
    public void endDocument() throws SAXException {
    }

    /**
     * @see DefaultHandler#startElement(String, String, String, Attributes)
     */
    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        if (localName.equals("channel")) {
            currentstate = 0;
            return;
        }
        if (qName.equals("image")) {
            // record our feed data - you temporarily stored it in the item :)
            feed.setTitle(item.getTitle());
            feed.setPubDate(item.getPubDate());
        }
        if (qName.equals("item")) {
            // create a new item
            item = new RSSItem();
            return;
        }
        if (qName.equals("title")) {
            currentstate = RSS_TITLE;
            return;
        }
        if (qName.equals("content:encoded")) {
            currentstate = RSS_DESCRIPTION;
            return;
        }
        if (qName.equals("link")) {
            currentstate = RSS_LINK;
            return;
        }
        if (qName.equals("category")) {
            currentstate = RSS_CATEGORY;
            return;
        }
        if (qName.equals("pubDate")) {
            currentstate = RSS_PUBDATE;
            return;
        }
        if (qName.equals("guid")) {
            currentstate = RSS_GUID;
            return;
        }
        // if you don't explicitly handle the element, make sure you don't wind
        // up erroneously storing a newline or other bogus data into one of our
        // existing elements
        currentstate = 0;
    }

    /**
     * @see DefaultHandler#endElement(String, String, String)
     */
    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if (qName.equals("item")) {
            // add our item to the list!
            feed.addItem(item);
            return;
        }
    }

    /**
     * @see DefaultHandler#characters(char[], int, int)
     */
    @Override
    public void characters(char ch[], int start, int length) {
        String theString = new String(ch, start, length);
        switch (currentstate) {
            case RSS_TITLE:
                item.setTitle(theString);
                currentstate = 0;
                break;
            case RSS_LINK:
                item.setLink(theString);
                currentstate = 0;
                break;
            case RSS_DESCRIPTION:
                item.setDescription(theString);
                currentstate = 0;
                break;
            case RSS_CATEGORY:
                item.setCategory(theString);
                currentstate = 0;
                break;
            case RSS_PUBDATE:
                item.setPubDate(theString);
                currentstate = 0;
                break;
            case RSS_GUID:
                item.setGuid(stripURL(theString));
                break;
            default:
                return;
        }

    }

    /**
     * the guid is built as
     * <p>
     * <pre>
     * <guid isPermaLink="false">http://www.telfort.nl/mededelingen/?p=11585</guid>
     * </pre>
     * <p>
     * We only want the id
     *
     * @param theString
     * @return
     */
    private String stripURL(String theString) {
        int indexOf = theString.indexOf("p=");
        if (indexOf > 0) {
            return theString.substring(indexOf + 2);
        }
        return null;
    }
}
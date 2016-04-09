package rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.net.URL;
import java.util.*;

/**
 * Container for a complete feed
 *
 * @author Ron
 */
public class RSSFeed {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String title = null;
    private Date pubdate = null;
    private int itemcount = 0;
    private final List<RSSItem> itemlist;

    /**
     * Setup the itemlist.
     */
    public RSSFeed() {
        itemlist = new ArrayList<RSSItem>();
    }

    /**
     * Get the feed in the parameter <code>urlfeed</code>. The url will be
     * parsed and entered into the feed container.
     *
     * @param urlfeed
     * @return the feed or null if something/anything went wrong.
     */
    public RSSFeed getFeed(String urlfeed) {
        try {
            URL url = new URL(urlfeed);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();

            RSSHandler handler = new RSSHandler();
            reader.setContentHandler(handler);
            InputSource inputSource = new InputSource(url.openStream());

            reader.parse(inputSource);
            return handler.getFeed();
        } catch (Exception e) {
            logger.debug("getting feed: " + e.toString());
        }
        return this;
    }

    public int addItem(RSSItem item) {
        itemlist.add(item);
        itemcount++;
        return itemcount;
    }

    /**
     * Get all the items in category.
     *
     * @param category
     * @return always returns a list, if nothing is found it will return an
     * empty list.
     */
    public List<RSSItem> getAllItemsByCategory(String category) {
        List<RSSItem> catlist = new ArrayList<RSSItem>();
        for (RSSItem item : getAllItems()) {
            if (item.getCategory().equals(category)) {
                catlist.add(item);
            }
        }
        Collections.sort(catlist, rssItemSorter());
        return catlist;
    }

    /**
     * get the item at the specified location.
     *
     * @param location
     * @return
     */
    public RSSItem getItem(int location) {
        return itemlist.get(location);
    }

    /**
     * Get all the items in the feed.
     *
     * @return
     */
    public List<RSSItem> getAllItems() {
        Collections.sort(itemlist, rssItemSorter());
        return itemlist;
    }

    private Comparator<RSSItem> rssItemSorter() {
        return (lhs, rhs) -> {
            // remember we sort reverse newest first
            return lhs.getPubDate().compareTo(rhs.getPubDate());
        };
    }

    protected int getItemCount() {
        return itemcount;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    protected void setPubDate(Date pubdate) {
        this.pubdate = pubdate;
    }

    protected String getTitle() {
        return title;
    }

    protected Date getPubDate() {
        return pubdate;
    }
}
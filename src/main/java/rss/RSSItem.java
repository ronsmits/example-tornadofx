package rss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Class to contain a single RSS item.
 *
 * @author Ron
 */
public class RSSItem {
    private String title = null;
    private String desc = null;
    private String link = null;
    private String category = null;
    private Date pubdate = null;
    private String guid = null;

    // Tue, 19 Feb 2013 09:51:49 +0000
    private static final String RSS = "EEE, dd MMM yyyy HH:mm:ss ZZZZZ";
    private final SimpleDateFormat sf = new SimpleDateFormat(RSS, Locale.ENGLISH);
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Empty constructor.
     */
    public RSSItem() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        desc = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Parse the string into a date. If there are problems parsing the string
     * the pubdate will be set to now.
     *
     * @param pubdate
     */
    public void setPubDate(String pubdate) {
        try {
            this.pubdate = sf.parse(pubdate);
        } catch (ParseException e) {
            logger.error("parsing pubDate: " + e.getMessage());
            this.pubdate = new Date();
        }
    }

    public void setPubDate(Date date) {
        this.pubdate = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return desc;
    }

    String getLink() {
        return link;
    }

    String getCategory() {
        return category;
    }

    Date getPubDate() {
        return pubdate;
    }

    @Override
    public String toString() {
        return pubdate + " " + title;
    }

    public String getGuid() {
        return guid;
    }

    /**
     * only set the guid if the guid is not null. Somehow wordpress is able to
     * have a null guid.
     *
     * @param guid
     */
    public void setGuid(String guid) {
        if (guid != null) {
            this.guid = guid;
        }
    }
}
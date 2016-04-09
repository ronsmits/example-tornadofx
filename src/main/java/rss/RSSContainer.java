package rss;

/**
 * RSSContainer is a singleton that will contain the rss feeds that are used for
 * the notifications. The feeds are set by a scheduled task that runs every hour
 * on the hour.
 *
 * @author ron
 */
public class RSSContainer {

    private static RSSContainer instance = null;

    private RSSFeed androidfeed = new RSSFeed();
    private RSSFeed iphonefeed = new RSSFeed();
    private RSSFeed webfeed = new RSSFeed();
    private RSSFeed generalFeed = new RSSFeed();

    public static RSSContainer getInstance() {
        if (instance == null) {
            instance = new RSSContainer();
        }
        return instance;
    }

    public RSSFeed getAndroidfeed() {
        return androidfeed;
    }

    public void setAndroidfeed(RSSFeed androidfeed) {
        this.androidfeed = androidfeed;
    }

    public RSSFeed getIphonefeed() {
        return iphonefeed;
    }

    public void setIphonefeed(RSSFeed iphonefeed) {
        this.iphonefeed = iphonefeed;
    }

    public RSSFeed getWebfeed() {
        return webfeed;
    }

    public void setWebfeed(RSSFeed webfeed) {
        this.webfeed = webfeed;
    }

    public RSSFeed getGeneralFeed() {
        return generalFeed;
    }

    public void setGeneralFeed(RSSFeed generalFeed) {
        this.generalFeed = generalFeed;
    }

    /**
     * return the feed that is connected to a front end.
     *
     * @param type
     * @return the feed or null if no feed found.
     */
    public RSSFeed getTypeFeed(String type) {
        RSSFeed feed = null;
        switch (type) {
            case "android":
                feed = androidfeed;
                break;
            case "iphone":
                feed = iphonefeed;
                break;
            case "web":
                feed = webfeed;
                break;
            default:
                feed = null;
                break;
        }
        return feed;
    }
}

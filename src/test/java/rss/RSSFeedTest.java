package rss;

/**
 * Created by ron on 9-4-16.
 */
public class RSSFeedTest {
    @org.junit.Test
    public void getFeed() throws Exception {
        RSSFeed feed = new RSSFeed();
        feed = feed.getFeed("http://www.jazzpodcast.nl/feed/");
        System.out.println(feed.getItemCount());

    }

    @org.junit.Test
    public void getAllItems() throws Exception {

    }

}
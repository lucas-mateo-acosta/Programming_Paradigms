package utils;

public class Config {
    private boolean printFeed = false;
    private String heurticKey;
    private boolean messageHelp;
    private String feedKey;
    private String statsKey;
    // TODO: A reference to the used heuristic will be needed here

    public Config(boolean printFeed, String heurtickey, boolean messageHelp, String statsKey, String feedKey) {
        this.printFeed = printFeed;
        this.heurticKey = heurtickey;
        this.feedKey = feedKey;
        this.messageHelp = messageHelp;
        this.statsKey = statsKey;
    }

    public boolean getPrintFeed() {
        return printFeed;
    }

    public String getHeurticKey() {
        return heurticKey;
    }

    public boolean getMessageHelp() {
        return messageHelp;
    }

    public String getFeedKey() {
        return feedKey;
    }

    public String getStatsKey() {
        return statsKey;
    }


}

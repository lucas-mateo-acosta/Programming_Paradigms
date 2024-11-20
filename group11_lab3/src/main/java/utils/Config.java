package utils;

public class Config {
    private boolean printFeed = false;
    private String heurticKey;
    private boolean messageHelp;
    private String statsKey;
    private String path;

    public Config(boolean printFeed, String heurtickey, boolean messageHelp, String statsKey, String path) {
        this.printFeed = printFeed;
        this.heurticKey = heurtickey;
        this.messageHelp = messageHelp;
        this.statsKey = statsKey;
        this.path = path;
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

    public String getStatsKey() {
        return statsKey;
    }

    public String getPath() {
        return path;
    }

}

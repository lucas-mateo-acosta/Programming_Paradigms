import feed.Article;
import feed.FeedParser;
import namedEntities.NamedEntity;
import namedEntities.heuristics.Heuristics;
import namedEntities.ComputeNamedEntities;
import utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class App {

    public static void main(String[] args) {

        List<FeedsData> feedsDataArray = new ArrayList<>();
        try {
            feedsDataArray = JSONParser.parseJsonFeedsData("src/data/feeds.json");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        if (args.length == 0) {
            Impresion.printHelp(feedsDataArray);
            System.exit(0);
        }

        UserInterface ui = new UserInterface();
        Config config = ui.handleInput(args);
        run(config, feedsDataArray);

    }

    private static void run(Config config, List<FeedsData> feedsDataArray) {

        HashMap<String, Integer> keyIndice = new HashMap<>();

        for (int j = 0; j < feedsDataArray.size(); ++j) {
            keyIndice.put(feedsDataArray.get(j).getLabel(), j);
        }

        if (feedsDataArray == null || feedsDataArray.size() == 0) {
            System.out.println("No feeds data found");
            System.exit(0);
        }

        // si -h se encuentra printea el aviso y sale del programa
        if (config.getMessageHelp()) {
            Impresion.printHelp(feedsDataArray);
            System.exit(0);
        }

        List<Article> allArticles = new ArrayList<>();

        // guardo en allArticles todos los articulos de todos los feeds
        for (int index = 0; index < feedsDataArray.size(); index++) {
            try {
                String url = feedsDataArray.get(index).getUrl();
                allArticles.addAll(FeedParser.parseXML(FeedParser.fetchFeed(url)));
            } catch (Exception MalformedURLException) {
                System.out.println("Ocurrió un error");
            }
        }

        // si obtengo pf y ningun valor printea todos los articulos de los feeds
        if (config.getPrintFeed() && (config.getFeedKey() == null)) {
            System.out.println(config.getFeedKey());
            System.out.println("Printing feed(s) ");
            Impresion.printArticles(allArticles);
        }

        String key = config.getFeedKey();
        Integer indice = keyIndice.get(key);
        List<Article> feed = new ArrayList<>();
        // flag: -f feed
        if (config.getFeedKey() != null) {
            if (indice == null) {
                System.out.println("No existe ese feed");
                System.exit(0);
            }
            try {
                String url = feedsDataArray.get(indice).getUrl();
                feed = FeedParser.parseXML(FeedParser.fetchFeed(url));
                if (config.getHeurticKey() == null || config.getPrintFeed()) {
                    // si no existe la flag -ne se considera activo el -pf, por lo tanto printea el
                    // feed.
                    System.out.println("Feed label: " + feedsDataArray.get(indice).getLabel());
                    System.out.println();
                    Impresion.printArticles(feed);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // flag: -ne heuristica
        if (config.getHeurticKey() != null) {

            HeuristicHasMap hasHeuristics = new HeuristicHasMap();
            String heuristicKey = config.getHeurticKey();
            Heuristics heuristic = hasHeuristics.get(heuristicKey);

            if (heuristic == null) {
                System.out.println(heuristicKey + " : no existe como heuristica, use -h para mas informacion");
                System.exit(0);
            }
            System.out.println("Computing named entities using heuristic " + config.getHeurticKey());
            List<NamedEntity> namedEntities;

            if (config.getFeedKey() != null) {
                namedEntities = ComputeNamedEntities.clasificarEntidades(feed, heuristic);
            } else {
                namedEntities = ComputeNamedEntities.clasificarEntidades(allArticles, heuristic);
            }

            // si no se especifica -sf o si se especifica "-sf cat"
            if (config.getStatsKey() == null || (config.getStatsKey() != null && config.getStatsKey().equals("cat"))) {

                Impresion.printStats(namedEntities, "cat");

                // si se especifica "-sf topic"
            } else if (config.getStatsKey().equals("topic")) {
                Impresion.printStats(namedEntities, "topic");
            } else {
                System.out.println("No existe ese formato de estadística");
                System.exit(0);
            }
        }

    }

}

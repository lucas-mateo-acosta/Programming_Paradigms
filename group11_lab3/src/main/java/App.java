package ar.edu.unc.famaf.paradigmas.lab3;

import org.apache.spark.sql.SparkSession;
import utils.*;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.*;
import feed.Article;
import feed.FeedParser;
import namedEntities.NamedEntity;
import namedEntities.heuristics.Heuristics;

public class App {

    private static String AppPath = System.getenv("APP_PATH");

    public static void main(String[] args) {

        List<FeedsData> feedsDataArray = new ArrayList<>();
        try {
            feedsDataArray = JSONParser
                    .parseJsonFeedsData(AppPath + "/src/main/java/data/feeds.json"); // Se arma la ruta para acceder a
                                                                                     // los archivos JSON.
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

        // si se encuentra el flag -h se imprime el mensaje de ayuda y sale del programa
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

        // si obtengo pf y ningún valor ,imprime por pantalla todos los articulos de los
        // feeds
        if (config.getPrintFeed()) {
            System.out.println("Printing feed(s) ");
            Impresion.printArticles(allArticles);
        }

        // flag: -ne heuristica
        if (config.getHeurticKey() != null) {

            HeuristicHasMap hasHeuristics = new HeuristicHasMap();
            String heuristicKey = config.getHeurticKey();
            Heuristics heuristic = hasHeuristics.get(heuristicKey);

            if (heuristic == null) {
                System.out.println(heuristicKey + " : no existe como heurística, use -h para mas información");
                System.exit(0);
            }
            System.out.println("Computing named entities using heuristic " + config.getHeurticKey());

            String JSONpath = AppPath + "/src/main/java/data/dictionary.json";

            Spark.SparkSession(heuristic, config.getStatsKey(), config.getPath(), JSONpath, allArticles);
        }

    }

}

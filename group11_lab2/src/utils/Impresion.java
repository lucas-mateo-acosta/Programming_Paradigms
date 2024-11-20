package utils;

import feed.Article;
import namedEntities.NamedEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Impresion {

    public static void printArticles(List<Article> allArticle) {
        for (Article article : allArticle) {
            article.print();
        }
    }

    public static void printStats(List<NamedEntity> listNameEntity, String format) {
        HashMap<String, HashMap<NamedEntity, Integer>> hash = new HashMap<>();
    
        for (NamedEntity entity : listNameEntity) {
            List<String> keys = format.equals("cat") ? List.of(entity.getCategory()) : entity.getTopics();
            int occurrences = entity.getOcurrencias();
    
            for (String key : keys) {
                hash.putIfAbsent(key, new HashMap<>());
                HashMap<NamedEntity, Integer> entityCountMap = hash.get(key);
                entityCountMap.put(entity, occurrences);
            }
        }
    
        for (String key : hash.keySet()) {
            System.out.println((format.equals("cat") ? "Category" : "Topic") + " : " + key);
            for (NamedEntity entity : hash.get(key).keySet()) {
                System.out.println("\t" + entity.getName() + " (" + hash.get(key).get(entity) + ")");
            }
        }
    }
    
    

    public static void printHelp(List<FeedsData> feedsDataArray) {
        System.out.println("Usage: make run ARGS=\"[OPTION]\"");
        System.out.println("Options:");
        System.out.println("  -h, --help: Show this help message and exit");
        System.out.println("  -f, --feed <feedKey>:                Fetch and process the feed with");
        System.out.println("                                       the specified key");
        System.out.println("                                       Available feed keys are: ");
        for (FeedsData feedData : feedsDataArray) {
            System.out.println("                                       " + feedData.getLabel());
        }
        System.out.println("  -ne, --named-entity <heuristicName>: Use the specified heuristic to extract");
        System.out.println("                                       named entities");
        System.out.println("                                       Available heuristic names are: ");
        System.out.println(
                "                                       <CapitalizedWord>: <clasifica todas las palabras que empiezan con mayusculas>");
        System.out.println(
                "                                       <AcronymWord>: <clasifica todas las palabras que son acronimos >");
        System.out.println("                                       <Dictionary>: <clasifica todas las palabras que ");
        System.out.println(
                "                                       coinciden con las keywords que se encuentran en dictionary.json>");
        System.out.println();
        System.out.println("  -pf, --print-feed:                   Print the fetched feed");
        System.out.println("  -sf, --stats-format <format>:        Print the stats in the specified format");
        System.out.println("                                       Available formats are: ");
        System.out.println("                                       cat: Category-wise stats");
        System.out.println("                                       topic: Topic-wise stats");
    }

}

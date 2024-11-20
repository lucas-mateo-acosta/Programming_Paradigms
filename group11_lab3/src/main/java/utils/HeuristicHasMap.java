package utils;

import namedEntities.heuristics.AcronymHeuristic;
import namedEntities.heuristics.CapitalizedWordHeuristic;
import namedEntities.heuristics.DiccionaryKeyWordsHeuristic;
import namedEntities.heuristics.Heuristics;

import java.util.HashMap;

public class HeuristicHasMap {
    private HashMap<String, Heuristics> hasHeuristics = new HashMap<String, Heuristics>();

    public HeuristicHasMap() {
        hasHeuristics.put("CapitalizedWord", new CapitalizedWordHeuristic());
        hasHeuristics.put("AcronymWord", new AcronymHeuristic());
        hasHeuristics.put("Dictionary", new DiccionaryKeyWordsHeuristic());
    }

    public Heuristics get(String key) {
        return hasHeuristics.get(key);
    }

}

package namedEntities.heuristics;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiccionaryKeyWordsHeuristic implements Heuristics {

    public List<String> extractCandidates(String text) {

        List<String> candidates = new ArrayList<>();
        text = text.replaceAll("[-+.^:,\"]", "");
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        text = text.replaceAll("\\p{M}", "");

        try {
            String jsonStr = new String(Files.readAllBytes(Paths.get("src/data/dictionary.json")));
            JSONArray jsonArray = new JSONArray(jsonStr);
            StringBuilder regexBuilder = new StringBuilder();
            regexBuilder.append("(");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                JSONArray keywordsArray = obj.getJSONArray("keywords");
                for (int j = 0; j < keywordsArray.length(); j++) {
                    regexBuilder.append(Pattern.quote(keywordsArray.getString(j)));
                    if (j < keywordsArray.length() - 1 || i < jsonArray.length() - 1) {
                        regexBuilder.append("|");
                    }
                }
            }
            regexBuilder.append(")");
            Pattern pattern = Pattern.compile(regexBuilder.toString());
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                candidates.add(matcher.group());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return candidates;
    }
}

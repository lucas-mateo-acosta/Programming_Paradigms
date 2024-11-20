package utils;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSON implements Serializable {

    private List<HashMap<String, Object>> json = new ArrayList<>();

    public JSON(String jsonPath) {

        try {
            JSONArray jsonArray = new JSONArray(new String(Files.readAllBytes(Paths.get(jsonPath))));

            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                HashMap<String, Object> obj = new HashMap<>();
                obj.put("label", jsonObject.getString("label"));
                obj.put("Category", jsonObject.getString("Category"));
                obj.put("Topics", arrayToList(jsonObject.getJSONArray("Topics")));
                obj.put("keywords", arrayToList(jsonObject.getJSONArray("keywords")));
                json.add(obj);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public String getLabel(int index) {
        return (String) json.get(index).get("label");
    }

    public String getCategory(int index) {
        return (String) json.get(index).get("Category");
    }

    public List<String> getTopics(int index) {
        return (List<String>) json.get(index).get("Topics");
    }

    public List<String> getKeywords(int index) {
        return (List<String>) json.get(index).get("keywords");
    }

    public int getLength() {
        return json.size();
    }

    private List<String> arrayToList(JSONArray jsonarray) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < jsonarray.length(); ++i) {
            res.add(jsonarray.getString(i));
        }
        return res;
    }

}
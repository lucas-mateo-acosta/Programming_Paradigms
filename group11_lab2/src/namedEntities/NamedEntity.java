package namedEntities;

import java.util.ArrayList;
import java.util.List;

public class NamedEntity {
    private String name;
    private String category;
    private List<String> topics;
    private int ocurrencias;

    public NamedEntity(String name, String category) {
        this.name = name;
        this.category = category;
        this.ocurrencias = 1;
        this.topics = new ArrayList<>();
    }

    public String getCategory(){
        return this.category;
    }

    public void addTopic(String topic) {
        this.topics.add(topic);
    }

    public List<String> getTopics() {
        return topics;
    }

    public String getName() {
        return name;
    }

    public void addOcurrencias() {
        this.ocurrencias++;
    }

    public int getOcurrencias() {
        return ocurrencias;
    }

}
package namedEntities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import namedEntities.SubEntidades.*;

public class NamedEntity implements Serializable {
    private String name;
    private String category;
    private List<String> topics;
    private Integer ocurrencias;

    public NamedEntity(String name, String category) {
        this.name = name;
        this.category = category;
        this.ocurrencias = 1;
        this.topics = new ArrayList<>();
    }

    public String getCategory() {
        return this.category;
    }

    public void addTopic(String topic) {
        this.topics.add(topic);
    }

    public void addTopics(List<String> topics) {
        this.topics.addAll(topics);
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

    public void setOcurrencias(int occur) {
        this.ocurrencias = occur;
    }

    public Integer getOcurrencias() {
        return ocurrencias;
    }

    public static NamedEntity CrearEntidad(String label, String categoria) {
        NamedEntity entidad = null;
        switch (categoria) {
            case "PERSON":
                entidad = new Person(label);
                break;
            case "LOCATION":
                entidad = new Location(label);
                break;
            case "ORGANIZATION":
                entidad = new Organization(label);
                break;
            case "EVENT":
                entidad = new Event(label);
                break;
            case "OTHER":
                entidad = new Other(label);
                break;
            default:
                System.out.println("La categoria " + categoria + "no existe");
                System.exit(1);
        }
        return entidad;
    }

}
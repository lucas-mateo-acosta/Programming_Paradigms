package namedEntities.SubEntidades;

import namedEntities.NamedEntity;

public class Event extends NamedEntity {
    private String fecha = null;

    public Event(String name) {
        super(name, "EVENT");
    }
}
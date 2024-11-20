package namedEntities.SubEntidades;

import namedEntities.NamedEntity;

public class Location extends NamedEntity {
    private int longitud = 0;
    private int latitud = 0;

    public Location(String name) {
        super(name, "LOCATION");
    }

}
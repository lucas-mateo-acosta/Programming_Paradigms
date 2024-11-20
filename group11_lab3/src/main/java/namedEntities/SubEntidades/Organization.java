package namedEntities.SubEntidades;

import namedEntities.NamedEntity;

public class Organization extends NamedEntity {
    private boolean internacional = false;

    public Organization(String name) {
        super(name, "ORGANIZATION");
    }

}
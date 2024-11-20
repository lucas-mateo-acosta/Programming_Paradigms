package namedEntities.SubEntidades;

import namedEntities.NamedEntity;


public class Person extends NamedEntity {
    private String nombre = null;
    private String apellido = null;

    public Person(String name) {
        super(name,"PERSON");
    }

}
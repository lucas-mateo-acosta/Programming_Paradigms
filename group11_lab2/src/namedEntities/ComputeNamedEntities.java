package namedEntities;

import feed.Article;
import namedEntities.heuristics.*;
import namedEntities.SubEntidades.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ComputeNamedEntities {

    static public List<NamedEntity> clasificarEntidades(List<Article> articles, Heuristics heuristic) {
        List<String> candidatesList = new ArrayList<>();
        List<NamedEntity> clasificados = new ArrayList<>();

        for (Article article : articles) {
            String titlePlusDescription = article.getTitle() + article.getDescription();
            candidatesList.addAll(heuristic.extractCandidates(titlePlusDescription));
        }
        try {
            clasificados = clasificarCandidatos(candidatesList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clasificados;
    }

    static public List<NamedEntity> clasificarCandidatos(List<String> candidatos) throws IOException {
        String jsonData = new String(Files.readAllBytes(Paths.get("src/data/dictionary.json")));
        List<NamedEntity> namedEntities = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonData);
        boolean noEncontrado = true;
        for (String candidato : candidatos) {
            int indice = 0;
            noEncontrado = true;
            while (indice < jsonArray.length() && noEncontrado) {
                JSONObject jsonObject = jsonArray.getJSONObject(indice);
                JSONArray keywords = jsonObject.getJSONArray("keywords");
                for (int i = 0; i < keywords.length(); i++) {
                    if (candidato.equals(keywords.getString(i)) && !entityExist(namedEntities, jsonObject.getString("label"))) {
                        String label = jsonObject.getString("label");
                        String category = jsonObject.getString("Category");
                        JSONArray topicos = jsonObject.getJSONArray("Topics");
                        NamedEntity entidad = CrearEntidad(label,category);
                        for (int j = 0; j < topicos.length(); j++) {
                            entidad.addTopic(topicos.getString(j));
                        }
                        noEncontrado = false;
                        namedEntities.add(entidad);
                    } else if (candidato.equals(keywords.getString(i))  && entityExist(namedEntities, jsonObject.getString("label"))) {
                        NamedEntity entidad = returnEntity(namedEntities,jsonObject.getString("label"));
                        entidad.addOcurrencias();
                        noEncontrado = false;
                    }
                }
                indice++;
            }
            if (noEncontrado && !entityExist(namedEntities, candidato)) {
                NamedEntity entidad = new Other(candidato);
                entidad.addTopic("OTHER");
                namedEntities.add(entidad);
            } else if (noEncontrado && entityExist(namedEntities, candidato)) {
                NamedEntity entidad = returnEntity(namedEntities,candidato);
                entidad.addOcurrencias();
            }

        }
        return namedEntities;
    }


    static private NamedEntity returnEntity(List<NamedEntity> lista, String candidato) {
        for (int i = 0; i < lista.size(); ++i) {
            if (lista.get(i).getName().equals(candidato)) {
                return lista.get(i);
            }
        }
        return null;
    }

    static private boolean entityExist(List<NamedEntity> lista, String candidato) {
        for (int i = 0; i < lista.size(); ++i) {
            if (lista.get(i).getName().equals(candidato)) {
                return true;
            }
        }
        return false;
    }


    private static NamedEntity CrearEntidad(String label,String categoria) {
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
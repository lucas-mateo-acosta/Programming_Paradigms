package utils;

import namedEntities.*;
import namedEntities.heuristics.*;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import feed.Article;
import java.util.*;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;

public class Spark {
    public static void SparkSession(Heuristics heuristica, String formato_de_impresion, String path, String pathJSON,
            List<Article> articulos) {

        SparkSession spark = SparkSession
                .builder()
                .appName("JavaApp")
                .master("local[*]")
                .getOrCreate();

        JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());

        try {
            JSON jsonData = new JSON(pathJSON);

            JavaRDD<String> TextoRDD;

            if (path != null) {
                TextoRDD = spark.read().textFile(path).javaRDD();
            } else {
                JavaRDD<Article> articlesRDD = sc.parallelize(articulos); // parelizacion de articulos

                JavaRDD<String> titulosRDD = articlesRDD.map(Article::getTitle); // Se mapean los articulos para obtener
                                                                                 // los titulos

                JavaRDD<String> DescriptionRDD = articlesRDD.map(Article::getDescription); // Se mapean los articulos
                                                                                           // para obtener las
                                                                                           // descripciones.

                TextoRDD = titulosRDD.union(DescriptionRDD);

            }

            JavaRDD<String> TextoRDDFilter = TextoRDD.filter(new Function<String, Boolean>() {
                @Override
                public Boolean call(String elem) throws Exception {
                    return elem != null; // filtra los elementos que no son vacios
                }
            });

            JavaRDD<String> candidates = TextoRDDFilter.flatMap(s -> heuristica.extractCandidates(s).iterator()); // Se
                                                                                                                  // obtiene
                                                                                                                  // los
                                                                                                                  // candidatos
                                                                                                                  // que
                                                                                                                  // cumplen
                                                                                                                  // con
                                                                                                                  // la
                                                                                                                  // heuristica

            JavaPairRDD<String, Integer> tuplecandidates = candidates.mapToPair(s -> new Tuple2<>(s, 1)); // En la tupla
                                                                                                          // s es el
                                                                                                          // candidato y
                                                                                                          // se lo
                                                                                                          // asocia a un
                                                                                                          // 1 que luego
                                                                                                          // se utiliza
                                                                                                          // para
                                                                                                          // contabilizar
                                                                                                          // las
                                                                                                          // ocurrencias
                                                                                                          // del
                                                                                                          // candidato.

            JavaPairRDD<String, Integer> count = tuplecandidates.reduceByKey(Integer::sum); // Se cuentan las
                                                                                            // ocurrencias del
                                                                                            // candidato.

            Broadcast<JSON> br = sc.broadcast(jsonData); // Los Json Array no son serializables. Por lo tanto se opto
                                                         // por crear una clase JSON que luego se pueda serializar. Esto
                                                         // representa el diccionario.

            JavaRDD<NamedEntity> resultadoRDD = count.map(new Function<Tuple2<String, Integer>, NamedEntity>() {
                @Override
                public NamedEntity call(Tuple2<String, Integer> candidato) {

                    JSON json = br.value();
                    String category = "";
                    String name = "";
                    List<String> topics = new ArrayList<>();
                    for (int i = 0; i < json.getLength(); ++i) {
                        List<String> keywords = json.getKeywords(i);
                        if (keywords.contains(candidato._1()) && keywords != null) {
                            category = json.getCategory(i);
                            name = json.getLabel(i);
                            topics = json.getTopics(i);
                            break;
                        }
                    }
                    if (name != "" && category != "") {
                        NamedEntity entidad = NamedEntity.CrearEntidad(name, category);
                        entidad.addTopics(topics);
                        entidad.setOcurrencias(candidato._2());
                        return entidad;
                    } else {
                        // NO CREAMOS ENTIDADES QUE NO PERTENEZCAN AL JSON
                        return null;
                    }
                }
            });

            JavaRDD<NamedEntity> filteredResult = resultadoRDD.filter(new Function<NamedEntity, Boolean>() {
                @Override
                public Boolean call(NamedEntity entidad) throws Exception { // Filtra que solo queden Entidades
                                                                            // Nombradas y no valores nulos.
                    return entidad != null;
                }
            });

            // Las lineas siguientes corrigen la repetición de entidades que tiene el mismo
            // Label en el diccionario. Para ello hacemos uso de dos variables llamadas
            // auxiliar y auxiliar2

            JavaPairRDD<String, NamedEntity> auxiliar = filteredResult.mapToPair(ne -> new Tuple2<>(ne.getName(), ne));

            JavaPairRDD<String, NamedEntity> auxiliar2 = auxiliar.reduceByKey((ne1, ne2) -> {
                NamedEntity entity = new NamedEntity(ne1.getName(), ne1.getCategory());
                entity.setOcurrencias(ne1.getOcurrencias() + ne2.getOcurrencias());
                entity.addTopics(ne1.getTopics());
                return entity;
            });

            JavaRDD<NamedEntity> entidades = auxiliar2.map(Tuple2::_2); // Esto para posibilitar luego la impresión.

            List<NamedEntity> namedEntities = entidades.collect(); // Collect guarda en una lista lo que se encuentra en
                                                                   // un tipo RDD. Los workers devuelven al programa los
                                                                   // datos en forma de lista.

            if (formato_de_impresion == null || (formato_de_impresion.equals("cat"))) {
                Impresion.printStats(namedEntities, "cat");
            } else if (formato_de_impresion.equals("topic")) {
                Impresion.printStats(namedEntities, "topic");
            } else {
                System.out.println("No existe ese formato");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        sc.stop();
        sc.close();
        spark.stop();
    }
}

package feed;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FeedParser {

    public static List<Article> parseXML(String xmlData) {
        List<Article> articles = new ArrayList<>();
        try {
            // nueva instancia de DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // crea un constructor de documentos
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlData));
            // obtengo un documento de xmldata
            Document doc = builder.parse(is);
            // obtengo los modulos por "item"
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("item");
            for (int i = 0; i < list.getLength(); i++) {
                String title = null;
                String description = null;
                String link = null;
                String pubDate = null;
                Node nodo = list.item(i);
                NodeList nodosHijos = nodo.getChildNodes();
                for (int j = 0; j < nodosHijos.getLength(); j++) {
                    Node nodoHijo = nodosHijos.item(j);
                    if (nodoHijo.getNodeName().equals("title")) {
                        title = nodoHijo.getTextContent();
                    } else if (nodoHijo.getNodeName().equals("description")) {
                        description = nodoHijo.getTextContent();
                    } else if (nodoHijo.getNodeName().equals("link")) {
                        link = nodoHijo.getTextContent();
                    } else if (nodoHijo.getNodeName().equals("pubDate")) {
                        pubDate = nodoHijo.getTextContent();
                    }
                }
                Article articulo = new Article(title, description, pubDate, link);
                articles.add(articulo);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return articles;
    }

    public static String fetchFeed(String feedURL) throws MalformedURLException, IOException, Exception {
        URL url = new URL(feedURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-agent", "group_11_lab2_2024");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int status = connection.getResponseCode();
        if (status != 200) {
            throw new Exception("HTTP error code: " + status);
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();
            return content.toString();
        }
    }
}

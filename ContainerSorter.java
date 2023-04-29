import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ContainerSorter {
    public static void main(String[] args) {
        try {
            File inputFile = new File("input.arxml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("CONTAINER");
            List<Container> containers = new ArrayList<>();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String uuid = element.getAttribute("UUID");
                    String shortName = element.getElementsByTagName("SHORT-NAME").item(0).getTextContent();
                    String longName = element.getElementsByTagName("LONG-NAME").item(0).getTextContent();
                    containers.add(new Container(uuid, shortName, longName));
                }
            }

            Collections.sort(containers);

            File outputFile = new File("output.arxml");
            FileWriter writer = new FileWriter(outputFile);

            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<AUTOSAR>\n");

            for (Container container : containers) {
                writer.write(" <CONTAINER UUID=\"" + container.uuid + "\">\n");
                writer.write("  <SHORT-NAME>" + container.shortName + "</SHORT-NAME>\n");
                writer.write("  <LONG-NAME>" + container.longName + "</LONG-NAME>\n");
                writer.write(" </CONTAINER>\n");
            }

            writer.write("</AUTOSAR>\n");
            writer.close();

            System.out.println("Containers reordered successfully!");

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}

class Container implements Comparable<Container> {
    String uuid;
    String shortName;
    String longName;

    public Container(String uuid, String shortName, String longName) {
        this.uuid = uuid;
        this.shortName = shortName;
        this.longName = longName;
    }

    @Override
    public int compareTo(Container other) {
        return this.shortName.compareTo(other.shortName);
    }
}

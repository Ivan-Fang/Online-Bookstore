package ioc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ClassPathXmlApplicationContext implements BeanFactory {
    private Map<String, Object> beanMap = new HashMap<>();

    public ClassPathXmlApplicationContext() {
        this("applicationContext.xml");
    }

    public ClassPathXmlApplicationContext(String path) {
        try {
            if (path == null || path.equals("")) {
                throw new RuntimeException("IOC 配置文件未指定...");
            }

            // 1. read applicationContext.xml and get all the beans in it
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);

            NodeList beanNodeList = document.getElementsByTagName("bean");
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE){
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    Class beanClass = Class.forName(beanElement.getAttribute("class"));

                    Object beanClassObj = beanClass.newInstance();

                    beanMap.put(beanId, beanClassObj);
                }
            }

            // 2. build up the dependencies between beans (ex. FruitController / FruitService / FruitDAO)
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    // get "property" under each element node
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    NodeList beanChildNodeList = beanElement.getChildNodes();
                    for (int j = 0; j < beanChildNodeList.getLength(); j++) {
                        Node beanChildNode = beanChildNodeList.item(j);
                        if (beanChildNode.getNodeType() == Node.ELEMENT_NODE && beanChildNode.getNodeName().equals("property")) {
                            Element beanChildElement = (Element) beanChildNode;
                            String propertyName = beanChildElement.getAttribute("name");    // field name
                            String propertyRef = beanChildElement.getAttribute("ref");      // upper class name

                            Object beanObj = beanMap.get(beanId);
                            Object refObj = beanMap.get(propertyRef);
                            Field field = beanObj.getClass().getDeclaredField(propertyName);
                            field.setAccessible(true);
                            field.set(beanObj, refObj);
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | ClassNotFoundException | SAXException |
                 InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }
}

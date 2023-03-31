import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.*;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileJSONname = "data.json";
        String fileJSONname2 = "data2.json";
        String fileName = "data.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);
        String json = listToJson(list);
        String dataJson = writeFile(json, fileJSONname);
        List<Employee> list2 = parseXML("data.xml");
        String json2 = listToJson(list2);
        String dataJson2 = writeFile(json2, fileJSONname2);

    }

    public static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> staff = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            staff = csv.parse();
            staff.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return staff;
    }

    public static String listToJson(List<Employee> list) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        String json = gson.toJson(list, listType);


        return json;
    }

    public static String writeFile(String json, String fileJSONname) {
        try (FileWriter file = new
                FileWriter(fileJSONname)) {
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static List<Employee> parseXML(String fileName) throws ParserConfigurationException, IOException, SAXException {
        List<Employee> employees = new ArrayList<Employee>();
        Employee employee = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));
        NodeList staff = doc.getElementsByTagName("employee");
        for (int i = 0; i < staff.getLength(); i++) {
            Node node = staff.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                employee = new Employee();
                employee.setId(Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent()));
                employee.setFirstName(element.getElementsByTagName("firstName").item(0).getTextContent());
                employee.setLastName(element.getElementsByTagName("lastName").item(0).getTextContent());
                employee.setCountry(element.getElementsByTagName("country").item(0).getTextContent());
                employee.setAge(Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent()));


                employees.add(employee);
            }
        }
        return employees;
    }
}




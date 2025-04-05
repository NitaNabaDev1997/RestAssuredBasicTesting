package Files;

public class Payload {

    public static String AddBook(String aisle,String isbn)
    {
        String payload="{\n" +
                "\n" +
                "\"name\":\"Learn Appium Automation with Java\",\n" +
                "\"isbn\":\""+isbn+"\",\n" +
                "\"aisle\":\""+aisle+"\",\n" +
                "\"author\":\"John foe\"\n" +
                "}\n";

        return payload;
    }
}

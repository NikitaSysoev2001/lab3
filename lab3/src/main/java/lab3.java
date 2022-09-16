import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class lab3 {

    public static void main(String[] args) throws IOException {
        String html = htmlToString("./src/main/resources/input.html");

        Pattern pattern = Pattern.compile("src=\"[^\"]*\"");
        Matcher matcher = pattern.matcher(html);
        ArrayList list = new ArrayList<String>();

        while(matcher.find()) {
            list.add(matcher.group());
        }

        StringJoiner joiner = new StringJoiner("\n");
        for (Object item : list) {
            joiner.add(item.toString());
        }
        String newString = joiner.toString();
        Pattern pattern2 = Pattern.compile("\"[^\"]*\"");
        Matcher matcher2 = pattern2.matcher(newString);
        ArrayList list2 = new ArrayList<String>();
        while(matcher2.find()) {
            list2.add(matcher2.group().replaceAll("\"",""));
        }

        Map<String, Long> counts = (Map<String, Long>) list2.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        newFile("./src/main/resources/result.txt");
        for (Map.Entry<String, Long> pair : counts.entrySet())
        {
            String key = pair.getKey();                      //ключ
            String value = pair.getValue().toString();       //значение
            System.out.println(key + " " + value);
            appendStrToFile("./src/main/resources/result.txt",key + " " + value + "\n");
        }
    }

    public static String htmlToString(String fileName) throws IOException {
        FileReader reader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String html = "";
        while(bufferedReader.ready()) {
            html = html.concat(bufferedReader.readLine());
        }
        return html;
    }

    public static void appendStrToFile(String fileName, String str) {
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(fileName, true));
            out.write(str);
            out.close();
        }
        catch (IOException e) {
            System.out.println("exception occoured" + e);
        }
    }

    public static void newFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            bufferedWriter.write("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

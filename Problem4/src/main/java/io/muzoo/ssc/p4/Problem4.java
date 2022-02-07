package io.muzoo.ssc.p4;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

public class Problem4 extends Problem3 {

    private HashSet<String> links;
    public String origin;
    public int wordCount;

    public Problem4(String origin) {
        links = new HashSet<String>();
        this.origin = origin;
        wordCount = 0;
    }

    public void getLinks(String url, String des) throws IOException {

        // check if already visited
        if (!links.contains(url) && url.contains(origin)) {
            try {
                // If not add it to links
                if (links.add(url)) {
                    System.out.println(url);
                    //int a = 0;
                }

                // Fetch the HTML code
                Document doc = Jsoup.connect(url).ignoreContentType(true).get();
                // Parse the HTML to extract links to other urls
                Elements linksOnPage = doc.select("a[href]");

                // Count words
                wordCount += countWords(String.valueOf(doc));

                // For each url; go back to the beginning.
                for (Element page : linksOnPage) {

                    // trim off '#'
                    if (page.attr("href").contains("#") || page.attr("href").contains("?")) {
                        continue;
                    }

                    // getting path and links
                    String absURL = page.absUrl("href");
                    URI link = new URI(absURL);
                    String path = des + link.getPath();
                    Path ptf = Paths.get(path);

                    // create dir
                    Files.createDirectories(ptf.getParent());
                    //Files.createFile(ptf);

                    // download
                    download2(link.toString(), path);
//                    System.out.println("ptf: " +  ptf);
//                    System.out.println("path: " +  path);
//                    System.out.println("abs: " + absURL);

                    getLinks(page.attr("abs:href"), des);
                }

            } catch (IOException e) {
                System.err.println("For '" + url + "': " + e.getMessage());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int countWords(String html) throws Exception {
        Document doc = Jsoup.parse(html);
        String text = doc.text();

        return text.split(" ").length;
    }

    public static void main(String[] args) throws IOException {
        // links
        String url = "https://cs.muic.mahidol.ac.th/courses/ooc/";
        String des = "/Users/thanthongchim-ong/Desktop/ssc/p4-test";

        Problem4 test = new Problem4(url);

        test.getLinks(url,des);

        System.out.println(test.links.toString());
        System.out.println("total Link: " + test.links.size());
        System.out.println("word count: " + test.wordCount);


    }

}

/*
Reference:
    https://simplesolution.dev/java-jsoup-uparse-html-document-from-url/
    https://www.javatpoint.com/web-crawler-java
    https://mkyong.com/java/jsoup-basic-web-crawler-example/
    https://www.javacodegeeks.com/2020/10/what-is-a-web-crawler-and-how-to-create-one.html
    https://stackoverflow.com/questions/12277461/delete-everything-after-part-of-a-string
    https://stackoverflow.com/questions/3522454/how-to-implement-a-tree-data-structure-in-java
    https://stackoverflow.com/questions/4144529/how-to-extract-absolute-url-from-relative-html-links-using-jsoup
    https://stackoverflow.com/questions/2833853/create-whole-path-automatically-when-writing-to-a-new-file
    https://stackoverflow.com/questions/4144529/how-to-extract-absolute-url-from-relative-html-links-using-jsoup
    https://stackoverflow.com/questions/2833853/create-whole-path-automatically-when-writing-to-a-new-file
    https://docs.oracle.com/javase/tutorial/networking/urls/urlInfo.html
    https://stackoverflow.com/questions/605696/get-file-name-from-ur
    https://stackoverflow.com/questions/16327105/connection-error-org-jsoup-unsupportedmimetypeexception-unhandled-content-typ
    https://jsoup.org/apidocs/org/jsoup/Connection.html#ignoreContentType(boolean)
 */
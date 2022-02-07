package io.muzoo.ssc.p4;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class Problem3 {

    // JavaIO
    public static void download1(String fileURL, String path) throws IOException {
        InputStream inputStream = new URL(fileURL).openStream();

        String desPath = path;

        Files.copy(inputStream, Paths.get(desPath), StandardCopyOption.REPLACE_EXISTING);
    }

    // Apache commonIO
    public static void download2(String fileURL, String path) throws IOException {
        String desPath = path;

        FileUtils.copyURLToFile(new URL(fileURL),new File(desPath));

    }

    // Apache Http Component
    public static void download3(String fileURL, String path) throws IOException {
        String desPath = path;

        //Creating a HttpClient object
        CloseableHttpClient client = HttpClientBuilder.create().build();

        //Creating a HttpGet object
        HttpGet getLink = new HttpGet(fileURL);

        //Store respond
        CloseableHttpResponse response = client.execute(getLink);
        HttpEntity entity = response.getEntity();

        //Print respond
        InputStream inputStream = entity.getContent();
        FileOutputStream output = new FileOutputStream(desPath);
        int read;
        while( (read = inputStream.read() ) >= 0) {
            output.write(read);
        }
    }


    public static void main(String[] args) throws IOException {
        //keep url
        String url1 = "https://cs.muic.mahidol.ac.th/courses/ooc/api/index.html"; //html
        String url2 = "https://wallup.net/wp-content/uploads/2017/11/23/514780-cat-cat_eyes-animals.jpg"; //jpg

        //keep destination
        String des1 = "/Users/thanthongchim-ong/Desktop/SSC/";

        // Java IO
//        download1(url2,des1, "pic1.jpg");
//        download1(url1,des1,"text1.txt");
//
//        // Apache commonIO
//        download2(url2,des1, "pic2.jpg");
//        download1(url1,des1,"text2.txt");

        // Apache Http Component
//        download3(url2,des1, "pic3.jpg");
//        download3(url1,des1,"text3.txt");
    }
}

/*
Reference:
    https://stackabuse.com/how-to-download-a-file-from-a-url-in-java/
    https://zetcode.com/java/httpclient/
    https://mkyong.com/java/apache-httpclient-examples/
    https://stackoverflow.com/questions/36931603/writing-inside-a-text-file-using-scanner-class
    https://www.baeldung.com/java-copy-file
    https://www.tutorialspoint.com/apache_httpclient/apache_httpclient_http_get_request.htm
 */

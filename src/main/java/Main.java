import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.time.Duration;

public class Main {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void main(String[] args) throws IOException, InterruptedException, SQLException {

        // Create Request
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://www.sakugabooru.com/post.json?limit=1&tags=fate_series"))
                .build();

        // Send Request
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse Json
        Gson gson = new Gson();

        JsonArray jsonArray = gson.fromJson(response.body(), JsonArray.class);

        int file_id = jsonArray.get(0).getAsJsonObject().get("id").getAsInt();
        String file_url = jsonArray.get(0).getAsJsonObject().get("file_url").toString();
        String file_ext = jsonArray.get(0).getAsJsonObject().get("file_ext").toString().replace('"',' ').trim();

        //Check if posted
        Database database = new Database();
        String q = "SELECT EXISTS (SELECT * FROM posted WHERE post_id = " + file_id + ")";

        if ( database.checkIfPosted(q) ) {
            // get new file
            System.out.println("Already posted");
        } else {

            //download file
            FileActions ac = new FileActions();
            ac.downloadFile(file_url, file_ext);

            // Transform video to Twitter standards
                // check file size and format

                // if these aren't correct, then transform them

            // Post

            // If post is successful

            // Store URL in Database

            // else try again or resolve error
        }



//        System.out.println(jsonArray);


//
//        System.out.println(
//            "downloaded"
//        );

    }




}

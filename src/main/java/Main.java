import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Main {

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void main(String[] args) throws IOException, InterruptedException {

        // Create Request
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://www.sakugabooru.com/post.json?limit=3&tags=fate_series"))
                .build();

        // Send Request
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());


        // Parse Json
        Gson gson = new Gson();

        JsonArray jsonArray = gson.fromJson(response.body(), JsonArray.class);

        // Download all files from list
        for (int i  = 0; i < jsonArray.size(); i++ ) {

            String preURL = jsonArray.get(i).getAsJsonObject().get("file_url").toString();
            String fileExt = jsonArray.get(i).getAsJsonObject().get("file_ext").toString().replace('"',' ').trim();

            URL url = new URL(preURL.replace('"',' ').trim());

            try ( BufferedInputStream in = new BufferedInputStream(url.openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream("anime_download_" + i + "." + fileExt)) {
                byte dataBuffer[] = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
            } catch (IOException e) {
                // handle exception
            }

            System.out.println(
                    i + " download"
            );

        }

    }

}

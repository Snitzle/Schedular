import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FileActions {

    public void downloadFile(String file_url, String file_ext) throws MalformedURLException {

        URL url = new URL(file_url.replace('"',' ').trim());

        try (BufferedInputStream in = new BufferedInputStream(url.openStream());

             FileOutputStream fileOutputStream = new FileOutputStream("anime_download" + "." + file_ext)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

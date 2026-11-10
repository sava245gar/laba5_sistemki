import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Main  {

    public static void main(String[] args) {
        String urlString = "https://music.yandex.ru/playlists/lk.fa13ddd4-ef8f-4867-8e1b-ea9b6155991f";
        String filename = "song.mp3";

        Thread downloadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Подождите, идет скачивание...");
                try {
                    saveUrl(filename, urlString);
                    System.out.println("Скачивание завершено");
                } catch (IOException ex) {
                    System.out.println("Ошибка при скачивании: " + ex.getMessage());
                }
            }
        });

        downloadThread.start();

        try {
            downloadThread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void saveUrl(final String filename, final String urlString) throws IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {

            in = new BufferedInputStream(new URL(urlString).openStream());
            fout = new FileOutputStream(filename);

            final byte[] data = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null) in.close();
            if (fout != null) fout.close();
        }
    }
}

package network;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DownloadManger {

    boolean finished = false;
    String path;
    FileOutputStream fileOutputStream;
    ReadableByteChannel readableByteChannel;
    URL url;
    int fileSize = 0;
    String fileName = "";
    File file;



    public DownloadManger(String path) throws IOException {
        url = new URL(path);
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        fileSize = urlConnection.getContentLength();
        String toGetName = urlConnection.getHeaderField("Content-Disposition");
        Pattern pattern = Pattern.compile("filename=\"(.+)\"");
        Matcher matcher;
        if(toGetName!=null){
            matcher = pattern.matcher(toGetName);
            if(matcher.find())
                fileName = matcher.group(1).replaceAll("\"","");}
        else
            fileName = getRandomFileName();//todo : get extension

        file = new File(fileName);
    }

    public void download() throws IOException {

        file.delete();
        finished = false;
        readableByteChannel = Channels.newChannel(url.openStream());
        fileOutputStream = new FileOutputStream(fileName);
        fileOutputStream.getChannel().transferFrom(readableByteChannel , 0 ,Long.MAX_VALUE);
        finished = true;
        System.out.println("finished");
    }


    public boolean isFinished() {
        return finished;
    }

    public void cancel() throws IOException {
        fileOutputStream.close();
    }

    private static String getRandomFileName()
    {
        Integer i = 0;
        Path path = Paths.get("file");
        while (Files.exists(path))
            path = Paths.get("file" + ++i);
        return path.toString();
    }

    public int getFileSize() {
        return fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public File getFile() {
        return file;
    }

}

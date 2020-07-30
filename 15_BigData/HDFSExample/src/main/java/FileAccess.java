import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class FileAccess
{
    FileSystem hdfs;


    /**
     * Initializes the class, using rootPath as "/" directory
     *
     * @param rootPath - the path to the root of HDFS,
     * for example, hdfs://localhost:32771
     */
    public FileAccess(String rootPath) throws URISyntaxException, IOException {
        Configuration configuration = new Configuration();
        configuration.set("dfs.client.use.datanode.hostname", "true");
        System.setProperty("HADOOP_USER_NAME", "root");

        hdfs = FileSystem.get(
                new URI("hdfs://6535fb5dca4d:8020"), configuration
        );

    }

    /**
     * Creates empty file or directory
     *
     * @param path
     */
    public void create(String path) throws IOException {
        hdfs.create(new Path(path));
    }

    /**
     * Appends content to the file
     *
     * @param path
     * @param content
     */
    public void append(String path, String content) throws IOException {
        FSDataOutputStream fsDataOutputStream = hdfs.append(new Path(path));
        PrintWriter printWriter = new PrintWriter(fsDataOutputStream);
        printWriter.append(content);
        printWriter.flush();
        fsDataOutputStream.hflush();
        printWriter.close();
        fsDataOutputStream.close();
    }

    /**
     * Returns content of the file
     *
     * @param path
     * @return
     */
    public String read(String path)
    {
        Path hdfsPath = new Path(path);
        StringBuilder fileContent = new StringBuilder("");
        try{
            BufferedReader bfr=new BufferedReader(new InputStreamReader(hdfs.open(hdfsPath)));
            String str;
            while ((str = bfr.readLine()) != null) {
                fileContent.append(str+"\n");
            }
        }
        catch (IOException ex){
            System.out.println("----------Could not read from HDFS---------\n");
        }
        return fileContent.toString();

    }

    /**
     * Deletes file or directory
     *
     * @param path
     */
    public void delete(String path) throws IOException {
//        hdfs.delete(new Path(path), )
    }

    /**
     * Checks, is the "path" is directory or file
     *
     * @param path
     * @return
     */
    public boolean isDirectory(String path)
    {
        return false;
    }

    /**
     * Return the list of files and subdirectories on any directory
     *
     * @param path
     * @return
     */
    public List<String> list(String path)
    {
        return null;
    }
}

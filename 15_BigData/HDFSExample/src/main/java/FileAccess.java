import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
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
                new URI(rootPath), configuration
        );

    }

    /**
     * Creates empty file or directory
     *
     * @param path
     */
    public void create(String path) throws IOException {
        Path file = new Path(path);
        if(hdfs.exists(file)){
            delete(path);
        }

        if(!path.matches("[^\\.]+")){
            hdfs.create(new Path(path));

        }
        else {
            hdfs.mkdirs(new Path(path));
        }
//        hdfs.close();
    }

    /**
     * Appends content to the file
     *
     * @param path
     * @param content
     */
    public void append(String path, String content) throws IOException {
        String oldContent = read(path);
        delete(path);

        OutputStream os = hdfs.create(new Path(path));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        bufferedWriter.write(oldContent);
        bufferedWriter.newLine();
        bufferedWriter.write(content);
        bufferedWriter.flush();
        bufferedWriter.close();
//        hdfs.close();
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
        hdfs.delete(new Path(path), true);
//        if(!path.matches("[^\\.]+")){
//            hdfs.delete(new Path(path), true);
//
//        }
//        else {
//            hdfs.delete(new Path(path), true);
//        }
    }

    /**
     * Checks, is the "path" is directory or file
     *
     * @param path
     * @return
     */
    public boolean isDirectory(String path) throws IOException {
        Path hdfsPath = new Path(path);

//        hdfs.isDirectory(hdfsPath);
        return hdfs.isDirectory(hdfsPath);
    }

    /**
     * Return the list of files and subdirectories on any directory
     *
     * @param path
     * @return
     */
    public List<String> list(String path) throws IOException {

        List<String> list = new LinkedList<String>();

        FileStatus statuses[] = hdfs.listStatus(new Path(path));

        for (FileStatus status : statuses) {

            list.add(status.getPath().getName());
//            System.out.println("*************************************");
//
//            System.out.println("Access Time : " + status.getAccessTime());
//            System.out.println("Block size : " + status.getBlockSize());
//            System.out.println("Group : " + status.getGroup());
//            System.out.println("Length : " + status.getLen());
//            System.out.println("Modified Time : "
//                    + status.getModificationTime());
//            System.out.println("Owner : " + status.getOwner());
//            System.out.println("Path : " + status.getPath());
//            System.out.println("Permission : " + status.getPermission());
//            System.out.println("Replication factor : "
//                    + status.getReplication());
//            System.out.println("Is Directory : " + status.isDirectory());
        }
        return list;
    }
}

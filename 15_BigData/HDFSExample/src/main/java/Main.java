import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.List;

public class Main
{
    private static String symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) throws Exception
    {
        String uri = "hdfs://8547a1f40dc9:8020";
        FileAccess fileAccess = new FileAccess(uri);

        String filePath = "/test/file.txt";

        fileAccess.create(uri + filePath);
        System.out.println(fileAccess.read(uri + filePath));
        fileAccess.append(filePath, "Hello");
        System.out.println(fileAccess.read(uri + filePath));
        fileAccess.create(uri + "/test2");
        fileAccess.create(uri + "/test3");
        fileAccess.create(uri + "/test4");

        fileAccess.delete(uri + "/test4");
        fileAccess.delete(uri + "/user");


        System.out.println("Is Directory : " + fileAccess.isDirectory(uri + filePath));

        List<String> list = fileAccess.list(uri + "/test");
        for (String l: list){
            System.out.println(l);

        }


/*        Configuration configuration = new Configuration();
        configuration.set("dfs.client.use.datanode.hostname", "true");
        System.setProperty("HADOOP_USER_NAME", "root");

        FileSystem hdfs = FileSystem.get(
            new URI("hdfs://8547a1f40dc9:8020"), configuration
        );
        Path file = new Path("hdfs://8547a1f40dc9:8020/test/file.txt");

        if (hdfs.exists(file)) {
            hdfs.delete(file, true);
        }

        OutputStream os = hdfs.create(file);
        BufferedWriter br = new BufferedWriter(
            new OutputStreamWriter(os, "UTF-8")
        );

        for(int i = 0; i < 10_000_000; i++) {
            br.write(getRandomWord() + " ");
        }

        br.flush();
        br.close();
        hdfs.close();*/
    }

    private static String getRandomWord()
    {
        StringBuilder builder = new StringBuilder();
        int length = 2 + (int) Math.round(10 * Math.random());
        int symbolsCount = symbols.length();
        for(int i = 0; i < length; i++) {
            builder.append(symbols.charAt((int) (symbolsCount * Math.random())));
        }
        return builder.toString();
    }
}

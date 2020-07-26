import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.HashMap;

public class Loader
{
    private static SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private static SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    private static HashMap<Integer, WorkTime> voteStationWorkTimes = new HashMap<>();
    private static HashMap<Voter, Integer> voterCounts = new HashMap<>();

    public static void main(String[] args) throws Exception
    {


        String fileName = "14_Performance/VoteAnalyzer/res/data-1572M.xml";


        long start = System.currentTimeMillis();


        try (StaxStreamProcessor processor = new StaxStreamProcessor(Files.newInputStream(Paths.get(fileName)))) {
            processor.startElement();
        }

        System.out.println("Parsing Duration: " + ((System.currentTimeMillis() - start) / 1000) + " s");

        DBConnection.printVoterCounts();

    }
}
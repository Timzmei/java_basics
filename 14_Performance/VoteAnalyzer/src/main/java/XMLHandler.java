import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class XMLHandler extends DefaultHandler {

    private Voter voter;
    private SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");
    private SimpleDateFormat visitDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    private HashMap<Voter, Integer> voterCounts;
    private HashMap<Integer, WorkTime> voteStationWorkTimes;

    public XMLHandler() {
        voterCounts = new HashMap<>();
        voteStationWorkTimes = new HashMap<>();
    }



    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        try {
            if(qName.equals("voter")){

                Date birthDay = birthDayFormat.parse(attributes.getValue("birthDay"));
//                String birthDayStr = new String
                voter = new Voter(attributes.getValue("name"), birthDay.getTime());
            }
            else  if (qName.equals("visit") && voter != null) {
               int count = voterCounts.getOrDefault(voter, 0);
               voterCounts.put(voter, count + 1);


                Integer station = Integer.parseInt(attributes.getValue("station"));
                Date time = visitDateFormat.parse(attributes.getValue("time"));
                WorkTime workTime = voteStationWorkTimes.get(station);
                if(workTime == null)
                {
                    workTime = new WorkTime();
                    voteStationWorkTimes.put(station, workTime);
                }
                workTime.addVisitTime(time.getTime());

            }

//            System.out.println(qName + " - started");
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals("voter")){
            voter = null;
        }
//        System.out.println(qName + " - ended");
    }

    public void printDuplicatedVoters(){
        for (Voter voter : voterCounts.keySet()){
            int count = voterCounts.get(voter);
            if(count > 1){
                System.out.println(voter.toString() + " - " + count);
            }

//            if(voterCounts.get(voter) > 1){
//                System.out.println(voter.toString() + " - " + voterCounts.get(voter));
//            }
        }
    }


    public void printStationWorkTime(){
        System.out.println("Voting station work times: ");
        for(Integer votingStation : voteStationWorkTimes.keySet())
        {
            WorkTime workTime = voteStationWorkTimes.get(votingStation);
            System.out.println("\t" + votingStation + " - " + workTime);
        }
    }


}

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkTime2 {
    private List<TimePeriod> periods;

    /**
     * Set of TimePeriod objects
     */
    public WorkTime2()
    {
        periods = new ArrayList<>();
    }

    public void addVisitTime(long visitTime)
    {
        Date visit = new Date(visitTime);
        TimePeriod newPeriod = new TimePeriod(visit, visit);
        for(TimePeriod period : periods)
        {
            if(period.compareTo(newPeriod) == 0)
            {
                period.appendTime(visit);
                return;
            }
        }
        periods.add(new TimePeriod(visit, visit));
    }

    public String toString()
    {
        String line = "";
        for(TimePeriod period : periods)
        {
            if(!line.isEmpty()) {
                line += ", ";
            }
            line += period;
        }
        return line;
    }
}

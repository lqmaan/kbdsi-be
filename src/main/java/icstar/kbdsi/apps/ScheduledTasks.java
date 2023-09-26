package icstar.kbdsi.apps;

import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduledTasks {
    SimpleDateFormat dateFormat =  new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    @Scheduled(cron = "* * * * * /1 *")
    public void everyMinuteCron(){
        System.out.println("run cron at " + dateFormat.format(new Date()));
    }

}

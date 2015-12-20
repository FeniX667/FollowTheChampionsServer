package followTheChampions.dataLoaders;

import followTheChampions.services.FootballApiCaller;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class TodayMatchesLoadingScheduler {

    @Autowired
    private FootballApiCaller footballApiCaller;

    private final int delay = 10000;
    private final int interval = 10000 * 10000; // every 10 sec

    private final static org.slf4j.Logger logger = LoggerFactory
            .getLogger(TodayMatchesLoadingScheduler.class);

    @Scheduled(fixedRate = interval, initialDelay = delay)
    public void timeout() {
        logger.info("Loading today matches");
        footballApiCaller.callTodayMatches();
    }
}

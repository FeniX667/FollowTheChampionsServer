package followTheChampions.dataLoaders;

import followTheChampions.services.FootballApiCaller;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class StandingsLoadingScheduler {

    @Autowired
    private FootballApiCaller footballApiCaller;

    private final int delay = 500;
    private final int interval = 1000 * 60 * 30 ; //every 30min

    private final static org.slf4j.Logger logger = LoggerFactory
            .getLogger(StandingsLoadingScheduler.class);

    @Scheduled(fixedRate = interval, initialDelay = delay)
    public void timeout() {
        logger.info("Loading standings");
        footballApiCaller.callStandings();
    }
}

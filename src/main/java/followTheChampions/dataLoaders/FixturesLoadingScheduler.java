package followTheChampions.dataLoaders;

import followTheChampions.services.FootballApiCaller;
import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class FixturesLoadingScheduler {

    @Autowired
    private FootballApiCaller footballApiCaller;

    private final int delay = 2000;
    private final int interval = 1000 * 60 * 60 * 12; //every 12 hours

    private final static org.slf4j.Logger logger = LoggerFactory
            .getLogger(FixturesLoadingScheduler.class);

    @Scheduled(fixedRate = interval, initialDelay = delay)
    public void timeout() {
        logger.info("Loading fixtures from -7d to +14d");
        DateTime fromDate = DateTime.now().minusDays(7);
        DateTime toDate = DateTime.now().plusDays(14);

        footballApiCaller.callFixtures(fromDate, toDate);
    }
}

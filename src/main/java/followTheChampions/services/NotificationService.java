package followTheChampions.services;

import followTheChampions.models.MatchEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final static Logger logger = LoggerFactory
            .getLogger(NotificationService.class);

    public void prepareNotification(MatchEvent event){
        logger.info("Preparing notification");
    }
}

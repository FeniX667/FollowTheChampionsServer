package followTheChampions.services;

import com.google.android.gcm.server.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AndroidNotificationPusher {

    private static final Logger logger = LoggerFactory.getLogger(AndroidNotificationPusher.class);
    private static final String apiKey = "sandbox";

    public void pushToDevices(List<String> deviceTokens, Message message) {
        if( deviceTokens.size() > 0)
            logger.info("Pushing to " + deviceTokens.size() + " Android devices.");
            try {
                logger.info("GCM: Sending message {}", message);

                Sender sender = new Sender( apiKey );
                sender.sendNoRetry(message, deviceTokens);

            } catch (Exception e) {
                logger.error("GCM caught exception: {}", e.getMessage());
            }
    }

}

package followTheChampions.services;

import com.notnoop.apns.*;
import com.notnoop.apns.internal.ReconnectPolicies;
import com.notnoop.apns.internal.Utilities;
import followTheChampions.dto.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;

@Service
public class IosNotificationPusher {

    private static final Logger logger = LoggerFactory.getLogger(IosNotificationPusher.class);
    private static final String certLocation = "classpath:APNS_cert.p12";
    private static final String certPassword = "followthechampions9";
    private static final String gatewayHost = "gateway.sandbox.push.apple.com";
    private static final int gatewayPort = 2195;

    private ApnsService apnsService;

    @Autowired
    FileLoaderService fileLoaderService;

    public void pushToDevices(List<String> deviceTokens, Notification notification) {
        if( deviceTokens.size() > 0)
            try {
                pushMessage(deviceTokens, notification);
            } catch (Exception ex) {
                logger.error("Error sending message(s).", ex);
            }
    }

    private void pushMessage(List<String> deviceTokens, Notification notification) throws Exception {
        logger.info("Pushing to " + deviceTokens.size() + " IOS devices.");
        String json = null;
        if ( deviceTokens.size() > 0 && !StringUtils.isEmpty( notification.getPayload()) ) {
            checkConnection();

            json = notification.toJson();
            int length = json.length();

            if (length > 256) {
                logger.warn("Payload is too large (" + length + " bytes); shortening!");
            }

            for (String token : deviceTokens) {
                try {
                    apnsService.push(token, json);
                } catch (Exception ex) {
                    logger.warn("Exception while sending to " + token, ex.getMessage());
                }
            }
        } else {
            logger.error("NOT SENDING " + json);
        }
    }

    @PostConstruct
    private void checkConnection() throws Exception {
        if (apnsService == null) {
            logger.info("Setting up APNS connection");

            apnsService = setupConnection();

            if (apnsService == null) {
                throw new Exception("Can't initialize APNS connection!");
            }
        }

        logger.info("Testing APNS connection");
        try {
            apnsService.testConnection();
        } catch (Exception e) {
            logger.error("TestConnection() failed!", e);
        }
    }

    private ApnsService setupConnection() throws Exception {
        logger.info("Loading cert {}", certLocation);

        InputStream stream = fileLoaderService.getResource(certLocation).getInputStream();

        if (stream == null) {
            throw new Exception("No cert found at " + certLocation);
        }

        return APNS.newService()
                .withDelegate(new ApnsDelegate() {
                    @Override
                    public void messageSent(ApnsNotification apnsNotification) {
                        String payload = new String(apnsNotification.getPayload(), StandardCharsets.UTF_8);
                        String deviceToken = Utilities.encodeHex(apnsNotification.getDeviceToken());
                        logger.info("APNS: Device {} should receive {}", deviceToken, payload);
                    }

                    @Override
                    public void messageSendFailed(ApnsNotification apnsNotification, Throwable e) {
                        String registrationId = Utilities.encodeHex(apnsNotification.getDeviceToken());
                        logger.warn("Message NOT delivered to {}" + registrationId, e);

                    }

                    @Override
                    public void connectionClosed(DeliveryError deliveryError, int i) {
                        logger.info("Connection closed, delivery error {}", deliveryError.toString());
                    }
                })
                .withReconnectPolicy(new ReconnectPolicies.EveryHalfHour())
                .withCert(stream, certPassword)
                .withGatewayDestination(gatewayHost, gatewayPort)
                .build();
    }

    @PreDestroy
    public void dispose() {
        logger.info("Destroying apnsService");
                apnsService.stop();
    }
}

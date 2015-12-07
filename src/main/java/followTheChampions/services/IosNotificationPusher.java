package followTheChampions.services;

import com.notnoop.apns.*;
import com.notnoop.apns.internal.ReconnectPolicies;
import followTheChampions.dto.Alert;
import followTheChampions.models.RegisteredDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class IosNotificationPusher {

    private static final Logger logger = LoggerFactory.getLogger(IosNotificationPusher.class);
    private static final String certLocation = "classpath:APNS_cert.p12";
    private static final String certPassword = "followthechampions9";
    private static final String gatewayHost = "gateway.sandbox.push.apple.com";
    private static final int gatewayPort = 2195;

    private ApnsService apnsService;

    @Autowired
    LoaderService loaderService;

    public void pushToDevices(List<String> deviceTokens, Alert alert) {
        try {
            pushMessage(deviceTokens, alert);
            logger.info("Notification sent: " + alert.getMessage());
        } catch (Exception ex) {
            logger.error("Error sending message(s).", ex);
        }
    }

    private void pushMessage(List<String> deviceTokens, Alert alert) throws Exception {
        for (String token : deviceTokens) {
            logger.debug("sending to " + token);
        }
        logger.info("Pushing to " + deviceTokens.size() + " devices.");

        String json = null;
        if ( deviceTokens.size() > 0 && !StringUtils.isEmpty( alert.getMessage()) ) {
            checkConnection();

            json = alert.toJson();
            logger.debug("json:\n" + json);
            int length = json.length();

            logger.debug("payload size=" + length);

            if (length > 256) {
                logger.warn("Payload is too large (" + length + " bytes); shortening!");
            }

            for (String token : deviceTokens) {
                try {
                    logger.debug("Sending to " + token);
                    apnsService.push(token, json);
                } catch (Exception ex) {
                    logger.warn("Exception while sending to " + token, ex.getMessage());
                }
            }
        } else {
            logger.debug("NOT SENDING " + json);
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

        InputStream stream = loaderService.getResource(certLocation).getInputStream();

        if (stream == null) {
            throw new Exception("No cert found at " + certLocation);
        }

        return APNS.newService()
                .withDelegate(new ApnsDelegate() {
                    @Override
                    public void messageSent(ApnsNotification apnsNotification) {

                    }

                    @Override
                    public void messageSendFailed(ApnsNotification apnsNotification, Throwable throwable) {

                    }

                    @Override
                    public void connectionClosed(DeliveryError deliveryError, int i) {

                    }
                })
                .withReconnectPolicy(new ReconnectPolicies.EveryHalfHour())
                .withCert(stream, certPassword)
                .withGatewayDestination(gatewayHost, gatewayPort)
                .build();
    }
}

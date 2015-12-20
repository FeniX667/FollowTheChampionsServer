package followTheChampions.services;

import com.google.android.gcm.server.Message;
import followTheChampions.dao.MatchRepository;
import followTheChampions.dao.RegisteredDeviceRepository;
import followTheChampions.dto.Notification;
import followTheChampions.models.Match;
import followTheChampions.models.MatchEvent;
import followTheChampions.models.RegisteredDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    private final static Logger logger = LoggerFactory
            .getLogger(NotificationService.class);

    @Autowired
    IosNotificationPusher iosNotificationPusher;

    @Autowired
    AndroidNotificationPusher androidNotificationPusher;

    @Autowired
    RegisteredDeviceRepository registeredDeviceRepository;

    @Autowired
    MatchRepository matchRepository;

    @Async
    public void sendAsNotification(MatchEvent event){
        logger.info("Preparing notification");

        List<RegisteredDevice> deviceList = getDevicesWhichFavourThis(event.getMatch());

        iosNotificationPusher.pushToDevices( getTokensFromDevices(deviceList, RegisteredDevice.Type.IOS), createIosAlert(event) );
        androidNotificationPusher.pushToDevices( getTokensFromDevices(deviceList, RegisteredDevice.Type.Android), createAndroidMessage(event) );
    }

    @Async
    public void sendAsNotification(Match match){
        logger.info("Preparing notification");

        List<RegisteredDevice> deviceList = getDevicesWhichFavourThis( match );
        logger.info("Sending to {} devices", deviceList.size());

        iosNotificationPusher.pushToDevices( getTokensFromDevices(deviceList, RegisteredDevice.Type.IOS), createIosAlert(match) );
        androidNotificationPusher.pushToDevices( getTokensFromDevices(deviceList, RegisteredDevice.Type.Android), createAndroidMessage(match) );
    }

    private List<RegisteredDevice> getDevicesWhichFavourThis(Match match) {
        LinkedList<RegisteredDevice> deviceList = new LinkedList<>();
        LinkedList<RegisteredDevice> interestedInTeams = new LinkedList<>();
        LinkedList<RegisteredDevice> interestedInMatch = new LinkedList<>();

        interestedInTeams.addAll(registeredDeviceRepository.getInterestedDevicesByTeams(match.getLocalTeam(), match.getVisitorTeam()));
        interestedInMatch.addAll(registeredDeviceRepository.getInterestedDevicesByMatch(match));

        deviceList.addAll(interestedInTeams);
        deviceList.addAll(interestedInMatch);

        return deviceList;
    }

    private List<String> getTokensFromDevices(List<RegisteredDevice> devices, RegisteredDevice.Type type){
        LinkedList<String> tokenList = new LinkedList<>();
        for(RegisteredDevice device : devices)
            if (device.getType().equals(type))
                tokenList.add(device.getDeviceToken());

        return tokenList;
    }

    private Notification createIosAlert(MatchEvent event){
        Notification notification = new Notification();

        Map<String, String> aps = new HashMap<>();
        aps.put("category", "NEW_MESSAGE_CATEGORY");
        aps.put("alert", event.getMatch().getMatchName() + event.getType() );

        notification.getPayload().put("aps", aps);
        notification.getPayload().put("matchId", event.getMatch().getId().toString());
        notification.getPayload().put("time", event.getMinute());
        notification.getPayload().put("whichTeam", event.getWhichTeam());
        notification.getPayload().put("type", event.getType());
        notification.getPayload().put("player", event.getPlayerName());
        notification.getPayload().put("result", event.getResult());

        return notification;
    }

    private Notification createIosAlert(Match match){
        Notification notification = new Notification();

        Map<String, String> aps = new HashMap<>();
        aps.put("category", "NEW_MESSAGE_CATEGORY");

        String status = match.getStatus();
        switch(match.getStatus()){
            case "1" :
                aps.put("alert", match.getMatchName() + " started! " );
                break;

            case "HT" :
                aps.put("alert", match.getMatchName() + " first half finished. " );
                break;

            case "46" :
                aps.put("alert", match.getMatchName() + " second half started. " );
                break;

            case "FT" :
                aps.put("alert", match.getMatchName() + " is finished. " );
                break;
        }

        notification.getPayload().put("aps", aps);
        notification.getPayload().put("matchId", match.getId().toString());

        return notification;
    }
    private Message createAndroidMessage(MatchEvent event) {
        Message.Builder messageBuilder =  new Message.Builder();
        messageBuilder
                .addData("text", "Testing message!")
                .addData("match", event.getMatch().getId().toString())
                .addData("time", event.getMinute())
                .addData("whichTeam", event.getWhichTeam())
                .addData("type", event.getType())
                .addData("player", event.getPlayerName())
                .addData("result", event.getResult())
                .delayWhileIdle(true)
                .collapseKey(event.getType())
                .timeToLive(30 * 60);

        return messageBuilder.build();
    }

    private Message createAndroidMessage(Match match) {
        Message.Builder messageBuilder =  new Message.Builder();
        String message = new String();

        String status = match.getStatus();
        switch(match.getStatus()){
            case "1" :
                message = match.getMatchName() + " started! ";
                break;

            case "HT" :
                message = match.getMatchName() + " first half finished. " ;
                break;

            case "46" :
                message = match.getMatchName() + " second half started. " ;
                break;

            case "FT" :
                message = match.getMatchName() + " is finished. " ;
                break;
        }

        messageBuilder
                .addData("text", message)
                .addData("match", match.getId().toString())
                .addData("status", status)
                .delayWhileIdle(true)
                .collapseKey(status)
                .timeToLive(30 * 60);

        return messageBuilder.build();
    }

    //@Async
    public void fakeNotification(){
        logger.info("Preparing single notification");

        MatchEvent event = new MatchEvent();
        event.setId(99L);
        event.setWhichTeam("local");
        event.setResult("[1:0]");
        event.setPlayerName("Malar");
        event.setType("green card");
        event.setMinute("30");
        event.setMatch( matchRepository.getById(2L) );

        sendAsNotification(event);
    }

    public void fakeStatusNotification() {
        logger.info("Preparing status notification");
        Match match = matchRepository.getById(1L);
        match.setStatus("1");
        sendAsNotification(match);

        match.setStatus("46");
        sendAsNotification(match);

        match.setStatus("FT");
        sendAsNotification(match);
    }
}

package followTheChampions.services;

import com.google.android.gcm.server.Message;
import followTheChampions.dao.MatchEventRepository;
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

    private static String CARD_SOUND = "card_sound.mp3";
    private static String GOAL_SOUND = "goal_sound.mp3";
    private static String HALF_START_SOUND = "half_start_sound.mp3";
    private static String FIRST_HALF_END_SOUND = "first_half_end_sound.mp3";
    private static String MATCH_END_SOUND = "match_end_sound.mp3";

    @Autowired
    IosNotificationPusher iosNotificationPusher;

    @Autowired
    AndroidNotificationPusher androidNotificationPusher;

    @Autowired
    RegisteredDeviceRepository registeredDeviceRepository;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    MatchEventRepository matchEventRepository;

    //@Async
    public void sendAsNotification(MatchEvent event){
        logger.info("Preparing notification");

        List<RegisteredDevice> deviceList = getDevicesWhichFavourThis(event.getMatch());

        iosNotificationPusher.pushToDevices( getTokensFromDevices(deviceList, RegisteredDevice.Type.IOS), createIosAlert(event) );
        androidNotificationPusher.pushToDevices( getTokensFromDevices(deviceList, RegisteredDevice.Type.Android), createAndroidMessage(event) );
    }

    //@Async
    public void sendAsNotification(Match match){
        logger.info("Preparing notification");

        List<RegisteredDevice> deviceList = getDevicesWhichFavourThis( match );

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
        interestedInMatch.stream().filter(device -> !deviceList.contains(device)).forEach(deviceList::add);

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

        switch(event.getType()){
            case "goal":
                notification.getPayload().put("sound", GOAL_SOUND);
                break;

            case "yellowcard":
                notification.getPayload().put("sound", CARD_SOUND);
                break;

            case "redcard":
                notification.getPayload().put("sound", CARD_SOUND);
                break;
        }


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
                aps.put("sound", this.HALF_START_SOUND);
                break;

            case "HT" :
                aps.put("alert", match.getMatchName() + " first half finished. " );
                aps.put("sound", this.FIRST_HALF_END_SOUND);
                break;

            case "46" :
                aps.put("alert", match.getMatchName() + " second half started. " );
                aps.put("sound", this.HALF_START_SOUND);
                break;

            case "FT" :
                aps.put("alert", match.getMatchName() + " is finished. " );
                aps.put("sound", this.MATCH_END_SOUND);
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
        String sound = new String();
        String status = match.getStatus();
        switch(match.getStatus()){
            case "1" :
                message = match.getMatchName() + " started! ";
                sound = this.HALF_START_SOUND;
                break;

            case "HT" :
                message = match.getMatchName() + " first half finished. " ;
                sound = this.FIRST_HALF_END_SOUND;
                break;

            case "46" :
                message = match.getMatchName() + " second half started. " ;
                sound = this.HALF_START_SOUND;
                break;

            case "FT" :
                message = match.getMatchName() + " is finished. " ;
                sound = this.MATCH_END_SOUND;
                break;
        }

        messageBuilder
                .addData("text", message)
                .addData("match", match.getId().toString())
                .addData("status", status)
                .addData("sound", sound)
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

        try {
            Thread.sleep(10000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        MatchEvent fakeMatchEvent = new MatchEvent();
        fakeMatchEvent.setMinute("15");
        fakeMatchEvent.setWhichTeam("local");
        fakeMatchEvent.setMatch(match);
        fakeMatchEvent.setId(1L);
        fakeMatchEvent.setPlayerName("Malarinhio");
        fakeMatchEvent.setType("goal");
        fakeMatchEvent.setResult("[1 - 0");

        matchEventRepository.save(fakeMatchEvent);
        match.getMatchEventList().add(fakeMatchEvent);

        sendAsNotification(fakeMatchEvent);

        try {
            Thread.sleep(10000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        MatchEvent fakeMatchEvent2 = new MatchEvent();
        fakeMatchEvent2.setMinute("23");
        fakeMatchEvent2.setWhichTeam("local");
        fakeMatchEvent2.setMatch(match);
        fakeMatchEvent2.setId(2L);
        fakeMatchEvent2.setPlayerName("Malarinhio");
        fakeMatchEvent2.setType("yellowcard");

        matchEventRepository.save(fakeMatchEvent);
        match.getMatchEventList().add(fakeMatchEvent);

        sendAsNotification(fakeMatchEvent2);

        try {
            Thread.sleep(10000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        match.setStatus("HT");
        sendAsNotification(match);

        try {
            Thread.sleep(10000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        match.setStatus("46");
        sendAsNotification(match);

        try {
            Thread.sleep(10000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        match.setStatus("FT");
        sendAsNotification(match);

        matchEventRepository.delete(1L);
        matchEventRepository.delete(2L);
    }
}

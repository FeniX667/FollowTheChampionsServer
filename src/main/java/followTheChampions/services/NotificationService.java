package followTheChampions.services;

import com.google.android.gcm.server.Message;
import followTheChampions.dao.MatchRepository;
import followTheChampions.dao.RegisteredDeviceRepository;
import followTheChampions.dto.Alert;
import followTheChampions.models.Match;
import followTheChampions.models.MatchEvent;
import followTheChampions.models.RegisteredDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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

    private List<RegisteredDevice> getDevicesWhichFavourThis(Match match) {
        LinkedList<RegisteredDevice> deviceList = new LinkedList<>();
        for(RegisteredDevice device : registeredDeviceRepository.findAll()){
            deviceList.add(device);
        }
        return deviceList;
    }

    private List<String> getTokensFromDevices(List<RegisteredDevice> devices, RegisteredDevice.Type type){
        LinkedList<String> tokenList = new LinkedList<>();
        for(RegisteredDevice device : devices)
            if (device.getType().equals(type))
                tokenList.add(device.getDeviceToken());

        return tokenList;
    }

    private Alert createIosAlert(MatchEvent event){
        Alert alert = new Alert();

        alert.setMessage("Testing message!");
        alert.getPayload().put("match", event.getMatch().getId().toString());
        alert.getPayload().put("time", event.getMinute());
        alert.getPayload().put("whichTeam", event.getWhichTeam());
        alert.getPayload().put("type", event.getType());
        alert.getPayload().put("player", event.getPlayerName());
        alert.getPayload().put("result", event.getResult());

        return alert;
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

    //@Async
    public void fakeNotification(){
        logger.info("Preparing notification");

        MatchEvent event = new MatchEvent();
        event.setId(99L);
        event.setWhichTeam("local");
        event.setResult("[1:0]");
        event.setPlayerName("Malar");
        event.setType("green card");
        event.setMinute("30");
        event.setMatch( matchRepository.getById(0L) );

        sendAsNotification(event);
    }
}

package followTheChampions.controllers;

import followTheChampions.dao.MatchEventRepository;
import followTheChampions.dao.RegisteredDeviceRepository;
import followTheChampions.models.Competition;
import followTheChampions.models.MatchEvent;
import followTheChampions.models.RegisteredDevice;
import followTheChampions.models.Team;
import followTheChampions.services.FootballApiCaller;
import followTheChampions.services.NotificationService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


@RequestMapping("/admin")
@RestController
public class AdminApiController {

    public final static String SEASON_START_DATE = "08.08.2015";

    @Autowired
    FootballApiCaller firstApiCaller;

    @Autowired
    NotificationService notificationService;

    @Autowired
    RegisteredDeviceRepository registeredDeviceRepository;

    private final static Logger logger = LoggerFactory
            .getLogger(AdminApiController.class);

    @RequestMapping(value = "/populateDbFromApi", method = RequestMethod.GET)
    public ResponseEntity<String> populateDbFromApi() throws ParseException {
        ResponseEntity<String> response;

        firstApiCaller.callCompetition();
        firstApiCaller.callStandings();
        this.callFixturesAllSeason();

        response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @RequestMapping("/callCompetition")
    public ResponseEntity<String> callCompetition() {
        ResponseEntity<String> response;

        firstApiCaller.callCompetition();

        response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @RequestMapping("/callStandings")
    public ResponseEntity<String> callStandings() {
        ResponseEntity<String> response;

        firstApiCaller.callStandings();

        response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @RequestMapping("/callTodayMatches")
    public ResponseEntity<String> callTodayMatches() {
        ResponseEntity<String> response;

        firstApiCaller.callTodayMatches();

        response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @RequestMapping("/callFixturesAllSeason")
    public ResponseEntity<String> callFixturesAllSeason() throws ParseException {
        ResponseEntity<String> response;

        DateFormat format = new SimpleDateFormat("dd.mm.yyyy", Locale.ENGLISH);

        DateTime fromDate = new DateTime(format.parse(SEASON_START_DATE));
        DateTime toDate = DateTime.now();

        firstApiCaller.callFixtures(fromDate, toDate);

        response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @RequestMapping("/callFixturesLastTwoWeeks")
    public ResponseEntity<String> callFixturesLastTwoWeeks() {

        ResponseEntity<String> response;
        DateTime fromDate = DateTime.now().minusDays(14);
        DateTime toDate = DateTime.now();

        firstApiCaller.callFixtures(fromDate, toDate);

        response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @RequestMapping("/testAlert")
    public ResponseEntity<String> testAlert()  {
        ResponseEntity<String> response;

        notificationService.fakeNotification();

        response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @RequestMapping("/getRegisteredDevices")
    public @ResponseBody
    Iterable<RegisteredDevice> getRegisteredDevices() {
        logger.info("Searching for devices");
        return registeredDeviceRepository.findAll();
    }


    @RequestMapping("/dumpDb")
    public ResponseEntity<String> dumpDb() throws SQLException {

        ResponseEntity<String> response;

        String[] args = {
            "-url",
            "jdbc:h2:path/ftc",
            "-user",
            "admin",
            "-password",
            "secret"
        };

        String[] args2 = {
                "-dir",
                "D:\\Projekty\\FollowTheChampionsServer\\path",
                "-db",
                "ftc",
                "-file",
                "dump",
        };

        org.h2.tools.Script.main(args);
        org.h2.tools.Backup.main(args2);

        response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @RequestMapping("/restoreDb")
    public ResponseEntity<String> restoreDb() throws SQLException {

        ResponseEntity<String> response;


        String[] args = {
                "-dir",
                "D:\\Projekty\\FollowTheChampionsServer\\path",
                "-db",
                "ftc",
                "-file",
                "dump",
        };

        org.h2.tools.Restore.main(args);

        response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }
}

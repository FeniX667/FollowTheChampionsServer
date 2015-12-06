package followTheChampions.controllers;

import followTheChampions.dao.*;
import followTheChampions.models.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RequestMapping("/mobile")

@RestController
public class MobileApiController {

    @Autowired
    CompetitionRepository competitionRepository;

    @Autowired
    FavouritedMatchRepository favouritedMatchRepository;

    @Autowired
    FavouritedTeamRepository favouritedTeamRepository;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    MatchEventRepository matchEventRepository;

    @Autowired
    RegisteredDeviceRepository registeredDeviceRepository;

    @Autowired
    StandingRepository standingRepository;

    @Autowired
    TeamRepository teamRepository;

    private final static Logger logger = LoggerFactory
            .getLogger(MobileApiController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        logger.info("Default call");

        return "Greetings from Follow The Champions!";
    }

    @RequestMapping("/registerDevice")
    public ResponseEntity<String> registerDevice(String deviceToken) {
        logger.info("Registering device " +deviceToken);
        ResponseEntity<String> response;

        try {
            RegisteredDevice registeredDevice = new RegisteredDevice();
            registeredDevice.setDeviceToken(deviceToken);
            registeredDevice.setIsActive(Boolean.TRUE);
            registeredDevice.setRegistrationDate(DateTime.now().toDate());
            registeredDevice.setType(deviceToken.length() == 64 ? RegisteredDevice.Type.IOS : RegisteredDevice.Type.Android);

            registeredDeviceRepository.save(registeredDevice);
        }catch(Exception e){
            logger.error("Registration failed for id {}", deviceToken);
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return response;
        }

        response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @RequestMapping("/competitionData")
    public @ResponseBody ResponseEntity<Competition> competitionData() {
        logger.info("Retrieving competition data");
        ResponseEntity<Competition> response;

        try {
            response = ResponseEntity.ok().body(competitionRepository.findAll().iterator().next());
        }catch(Exception e){
            logger.error("Retrieving failed");
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return response;
        }

        return response;
    }

    @RequestMapping("/searchForTeams")
    public @ResponseBody Iterable<Team> searchForTeams() {
        logger.info("Searching for teams");
        return teamRepository.findAll();
    }

    @RequestMapping("/addFavouritedTeam")
    public ResponseEntity<String> addFavouritedTeam(String deviceToken, String teamId) {
        logger.info("Adding favourited team");
        ResponseEntity<String> response;

        try {
            RegisteredDevice registeredDevice = registeredDeviceRepository.getByDeviceToken(deviceToken);
            Team team = teamRepository.getById( new Long(teamId) );

            FavouritedTeam favouritedTeam = new FavouritedTeam();
            favouritedTeam.setRegisteredDevice(registeredDevice);
            favouritedTeam.setTeam(team);
            favouritedTeamRepository.save(favouritedTeam);

            response = new ResponseEntity<>(HttpStatus.OK);

        }catch(Exception e){
            logger.error("failed with exception {}", e.getMessage());
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return response;
        }

        return response;
    }

    @RequestMapping("/removeFavouritedTeam")
    public ResponseEntity<String>  removeFavouritedTeam(String deviceToken, String teamId) {
        logger.info("Removing favourited team");

        RegisteredDevice device = registeredDeviceRepository.getByDeviceToken(deviceToken);
        Team team =teamRepository.getById( Long.parseLong(teamId) );

        List<FavouritedTeam> favouritedTeams = favouritedTeamRepository.getByDeviceTokenTeamId(device, team);

        favouritedTeams.forEach(favouritedTeamRepository::delete);

        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @RequestMapping("/getFavouritedTeams")
    public @ResponseBody List<FavouritedTeam> getFavouritedTeams(String deviceToken) {
        logger.info("Getting all favourited teams");

        RegisteredDevice registeredDevice = registeredDeviceRepository.getByDeviceToken(deviceToken);

        return favouritedTeamRepository.getByRegisteredDevice(registeredDevice);
    }

    @RequestMapping("/searchForMatches")
    public @ResponseBody Iterable<Match> searchForMatches() {
        logger.info("Searching for matches");
        return matchRepository.findAll();
    }

    @RequestMapping("/addFavouritedMatch")
    public ResponseEntity<String> addFavouritedMatch(String deviceToken, String matchId) {
        logger.info("Adding favourited match");
        ResponseEntity<String> response;

        try {
            RegisteredDevice registeredDevice = registeredDeviceRepository.getByDeviceToken(deviceToken);
            Match match = matchRepository.getById(new Long(matchId));

            FavouritedMatch favouritedMatch = new FavouritedMatch();
            favouritedMatch.setRegisteredDevice(registeredDevice);
            favouritedMatch.setMatch(match);
            favouritedMatchRepository.save(favouritedMatch);

            response = new ResponseEntity<>(HttpStatus.OK);

        }catch (Exception e){
            logger.error("failed with exception {}", e.getMessage());
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return response;
        }

        return response;
    }

    @RequestMapping("/removeFavouritedMatch")
    public ResponseEntity<String> removeFavouritedMatch(String deviceToken, String matchId) {
        logger.info("Removing favourited match");

        RegisteredDevice device = registeredDeviceRepository.getByDeviceToken(deviceToken);
        Match match = matchRepository.getById( Long.parseLong(matchId) );

        List<FavouritedMatch> favouritedMatches = favouritedMatchRepository.getByDeviceTokenTeamId(device, match);

        favouritedMatches.forEach(favouritedMatchRepository::delete);

        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @RequestMapping("/getFavouritedMatches")
    public @ResponseBody Iterable<FavouritedMatch> getFavouritedMatches(String deviceToken) {
        logger.info("Getting favourited matches");

        RegisteredDevice registeredDevice = registeredDeviceRepository.getByDeviceToken(deviceToken);

        return favouritedMatchRepository.getByRegisteredDevice(registeredDevice);
    }

    @RequestMapping("/getStandings")
    public @ResponseBody Iterable<Standing> getStandings() {
        logger.info("Getting standings");
        return standingRepository.findAll();
    }

    @RequestMapping("/getMatchesForTeam")
    public @ResponseBody ResponseEntity<List<Match>> getMatchesForTeam(String teamId) {
        logger.info("Getting matches for team");

        Team team = teamRepository.getById( Long.parseLong(teamId) );

        ResponseEntity<List<Match>> response;
        response = ResponseEntity.ok().body( matchRepository.getByTeamId(team) );

        return response;
    }
}

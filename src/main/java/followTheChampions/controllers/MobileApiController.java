package followTheChampions.controllers;

import com.google.common.collect.Lists;
import followTheChampions.dao.*;
import followTheChampions.models.*;
import followTheChampions.services.FirstApiCaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.LinkedList;
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

        return "Greetings from Follow The Champions!";
    }

    @RequestMapping("/registerDevice")
    public ResponseEntity<String> registerDevice(String deviceToken) {
        logger.info("Registering device " +deviceToken);
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @RequestMapping("/competitionData")
    public @ResponseBody ResponseEntity<Competition> competitionData() {

        logger.info("Retrieving competition data");
        ResponseEntity<Competition> response;
        response = ResponseEntity.ok().body(competitionRepository.findAll().iterator().next());
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

        RegisteredDevice registeredDevice = registeredDeviceRepository.getByDeviceToken(deviceToken);
        Team team = teamRepository.getById( new Long(teamId) );

        FavouritedTeam favouritedTeam = new FavouritedTeam();
        favouritedTeam.setRegisteredDevice(registeredDevice);
        favouritedTeam.setTeam(team);
        favouritedTeamRepository.save(favouritedTeam);

        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @RequestMapping("/removeFavouritedTeam")
    public ResponseEntity<String>  removeFavouritedTeam(String deviceToken, String teamId) {
        logger.info("Removing favourited team");


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
        Iterable<Match> matches = matchRepository.findAll();

        return matches;
    }

    @RequestMapping("/addFavouritedMatch")
    public ResponseEntity<String> addFavouritedMatch(String deviceToken, String matchId) {
        RegisteredDevice registeredDevice = registeredDeviceRepository.getByDeviceToken(deviceToken);
        Match match = matchRepository.getById( new Long(matchId) );

        FavouritedMatch favouritedMatch = new FavouritedMatch();
        favouritedMatch.setRegisteredDevice(registeredDevice);
        favouritedMatch.setMatch(match);
        favouritedMatchRepository.save(favouritedMatch);


        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @RequestMapping("/removeFavouritedMatch")
    public ResponseEntity<String> removeFavouritedMatch(String deviceToken, String matchId) {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @RequestMapping("/getFavouritedMatches")
    public @ResponseBody Iterable<FavouritedMatch> getFavouritedMatches(String deviceToken) {

        RegisteredDevice registeredDevice = registeredDeviceRepository.getByDeviceToken(deviceToken);

        return favouritedMatchRepository.getByRegisteredDevice(registeredDevice);
    }

    @RequestMapping("/getStandings")
    public @ResponseBody Iterable<Standing> getStandings() {
        return standingRepository.findAll();
    }
}

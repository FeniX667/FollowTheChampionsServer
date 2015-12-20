package followTheChampions.controllers;

import followTheChampions.dao.*;
import followTheChampions.dto.MatchPersonalizedDTO;
import followTheChampions.dto.StandingsPersonalizedDTO;
import followTheChampions.dto.TeamPersonalizedDTO;
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

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


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


        RegisteredDevice registeredDevice = registeredDeviceRepository.getByDeviceToken(deviceToken);
        if( registeredDevice!=null )
            logger.error("Device with token {} already registered", deviceToken);
        else{
            try {
                registeredDevice = new RegisteredDevice();
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

    @RequestMapping("/searchForTeamsPersonalized")
    public @ResponseBody List<TeamPersonalizedDTO> searchForTeamsPersonalized(String deviceToken) {
        logger.info("Searching for personalized teams");
        List<TeamPersonalizedDTO> teamPersonalizedDTOs = new LinkedList<>();
        RegisteredDevice registeredDevice = registeredDeviceRepository.getByDeviceToken(deviceToken);

        Iterable<Team> teams = teamRepository.findAll();
        List<FavouritedTeam> favTeams = favouritedTeamRepository.getByRegisteredDevice(registeredDevice);
        List<Team> teamsExtracted = new LinkedList<>();

        for(FavouritedTeam favouritedTeam : favTeams){
            teamsExtracted.add(favouritedTeam.getTeam());
        }

        for(Team team : teams){
            if( teamsExtracted.contains(team) )
                teamPersonalizedDTOs.add( new TeamPersonalizedDTO(team, Boolean.TRUE));
            else
                teamPersonalizedDTOs.add( new TeamPersonalizedDTO(team, Boolean.FALSE));
        }

        return teamPersonalizedDTOs;
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

    @RequestMapping("/searchForMatchesPersonalized")
    public @ResponseBody List<MatchPersonalizedDTO> searchForMatchesPersonalized(String deviceToken) {
        logger.info("Searching for personalized matches");
        List<MatchPersonalizedDTO> matchPersonalizedDTOs = new LinkedList<>();
        RegisteredDevice registeredDevice = registeredDeviceRepository.getByDeviceToken(deviceToken);

        Iterable<Match> matches = matchRepository.findAll();
        List<FavouritedMatch> favMatches = favouritedMatchRepository.getByRegisteredDevice(registeredDevice);
        List<Match> matchesExtracted = favMatches.stream().map(FavouritedMatch::getMatch).collect(Collectors.toCollection(() -> new LinkedList<>()));

        for(Match match : matches){
            if( matchesExtracted.contains(match) )
                matchPersonalizedDTOs.add( new MatchPersonalizedDTO(match, Boolean.TRUE));
            else
                matchPersonalizedDTOs.add( new MatchPersonalizedDTO(match, Boolean.FALSE));
        }

        return matchPersonalizedDTOs;
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

    @RequestMapping("/getStandingsPersonalized")
    public @ResponseBody ResponseEntity<List<StandingsPersonalizedDTO>> getStandingsPersonalized(String deviceToken) {
        logger.info("Searching for personalized standings");
        ResponseEntity<List<StandingsPersonalizedDTO>> response;
        List<StandingsPersonalizedDTO> standingsPersonalizedDTOs = new LinkedList<>();

        try {
            RegisteredDevice registeredDevice = registeredDeviceRepository.getByDeviceToken(deviceToken);

            Iterable<Standing> standings = standingRepository.findAll();
            List<FavouritedTeam> favTeams = favouritedTeamRepository.getByRegisteredDevice(registeredDevice);
            List<Long> teamsExtracted = favTeams.stream().map(favTeam -> favTeam.getTeam().getId()).collect(Collectors.toCollection(() -> new LinkedList<>()));

            for (Standing standing : standings) {
                if (teamsExtracted.contains(standing.getStandTeamId()))
                    standingsPersonalizedDTOs.add(new StandingsPersonalizedDTO(standing, Boolean.TRUE));
                else
                    standingsPersonalizedDTOs.add(new StandingsPersonalizedDTO(standing, Boolean.FALSE));
            }
        }catch(Exception e){
            logger.error("failed with exception {}", e.getMessage());
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return response;
        }

        response = ResponseEntity.ok().body( standingsPersonalizedDTOs );
        return response;
    }

    @RequestMapping("/getMatchesForTeam")
    public @ResponseBody ResponseEntity<List<Match>> getMatchesForTeam(String teamId) {
        logger.info("Getting matches for team");

        Team team = teamRepository.getById( Long.parseLong(teamId) );

        ResponseEntity<List<Match>> response;
        response = ResponseEntity.ok().body( matchRepository.getByTeamId(team) );

        return response;
    }

    @RequestMapping("/getAllFavouritedMatches")
    public @ResponseBody ResponseEntity<List<Match>> getAllFavouritedMatches(String deviceToken) {
        logger.info("Getting favourited matches and matches of favourited teams");
        ResponseEntity<List<Match>> response;
        List<Match> matches = new LinkedList<>();
        try {
            RegisteredDevice registeredDevice = registeredDeviceRepository.getByDeviceToken(deviceToken);

            matches = matchRepository.getAllFavouritedMatches( registeredDevice );
        } catch (Exception e){
            response = ResponseEntity.badRequest().body(matches);
            return response;
        }

        response = ResponseEntity.ok().body( matches );

        return response;
    }
}

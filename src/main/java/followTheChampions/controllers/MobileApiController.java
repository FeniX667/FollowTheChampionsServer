package followTheChampions.controllers;

import followTheChampions.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.LinkedList;
import java.util.List;


@RequestMapping("/mobile")

@RestController
public class MobileApiController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/registerDevice")
    public ResponseEntity<String> registerDevice() {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return response;
    }

    @RequestMapping("/competitionData")
    public @ResponseBody Competition competitionData() {
        Competition sampleLeague = new Competition();
        sampleLeague.setId(1204L);
        sampleLeague.setName("Premier League");
        sampleLeague.setRegion("England");

        return sampleLeague;
    }

    @RequestMapping("/searchForTeams")
    public @ResponseBody List<Team> searchForTeams() {
        LinkedList<Team> teams = new LinkedList<Team>();
        teams.add(new Team());
        return teams;
    }

    @RequestMapping("/addFavouritedTeam")
    public ResponseEntity<String> addFavouritedTeam() {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return response;
    }

    @RequestMapping("/removeFavouritedTeam")
    public ResponseEntity<String>  removeFavouritedTeam() {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return response;
    }

    @RequestMapping("/getFavouritedTeams")
    public @ResponseBody List<FavouritedTeam> getFavouritedTeams() {
        LinkedList<FavouritedTeam> favouritedTeams= new LinkedList<>();
        favouritedTeams.add(new FavouritedTeam());
        return favouritedTeams;
    }

    @RequestMapping("/searchForMatches")
    public @ResponseBody List<Match> searchForMatches() {
        LinkedList<Match> matches= new LinkedList<>();
        matches.add(new Match());
        return matches;
    }

    @RequestMapping("/addFavouritedMatch")
    public ResponseEntity<String> addFavouritedMatch() {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @RequestMapping("/removeFavouritedMatch")
    public ResponseEntity<String> removeFavouritedMatch() {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
        return response;
    }

    @RequestMapping("/getFavouritedMatches")
    public @ResponseBody List<FavouritedMatch> getFavouritedMatches() {
        LinkedList<FavouritedMatch> favouritedMatches= new LinkedList<>();
        favouritedMatches.add(new FavouritedMatch());
        return favouritedMatches;
    }

    @RequestMapping("/getStandings")
    public @ResponseBody List<Standing> getStandings() {
        LinkedList<Standing> standings= new LinkedList<>();
        standings.add(new Standing());
        return standings;
    }
}

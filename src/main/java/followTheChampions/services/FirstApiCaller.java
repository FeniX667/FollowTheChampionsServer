package followTheChampions.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import followTheChampions.controllers.MobileApiController;
import followTheChampions.dao.*;
import followTheChampions.models.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
public class FirstApiCaller {

    public final static String API_KEY="64bc654e-5926-9cfe-384b14a72f1a"; // Solskiego
    //public final static String API_KEY="8b38ab51-3460-9ea3-73f697a040e3";   // Magda
    public final static String COMP_ID="1204";

    public final static String COMPETITION_URL = "http://football-api.com/api/?Action=competitions&APIKey=" +API_KEY;
    public final static String STANDINGS_URL = "http://football-api.com/api/?Action=standings&APIKey=" +API_KEY+ "&comp_id=" +COMP_ID;
    public final static String TODAY_MATCHES_URL = "http://football-api.com/api/?Action=today&APIKey=" +API_KEY+ "&comp_id=" +COMP_ID;
    public final static String FIXTURES_URL = "http://football-api.com/api/?Action=fixtures&APIKey=" +API_KEY+ "&comp_id=" +COMP_ID; //&from_date=d.m.y&to_date=d.m.y

    private final static Logger logger = LoggerFactory
            .getLogger(FirstApiCaller.class);

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

    @Autowired
    NotificationService notificationService;

    public void callCompetition() {
        logger.info( "Running against competitionURL" );

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity;
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> mappedResponse = new HashMap<>();
        List<Map<String, Object>> competitionList;

        responseEntity = restTemplate.getForEntity(COMPETITION_URL, String.class);

        //Retrieving jsonized competition from response
        String jsonResponse = (String) responseEntity.getBody();
        try {
            mappedResponse = mapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        competitionList = (List<Map<String, Object>>) mappedResponse.get("Competition");

        //Comparing received data do db data
        Map<String, Object> mappedCompetition = competitionList.get(0);

        Competition newCompetition = new Competition();
        newCompetition.setId( Long.valueOf(mappedCompetition.get("id").toString()) );
        newCompetition.setName( (String) mappedCompetition.get("name") );
        newCompetition.setRegion((String) mappedCompetition.get("region"));

        Competition existingCompetition = competitionRepository.getById( newCompetition.getId() );

        if( existingCompetition != null ){
            existingCompetition.setName( newCompetition.getName() );
            existingCompetition.setRegion( newCompetition.getRegion() );
            competitionRepository.save(existingCompetition);

            logger.info("Existing competition updated");
        }
        else{
            competitionRepository.save(newCompetition);

            logger.info("Saved as new competition");
        }

        logger.info("Running against competitionURL finished.");
    }

    //http://football-api.com/api/?Action=standings&APIKey=[YOUR_API_KEY]&comp_id=[COMPETITION]
    public void callStandings() {
        logger.info( "Running against standingsURL" );

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity;
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> mappedResponse = new HashMap<>();
        List<Map<String, Object>> StandingList;

        responseEntity = restTemplate.getForEntity(STANDINGS_URL, String.class);

        //Retrieving jsonized standings from response
        String jsonResponse = (String) responseEntity.getBody();
        try {
            mappedResponse = mapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info( mappedResponse.toString() );
        StandingList = (List<Map<String, Object>>) mappedResponse.get("teams");

        //Comparing received data do db data (standings and teams)
        for(Map<String, Object> mappedStanding : StandingList){
            Standing standing = new Standing();
            standing.setId( Long.valueOf(mappedStanding.get("stand_id").toString()) );
            standing.setStandTeamId( Long.valueOf(mappedStanding.get("stand_team_id").toString()) );
            standing.setStandTeamName((String) mappedStanding.get("stand_team_name"));
            standing.setStandPosition((String) mappedStanding.get("stand_position"));
            standing.setStandOverallGp((String) mappedStanding.get("stand_overall_gp"));
            standing.setStandOverallW((String) mappedStanding.get("stand_overall_w"));
            standing.setStandOverallD((String) mappedStanding.get("stand_overall_d"));
            standing.setStandOverallL((String) mappedStanding.get("stand_overall_l"));
            standing.setStandOverallGs((String) mappedStanding.get("stand_overall_gs"));
            standing.setStandOverallGa((String) mappedStanding.get("stand_overall_ga"));
            standing.setStandPoints((String) mappedStanding.get("stand_points"));
            standing.setStandDesc((String) mappedStanding.get("stand_desc"));

            Standing existingStanding = standingRepository.getById(standing.getId());

            if( existingStanding != null ){
                existingStanding.setId( standing.getId() );
                existingStanding.setStandTeamId( standing.getStandTeamId() );
                existingStanding.setStandTeamName(standing.getStandTeamName());
                existingStanding.setStandPosition(standing.getStandPosition());
                existingStanding.setStandOverallGp(standing.getStandOverallGp());
                existingStanding.setStandOverallW(standing.getStandOverallW());
                existingStanding.setStandOverallD(standing.getStandOverallD());
                existingStanding.setStandOverallL(standing.getStandOverallL());
                existingStanding.setStandOverallGs(standing.getStandOverallGs());
                existingStanding.setStandOverallGa(standing.getStandOverallGa());
                existingStanding.setStandPoints(standing.getStandPoints());
                existingStanding.setStandDesc(standing.getStandDesc());
                standingRepository.save(existingStanding);

                logger.info("Standing updated");
            }
            else{
                standingRepository.save(standing);
                logger.info("Saved as new standing");
            }

            Team existingTeam = teamRepository.getById(standing.getStandTeamId());
            if( existingTeam != null ){
                existingTeam.setName(standing.getStandTeamName());

                logger.info("Team updated");
            }
            else{
                existingTeam = new Team();
                existingTeam.setId( standing.getStandTeamId() );
                existingTeam.setName( standing.getStandTeamName() );
                teamRepository.save(existingTeam);

                logger.info("Saved as new team");
            }
        }

        logger.info("Running against standingURL finished.");
    }


    //http://football-api.com/api/?Action=today&APIKey=[YOUR_API_KEY]&comp_id=[COMPETITION]
    public void callTodayMatches(){

    }

    //http://football-api.com/api/?Action=fixtures&APIKey=[YOUR_API_KEY]&comp_id=[COMPETITION]&&match_date=[DATE_IN_d.m.Y_FORMAT]
    public void callFixtures(DateTime fromDate, DateTime toDate){
        logger.info( "Running against fixturesURL" );

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity;
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> mappedResponse = new HashMap<>();
        List<Map<String, Object>> matchList;

        StringBuilder fixturesUrlBuilder = new StringBuilder();
        fixturesUrlBuilder.append(FIXTURES_URL).
                append("&from_date=").append(fromDate.getDayOfMonth()).append(".").append(fromDate.getMonthOfYear()).append(".").append(fromDate.getYear()).
                append("&to_date=").append(toDate.getDayOfMonth()).append(".").append(toDate.getMonthOfYear()).append(".").append(toDate.getYear());

        String fixturesUrl = fixturesUrlBuilder.toString();

        responseEntity = restTemplate.getForEntity(fixturesUrl, String.class);

        //Retrieving jsonized matches from response
        String jsonResponse = (String) responseEntity.getBody();
        try {
            mappedResponse = mapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        matchList = (List<Map<String, Object>>) mappedResponse.get("matches");

        //Comparing received data do db data (matches and matchEvents)
        for(Map<String, Object> mappedMatch : matchList){

            Match existingMatch = matchRepository.getById( Long.valueOf( mappedMatch.get("match_id").toString()) );
            Team localTeam = teamRepository.getById( Long.parseLong( mappedMatch.get("match_localteam_id").toString()) );
            Team visitorTeam = teamRepository.getById( Long.parseLong( mappedMatch.get("match_visitorteam_id").toString()) );

            if( existingMatch != null ){
                logger.info("Match updated");
            }
            else{
                existingMatch = new Match();
                existingMatch.setMatchEventList( new LinkedList<>() );
                existingMatch.setId( Long.valueOf( mappedMatch.get("match_id").toString()) );
                logger.info("Saving as new match");
            }

            existingMatch.setMatchDate( mappedMatch.get("match_formatted_date").toString() );
            existingMatch.setStatus( mappedMatch.get("match_status").toString() );
            existingMatch.setTime( mappedMatch.get("match_time").toString() );
            existingMatch.setLocalTeam( localTeam );
            existingMatch.setVisitorTeam( visitorTeam );
            existingMatch.setMatchHtScore( mappedMatch.get("match_ht_score").toString() );
            existingMatch.setMatchFtScore( mappedMatch.get("match_ft_score").toString() );

            existingMatch = matchRepository.save(existingMatch);

            List<Map<String, Object>> matchEventList = (List<Map<String, Object>>) mappedMatch.get( "match_events" );
            for(Map<String, Object> mappedEvent : matchEventList){
                MatchEvent existingEvent = matchEventRepository.getById( Long.valueOf( mappedEvent.get("event_id").toString()) );
                boolean isNew=true;

                if( existingEvent != null ){
                    isNew=false;
                    logger.info("Event updated");
                }
                else{
                    existingEvent = new MatchEvent();
                    existingEvent.setId( Long.valueOf( mappedEvent.get("event_id").toString()) );

                    logger.info("Saving as new event");
                }

                existingEvent.setMatch( existingMatch );
                existingEvent.setType( mappedEvent.get("event_type").toString() );
                existingEvent.setMinute( mappedEvent.get("event_minute").toString() );
                existingEvent.setWhichTeam( mappedEvent.get("event_team").toString() );
                existingEvent.setPlayerName( mappedEvent.get("event_player").toString() );
                existingEvent.setResult( mappedEvent.get("event_result").toString() );

                existingEvent = matchEventRepository.save( existingEvent );
                existingMatch.getMatchEventList().add( existingEvent );
                existingMatch = matchRepository.save( existingMatch );

                if(isNew)
                    notificationService.prepareNotification(existingEvent);
            }
        }

        logger.info("Running against fixturesURL finished.");
    }



}

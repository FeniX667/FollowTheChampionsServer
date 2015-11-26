package followTheChampions.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import followTheChampions.controllers.MobileApiController;
import followTheChampions.dao.*;
import followTheChampions.models.Competition;
import followTheChampions.models.Standing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FirstApiCaller {

    public final static String API_KEY="64bc654e-5926-9cfe-384b14a72f1a";
    public final static String COMP_ID="1204";

    public final static String COMPETITION_URL = "http://football-api.com/api/?Action=competitions&APIKey=" +API_KEY;
    public final static String STANDINGS_URL = "http://football-api.com/api/?Action=standings&APIKey=" +API_KEY+ "&comp_id=" +COMP_ID;
    public final static String TODAY_MATCHES_URL = "http://football-api.com/api/?Action=today&APIKey=" +API_KEY+ "&comp_id=" +COMP_ID;;
    public final static String FIXTURES_URL = "http://football-api.com/api/?Action=fixtures&APIKey=" +API_KEY+ "&comp_id=" +COMP_ID;; //&from_date=d.m.y&to_date=d.m.y

    private final static Logger logger = LoggerFactory
            .getLogger(MobileApiController.class);

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

        //Retrieving jsonized competition from response
        String jsonResponse = (String) responseEntity.getBody();
        try {
            mappedResponse = mapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info( mappedResponse.toString() );
        StandingList = (List<Map<String, Object>>) mappedResponse.get("teams");

        //Comparing received data do db data
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

                logger.info("Existing competition updated");
            }
            else{
                standingRepository.save(standing);
                logger.info("Saved as new standing");
            }
        }

        logger.info("Running against standingURL finished.");
    }


    //http://football-api.com/api/?Action=today&APIKey=[YOUR_API_KEY]&comp_id=[COMPETITION]
    public void getTodayMatches(){
    }

    //http://football-api.com/api/?Action=fixtures&APIKey=[YOUR_API_KEY]&comp_id=[COMPETITION]&&match_date=[DATE_IN_d.m.Y_FORMAT]
    public void getFictures(){

    }



}

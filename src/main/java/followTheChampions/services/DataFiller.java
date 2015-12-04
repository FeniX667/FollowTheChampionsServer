package followTheChampions.services;

import followTheChampions.dao.*;
import followTheChampions.models.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;

@Service
@Transactional
public class DataFiller {
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
            .getLogger(DataFiller.class);

    public void initiateData(){

        logger.info("Rozpoczynam zasilanie bazy danych");

        LinkedList<Competition> competitions = new LinkedList<>();
        LinkedList<FavouritedMatch> favouritedMatches = new LinkedList<>();
        LinkedList<FavouritedTeam> favouritedTeams = new LinkedList<>();
        LinkedList<Match> matches = new LinkedList<>();
        LinkedList<MatchEvent> matchEvents = new LinkedList<>();
        LinkedList<RegisteredDevice> registeredDevices = new LinkedList<>();
        LinkedList<Standing> standings = new LinkedList<>();
        LinkedList<Team> teams = new LinkedList<>();

        //////////////////////////////////////////////////////////////////////////

        registeredDevices.add(0,new RegisteredDevice());
        registeredDevices.get(0).setRegistrationDate(DateTime.now().toDate());
        registeredDevices.get(0).setIsActive(Boolean.TRUE);
        registeredDevices.get(0).setDeviceToken("demoToken");

        registeredDevices.add(1,new RegisteredDevice());
        registeredDevices.get(1).setRegistrationDate(DateTime.now().toDate());
        registeredDevices.get(1).setIsActive(Boolean.TRUE);
        registeredDevices.get(1).setDeviceToken("demoToken1");

        for(RegisteredDevice registeredDevice : registeredDevices){
            registeredDeviceRepository.save(registeredDevice);
        }

        registeredDevices.forEach(registeredDeviceRepository::save);

        ////////////////////////////////////////////////////////////////

        competitions.add(0,new Competition());
        competitions.get(0).setId(1024L);
        competitions.get(0).setName("England");
        competitions.get(0).setRegion("Premier League");

        competitions.forEach(competitionRepository::save);

        /////////////////////////////////////////////////////////////////

        teams.add(0,new Team());
        teams.get(0).setId(9240L);
        teams.get(0).setName("Leicester");

        teams.add(1,new Team());
        teams.get(1).setId(9260L);
        teams.get(1).setName("Manchester United");

        teams.add(2,new Team());
        teams.get(2).setId(9259L);
        teams.get(2).setName("Manchester City");

        teams.forEach(teamRepository::save);
        //////////////////////////////////////////////////////////////////

        matches.add(0,new Match());
        matches.get(0).setId(1L);
        matches.get(0).setMatchDate(DateTime.now().toString());
        matches.get(0).setStatus("END");
        matches.get(0).setTime("90:00");
        matches.get(0).setLocalTeam( teamRepository.getById(9240L) );
        matches.get(0).setVisitorTeam( teamRepository.getById(9260L) );
        matches.get(0).setMatchHtScore("[2:2]");
        matches.get(0).setMatchFtScore("[3:2]");

        matches.add(1,new Match());
        matches.get(1).setId(2L);
        matches.get(1).setMatchDate(DateTime.now().toString());
        matches.get(1).setStatus("END");
        matches.get(1).setTime("90:00");
        matches.get(1).setLocalTeam( teamRepository.getById(9260L) );
        matches.get(1).setVisitorTeam( teamRepository.getById(9259L) );
        matches.get(1).setMatchHtScore("0:0");
        matches.get(1).setMatchFtScore("0:0");

        matches.add(2,new Match());
        matches.get(2).setId(3L);
        matches.get(2).setMatchDate(DateTime.now().toString());
        matches.get(2).setStatus("END");
        matches.get(2).setTime("90:00");
        matches.get(2).setLocalTeam( teamRepository.getById(9259L) );
        matches.get(2).setVisitorTeam( teamRepository.getById(9240L) );
        matches.get(2).setMatchHtScore("1:2");
        matches.get(2).setMatchFtScore("2:2");

        matches.forEach(matchRepository::save);

        //////////////////////////////////////////////////////////////////////////

        favouritedTeams.add(0,new FavouritedTeam());
        favouritedTeams.get(0).setRegisteredDevice( registeredDeviceRepository.getById(1L) );
        favouritedTeams.get(0).setTeam( teamRepository.getById(1L));

        favouritedTeams.add(1,new FavouritedTeam());
        favouritedTeams.get(1).setRegisteredDevice( registeredDeviceRepository.getById(1L) );
        favouritedTeams.get(1).setTeam( teamRepository.getById(2L));

        favouritedTeams.add(2,new FavouritedTeam());
        favouritedTeams.get(2).setRegisteredDevice( registeredDeviceRepository.getById(1L) );
        favouritedTeams.get(2).setTeam( teamRepository.getById(3L));

        favouritedTeams.add(3,new FavouritedTeam());
        favouritedTeams.get(3).setRegisteredDevice( registeredDeviceRepository.getById(2L) );
        favouritedTeams.get(3).setTeam( teamRepository.getById(3L));

        favouritedTeams.forEach(favouritedTeamRepository::save);

        /////////////////////////////////////////////////////////////////////////

        favouritedMatches.add(0,new FavouritedMatch());
        favouritedMatches.get(0).setRegisteredDevice( registeredDeviceRepository.getById(1L) );
        favouritedMatches.get(0).setMatch( matchRepository.getById(1L) );

        favouritedMatches.add(1,new FavouritedMatch());
        favouritedMatches.get(1).setRegisteredDevice( registeredDeviceRepository.getById(1L) );
        favouritedMatches.get(1).setMatch( matchRepository.getById(1L) );

        favouritedMatches.forEach(favouritedMatchRepository::save);


        logger.info("Baza danych zainicjowana");
    }
}

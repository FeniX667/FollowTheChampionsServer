package followTheChampions.dao;

import followTheChampions.models.FavouritedMatch;
import followTheChampions.models.Match;
import followTheChampions.models.RegisteredDevice;
import followTheChampions.models.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MatchRepository extends CrudRepository<Match, Long> {

    Match getById(Long id);

    @Query("SELECT m " +
            "FROM Match m " +
            "WHERE " +
            "m.localTeam = (:team) OR " +
            "m.visitorTeam = (:team)")
    List<Match> getByTeamId(@Param("team")Team team);

    @Query("SELECT m " +
            "FROM Match m " +
            "WHERE " +
            "m.localTeam IN( SELECT team FROM FavouritedTeam WHERE registeredDevice = (:device) ) OR " +
            "m.visitorTeam IN( SELECT team FROM FavouritedTeam WHERE registeredDevice = (:device) ) OR " +
            "m IN( SELECT match FROM FavouritedMatch WHERE registeredDevice = (:device) )" )
    List<Match> getAllFavouritedMatches(@Param("device")RegisteredDevice device);
}

package followTheChampions.dao;

import followTheChampions.models.FavouritedMatch;
import followTheChampions.models.Match;
import followTheChampions.models.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MatchRepository extends CrudRepository<Match, Long> {

    Match getById(Long id);

    @Query("SELECT m "
            + "FROM Match m "
            + "WHERE "
            + "m.localTeam = (:team) OR "
            + "m.visitorTeam = (:team)")
    List<Match> getByTeamId(@Param("team")Team team);
        }

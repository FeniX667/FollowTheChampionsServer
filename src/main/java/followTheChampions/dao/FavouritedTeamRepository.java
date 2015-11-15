package followTheChampions.dao;

import followTheChampions.models.Competition;
import followTheChampions.models.FavouritedTeam;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Malar on 2015-11-15.
 */
@Transactional
public interface FavouritedTeamRepository extends BasicJpaRepository<FavouritedTeam, Long> {

    Competition getById(Long id);
}

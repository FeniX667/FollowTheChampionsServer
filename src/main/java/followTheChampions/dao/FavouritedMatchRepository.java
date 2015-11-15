package followTheChampions.dao;

import followTheChampions.models.Competition;
import followTheChampions.models.FavouritedMatch;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Malar on 2015-11-15.
 */
@Transactional
public interface FavouritedMatchRepository extends BasicJpaRepository<FavouritedMatch, Long> {

    Competition getById(Long id);
}

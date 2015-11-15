package followTheChampions.dao;

import followTheChampions.models.Competition;
import followTheChampions.models.Match;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Malar on 2015-11-15.
 */
@Transactional
public interface MatchRepository extends BasicJpaRepository<Match, Long> {

    Competition getById(Long id);
}

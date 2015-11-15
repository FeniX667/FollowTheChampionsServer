package followTheChampions.dao;

import followTheChampions.models.Competition;
import followTheChampions.models.MatchEvent;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Malar on 2015-11-15.
 */
@Transactional
public interface MatchEventRepository extends BasicJpaRepository<MatchEvent, Long> {

    Competition getById(Long id);
}

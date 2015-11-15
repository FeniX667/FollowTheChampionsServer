package followTheChampions.dao;

import followTheChampions.models.Competition;
import followTheChampions.models.Team;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Malar on 2015-11-15.
 */
@Transactional
public interface TeamRepository extends BasicJpaRepository<Team, Long> {

    Competition getById(Long id);
}

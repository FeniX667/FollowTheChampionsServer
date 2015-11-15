package followTheChampions.dao;

import followTheChampions.models.Competition;
import followTheChampions.models.Standing;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Malar on 2015-11-15.
 */
@Transactional
public interface StandingRepository extends BasicJpaRepository<Standing, Long> {

    Competition getById(Long id);
}

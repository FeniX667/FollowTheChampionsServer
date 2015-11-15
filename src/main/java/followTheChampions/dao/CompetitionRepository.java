package followTheChampions.dao;

import followTheChampions.models.Competition;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Malar on 2015-11-15.
 */
@Transactional
public interface CompetitionRepository extends BasicJpaRepository<Competition, Long> {

    Competition getById(Long id);
}

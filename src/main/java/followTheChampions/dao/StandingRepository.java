package followTheChampions.dao;

import followTheChampions.models.Standing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface StandingRepository extends CrudRepository<Standing, Long> {

    Standing getById(Long id);
}

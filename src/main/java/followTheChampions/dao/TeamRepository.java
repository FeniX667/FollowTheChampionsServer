package followTheChampions.dao;

import followTheChampions.models.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TeamRepository extends CrudRepository<Team, Long> {

    Team getById(Long id);
}

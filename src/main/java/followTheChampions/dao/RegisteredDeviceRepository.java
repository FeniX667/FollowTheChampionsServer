package followTheChampions.dao;

import followTheChampions.models.Match;
import followTheChampions.models.RegisteredDevice;
import followTheChampions.models.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;

@Transactional
public interface RegisteredDeviceRepository extends CrudRepository<RegisteredDevice, Long> {

    RegisteredDevice getById(Long id);

    RegisteredDevice getByDeviceToken(String Token);

    @Query("SELECT DISTINCT rd " +
            "FROM FavouritedTeam ft " +
            "JOIN ft.registeredDevice rd " +
            "WHERE " +
            "ft.team = (:localTeam) OR " +
            "ft.team = (:visitorTeam) " +
            "" )
    LinkedList<RegisteredDevice> getInterestedDevicesByTeams(@Param("localTeam")Team localTeam, @Param("visitorTeam")Team visitorTeam);

    @Query("SELECT DISTINCT rd " +
            "FROM FavouritedMatch fm " +
            "JOIN fm.registeredDevice rd " +
            "WHERE " +
            "fm.match = (:match)" +
            "" )
    LinkedList<RegisteredDevice> getInterestedDevicesByMatch(@Param("match")Match match);
}

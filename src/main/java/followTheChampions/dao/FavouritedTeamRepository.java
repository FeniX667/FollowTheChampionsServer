package followTheChampions.dao;

import followTheChampions.models.FavouritedTeam;
import followTheChampions.models.RegisteredDevice;
import followTheChampions.models.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface FavouritedTeamRepository extends CrudRepository<FavouritedTeam, Long> {

    FavouritedTeam getById(Long id);

    List<FavouritedTeam> getByRegisteredDevice(RegisteredDevice registeredDevice);

    @Query("SELECT ft "
            + "FROM FavouritedTeam ft "
            + "WHERE "
            + "ft.registeredDevice = (:device) AND "
            + "ft.team = (:team)")
    List<FavouritedTeam> getByDeviceTokenTeamId(@Param("device")RegisteredDevice device, @Param("team")Team team);
}

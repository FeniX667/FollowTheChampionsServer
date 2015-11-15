package followTheChampions.dao;

import followTheChampions.models.RegisteredDevice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RegisteredDeviceRepository extends CrudRepository<RegisteredDevice, Long> {

    RegisteredDevice getById(Long id);

    RegisteredDevice getByDeviceToken(String Token);
}

package upp.la.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import upp.la.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
    Role findByName(String name);
}

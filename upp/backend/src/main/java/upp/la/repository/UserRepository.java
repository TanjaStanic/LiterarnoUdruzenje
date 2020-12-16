package upp.la.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import upp.la.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query("select case when count(u)> 0 then true else false end from User u where u.userName like userName")
	boolean existsByUserName(@Param("userName") String userName);
}

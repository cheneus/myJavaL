package chen;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepo extends CrudRepository<Customer, Long> {

  List<Customer> findByLastName(String lastName);
}
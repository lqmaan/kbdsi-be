package icstar.kbdsi.apps.repository;

import icstar.kbdsi.apps.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}

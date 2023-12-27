package leesangho.adtechstudy.mvc.infra.repository;

import leesangho.adtechstudy.mvc.infra.entity.BoardItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardItemJpaRepository extends JpaRepository<BoardItemEntity, String> {

}

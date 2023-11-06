package leesangho.adtechstudy.mvcapplication.infra.repository;

import leesangho.adtechstudy.mvcapplication.infra.entity.BoardItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardItemJpaRepository extends JpaRepository<BoardItemEntity, Long> {
}

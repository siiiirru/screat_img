package sy.screat_img.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sy.screat_img.entity.Crpimgs;

import java.util.List;

public interface CrpimgsRepository extends JpaRepository<Crpimgs, Long> {

    List<Crpimgs> findByUserId(Long userid);

}


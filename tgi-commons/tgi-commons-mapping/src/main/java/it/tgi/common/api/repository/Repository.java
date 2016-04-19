package it.tgi.common.api.repository;

import it.tgi.common.api.model.BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface Repository<T extends BaseEntity<Long>> extends GenericRepository<T, Long> {
}

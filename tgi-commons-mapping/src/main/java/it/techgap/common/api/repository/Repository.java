package it.techgap.common.api.repository;

import org.springframework.data.repository.NoRepositoryBean;

import it.techgap.common.api.model.BaseEntity;

@NoRepositoryBean
public interface Repository<T extends BaseEntity<Long>> extends GenericRepository<T, Long> {
}

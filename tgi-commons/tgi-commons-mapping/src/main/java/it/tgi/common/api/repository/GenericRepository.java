package it.tgi.common.api.repository;

import it.tgi.common.api.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface GenericRepository<T extends BaseEntity, PK extends Serializable> extends JpaRepository<T, PK> {

	@Transactional(readOnly = true)
	@Query("FROM #{#entityName} t ")
	public List<T> findAll();
	
	@Transactional(readOnly = true)
	@Query("FROM #{#entityName} t WHERE t.enabled = true")
	public List<T> findAllEnabled();

	@Transactional
	@Modifying
	@Query("UPDATE #{#entityName} t SET t.enabled = true WHERE t.id = :id")
	public void enable(@Param("id") PK id);

	@Transactional
	@Modifying
	@Query("UPDATE #{#entityName} t SET t.enabled = false WHERE t.id = :id")
	public void disable(@Param("id") PK id);

	@Transactional
	@Modifying
	@Query("UPDATE #{#entityName} t SET t.enabled = false")
	public void disableAll();

	@Transactional
	@Modifying
	@Query("UPDATE #{#entityName} t SET t.enabled = true")
	public void enableAll();

}
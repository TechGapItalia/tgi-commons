package it.tgi.common.api.service;

import it.tgi.common.api.model.AbstractEntityInterface;
import it.tgi.common.api.model.dto.AbstractDTO;
import it.tgi.common.api.model.dto.mapper.AbstractMapper;
import it.tgi.common.api.repository.AbstractRepository;

import java.util.List;

public abstract class AbstractCrudService<Entity extends AbstractEntityInterface<Long>, Dto extends AbstractDTO> {

	protected abstract AbstractRepository<Entity, Long> getEntityRepository();

	protected abstract AbstractMapper<Entity, Dto> getEntityMapper();

	public List<Dto> list() {
		return getEntityMapper().toDtos(getEntityRepository().findAllEnabled());
	}

	public List<Dto> listLight() {
		return getEntityMapper().toDtos(getEntityRepository().findAllEnabled(), false);
	}

	public Dto get(Long id) {
		return getEntityMapper().toDto(getEntityRepository().findOne(id));
	}

	public Dto save(Dto dto) {
		return saveOrUpdate(dto);
	}

	public Dto modify(Dto dto) {
		return saveOrUpdate(dto);
	}

	private Dto saveOrUpdate(Dto dto) {
		Entity e = getEntityMapper().toEntity(dto);
		e = getEntityRepository().save(e);
		return getEntityMapper().toDto(e);
	}

	public void delete(Long id) {
		getEntityRepository().disable(id);
	}

}
package it.tgi.common.api.service;

import it.tgi.common.api.dto.GenericDto;
import it.tgi.common.api.mapper.GenericMapper;
import it.tgi.common.api.model.BaseEntity;
import it.tgi.common.api.repository.GenericRepository;

import java.io.Serializable;

public abstract class GenericReadOnlyService<Entity extends BaseEntity<PK>, DTO extends GenericDto<PK>, PK extends Serializable> {
    protected abstract GenericRepository<Entity, PK> getEntityRepository();

    protected abstract GenericMapper<Entity, DTO, PK> getEntityMapper();

    public Iterable<DTO> list() {
        return getEntityMapper().convertAll(getEntityRepository().findAllEnabled());
    }
    
    public Iterable<DTO> listAll() {
        return getEntityMapper().convertAll(getEntityRepository().findAll());
    }


    public DTO get(PK id) {
        return getEntityMapper().convert(getEntityRepository().findOne(id));
    }
}

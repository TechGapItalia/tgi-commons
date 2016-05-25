package it.techgap.common.api.service;

import java.io.Serializable;

import it.techgap.common.api.dto.GenericDto;
import it.techgap.common.api.mapper.GenericMapper;
import it.techgap.common.api.model.BaseEntity;
import it.techgap.common.api.repository.GenericRepository;

public abstract class GenericReadOnlyService<Entity extends BaseEntity<PK>, DTO extends GenericDto<PK>, PK extends Serializable> {
    protected abstract GenericRepository<Entity, PK> getEntityRepository();

    protected abstract GenericMapper<Entity, DTO, PK> getEntityMapper();

    public Iterable<DTO> list() {
        return getEntityMapper().convertAll(getEntityRepository().findAllEnabled());
    }
    
    public DTO get(PK id) {
        return getEntityMapper().convert(getEntityRepository().findOne(id));
    }
}

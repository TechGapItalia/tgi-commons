package it.tgi.common.api.service;

import it.tgi.common.api.dto.GenericDto;
import it.tgi.common.api.mapper.MapperWithList;
import it.tgi.common.api.model.BaseEntity;
import it.tgi.common.api.repository.GenericRepository;

public abstract class GenericReadOnlyServiceWithList<Entity extends BaseEntity<Long>, DTO extends GenericDto<Long>> {
    protected abstract GenericRepository<Entity, Long> getEntityRepository();

    
    protected abstract MapperWithList<Entity, DTO> getEntityMapper();

    public Iterable<DTO> list(boolean withDeleted) {
    	return withDeleted?
    			getEntityMapper().convertAll(getEntityRepository().findAll())
    		:	getEntityMapper().convertAll(getEntityRepository().findAllEnabled());
    }
    

    public DTO get(Long id) {
        return getEntityMapper().convert(getEntityRepository().findOne(id));
    }
}

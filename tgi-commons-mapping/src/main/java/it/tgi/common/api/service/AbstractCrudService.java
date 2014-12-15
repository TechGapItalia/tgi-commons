package it.tgi.common.api.service;

import it.tgi.common.api.dto.Dto;
import it.tgi.common.api.mapper.Mapper;
import it.tgi.common.api.model.BaseEntity;
import it.tgi.common.api.repository.Repository;

public abstract class AbstractCrudService<Entity extends BaseEntity, DTO extends Dto> {

    protected abstract Repository<Entity> getEntityRepository();

    protected abstract Mapper<Entity, DTO> getEntityMapper();

    public Iterable<DTO> list() {
        return getEntityMapper().convertAll(getEntityRepository().findAllEnabled());
    }

    public DTO get(Long id) {
        return getEntityMapper().convert(getEntityRepository().findOne(id));
    }

    public DTO save(DTO DTO) {
        return saveOrUpdate(DTO);
    }

    public DTO modify(DTO DTO) {
        return saveOrUpdate(DTO);
    }

    private DTO saveOrUpdate(DTO DTO) {
        Entity e = getEntityMapper().reverse().convert(DTO);
        e = getEntityRepository().save(e);
        return getEntityMapper().convert(e);
    }

    public void delete(Long id, boolean logical) {
        if (logical) {
            getEntityRepository().disable(id);
        } else {
            getEntityRepository().delete(id);
        }
    }

    public void delete(Long id) {
        delete(id, true);
    }

    public void enable(Long id) {
        getEntityRepository().enable(id);
    }

}
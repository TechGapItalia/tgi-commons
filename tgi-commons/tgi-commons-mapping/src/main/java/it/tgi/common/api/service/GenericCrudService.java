package it.tgi.common.api.service;

import it.tgi.common.api.dto.GenericDto;
import it.tgi.common.api.mapper.GenericMapper;
import it.tgi.common.api.model.BaseEntity;
import it.tgi.common.api.repository.GenericRepository;

import java.io.Serializable;

public abstract class GenericCrudService<Entity extends BaseEntity<PK>, DTO extends GenericDto<PK>, PK extends Serializable> {

    protected abstract GenericRepository<Entity, PK> getEntityRepository();

    protected abstract GenericMapper<Entity, DTO, PK> getEntityMapper();

    public Iterable<DTO> list() {
        return getEntityMapper().convertAll(getEntityRepository().findAllEnabled());
    }

    public DTO get(PK id) {
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

    public void delete(PK id, boolean logical) {
        if (logical) {
            getEntityRepository().disable(id);
        } else {
            getEntityRepository().delete(id);
        }
    }

    public void delete(PK id) {
        delete(id, true);
    }

    public void enable(PK id) {
        getEntityRepository().enable(id);
    }

}
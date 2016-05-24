package it.tgi.common.api.service;

import it.tgi.common.api.dto.GenericDto;
import it.tgi.common.api.exception.DuplicateEntityException;
import it.tgi.common.api.exception.IdMismatchException;
import it.tgi.common.api.exception.MalformedEntityException;
import it.tgi.common.api.model.BaseEntity;

import java.io.Serializable;

public abstract class GenericCrudService<Entity extends BaseEntity<PK>, DTO extends GenericDto<PK>, PK extends Serializable> extends GenericReadOnlyService<Entity,DTO,PK> {

    protected abstract void assertDuplicate(Entity e, boolean isNew) throws DuplicateEntityException;

    public DTO save(DTO DTO) throws DuplicateEntityException {
        Entity e = getEntityMapper().reverse().convert(DTO);
        _assertDuplicate(e, true);
        return saveOrUpdate(e);
    }

    public DTO modify(PK id, DTO dto) throws MalformedEntityException, IdMismatchException, DuplicateEntityException {
        if (id == null) {
            throw new MalformedEntityException(dto.getClass(), "id");
        }
        if (!id.equals(dto.getId())) {
            throw new IdMismatchException(dto.getClass());
        }
        Entity e = getEntityMapper().reverse().convert(dto);
        _assertDuplicate(e, false);
        return saveOrUpdate(e);
    }

    private DTO saveOrUpdate(Entity e) {
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

    private void _assertDuplicate(Entity e, boolean isNew) throws DuplicateEntityException {
        PK id = e.getId();
        if (id != null && isNew) {
            if (getEntityRepository().exists(id)) {
                throw new DuplicateEntityException(e.getClass(), e.getId());
            }
        }
        try {
            assertDuplicate(e, isNew);
        } catch (DuplicateEntityException duplicateEntityException) {
            duplicateEntityException.printStackTrace();
        }
    }

}
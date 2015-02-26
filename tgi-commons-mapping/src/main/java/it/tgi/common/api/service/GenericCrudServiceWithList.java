package it.tgi.common.api.service;

import it.tgi.common.api.dto.GenericDto;
import it.tgi.common.api.exception.DuplicateEntityException;
import it.tgi.common.api.exception.IdMismatchException;
import it.tgi.common.api.exception.MalformedEntityException;
import it.tgi.common.api.model.BaseEntity;

import java.io.Serializable;

public abstract class GenericCrudServiceWithList<Entity extends BaseEntity<Long>, DTO extends GenericDto<Long>> extends GenericReadOnlyServiceWithList<Entity,DTO> {

    protected abstract void assertDuplicate(Entity e, boolean isNew) throws DuplicateEntityException;
    

    
    /**
     * Call back when flag enabled changes.
     * Useful to add a behaviour like disable all children too..
     * 
     * @param newEntity the updated not saved  {@link Entity}
     * @param previousEntity   the not updated  {@link Entity} 
     * */
    protected abstract void onEnabledChange(Entity newEntity, Entity previousEntity);

    public DTO save(DTO DTO) throws DuplicateEntityException {
        Entity e = getEntityMapper().reverse().convert(DTO);
        _assertDuplicate(e, true);
        return saveOrUpdate(e);
    }

    public DTO modify(Long id, DTO dto) throws MalformedEntityException, IdMismatchException, DuplicateEntityException {
        if (id == null) {
            throw new MalformedEntityException(dto.getClass(), "id");
        }
        if (!id.equals(dto.getId())) {
            throw new IdMismatchException(dto.getClass());
        }
        Entity e = getEntityMapper().reverse().convert(dto);
        _assertDuplicate(e, false);
        
        checkDisabledChange(e);
        
        return saveOrUpdate(e);
    }

    private void checkDisabledChange(Entity newEntity) {
    	
		Entity previousEntity = getEntityRepository().findOne(newEntity.getId());
		if(newEntity.isEnabled() != previousEntity.isEnabled()){
			onEnabledChange(newEntity,previousEntity);
		}
	}



	private DTO saveOrUpdate(Entity e) {
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

    private void _assertDuplicate(Entity e, boolean isNew) throws DuplicateEntityException {
        Long id = e.getId();
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
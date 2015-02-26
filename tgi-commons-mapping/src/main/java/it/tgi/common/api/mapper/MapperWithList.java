package it.tgi.common.api.mapper;

import it.tgi.common.api.dto.GenericDto;
import it.tgi.common.api.model.BaseEntity;

/**
 * A bi-direction {@link Entity} to {@link DTO} converter, implemented using Guava's {@link com.google.common.base.Converter}
 *
 * An {@link Entity} having Collections of other {@link Entity}s must extends this class.
 * 
 *
 * @param <Entity> Entity class
 * @param <DTO>    DTO class
 * @param <PK>     Identifier class
 */
public abstract class MapperWithList<Entity extends BaseEntity<Long>, DTO extends GenericDto<Long>> extends Mapper<Entity, DTO>{

   
    protected MapperWithList(Class<Entity> entityClazz, Class<DTO> dtoClazz) {
		super(entityClazz, dtoClazz);
	
	}

    /**
     * @return true if you want to include disabled children
     * */
    protected abstract boolean addDisabledChildred();
    
    
	protected void fillDto(Entity e, DTO dto){
		this.fillDto(e,dto,addDisabledChildred());

    };
    
    /**
     * Converts a {@link DTO} to the corresponding {@link Entity}.
     * <p/>
     * The {@link Entity id is automatically set}
     *
     * @param dto source {@link DTO}
     * @param e   target {@link Entity}
     * @param withDisabledChildren if true requires to put disabled entities in lists
     */
    protected abstract void fillDto(Entity e, DTO dto,boolean withDisabledChildren);
    
   
    
}
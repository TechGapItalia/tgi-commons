package it.techgap.common.api.mapper;

import it.techgap.common.api.dto.GenericDto;
import it.techgap.common.api.model.BaseEntity;

/**
 * Specialization of {@link it.techgap.common.api.mapper.GenericMapper} for entities identified by {@link java.lang.Long}
 *
 * @param <Entity> Entity class
 * @param <DTO>    DTO class
 * @see it.techgap.common.api.mapper.GenericMapper
 */
public abstract class Mapper<Entity extends BaseEntity<Long>, DTO extends GenericDto<Long>> extends GenericMapper<Entity, DTO, Long> {

    protected Mapper(Class<Entity> entityClazz, Class<DTO> dtoClazz) {
        super(entityClazz, dtoClazz);
    }
}

package it.tgi.common.api.mapper;

import it.tgi.common.api.dto.GenericDto;
import it.tgi.common.api.model.BaseEntity;

/**
 * Specialization of {@link it.tgi.common.api.mapper.GenericMapper} for entities identified by {@link java.lang.Long}
 *
 * @param <Entity> Entity class
 * @param <DTO>    DTO class
 * @see it.tgi.common.api.mapper.GenericMapper
 */
public abstract class Mapper<Entity extends BaseEntity<Long>, DTO extends GenericDto<Long>> extends GenericMapper<Entity, DTO, Long> {

    protected Mapper(Class<Entity> entityClazz, Class<DTO> dtoClazz) {
        super(entityClazz, dtoClazz);
    }
}

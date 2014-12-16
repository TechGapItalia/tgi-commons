package it.tgi.common.api.mapper;

import com.google.common.base.Converter;
import com.google.common.base.Throwables;
import it.tgi.common.api.dto.GenericDto;
import it.tgi.common.api.model.BaseEntity;

import java.io.Serializable;

/**
 * A bi-direction {@link Entity} to {@link DTO} converter, implemented using Guava's {@link com.google.common.base.Converter}
 *
 * @param <Entity> Entity class
 * @param <DTO>    DTO class
 * @param <PK>     Identifier class
 */
public abstract class GenericMapper<Entity extends BaseEntity<PK>, DTO extends GenericDto<PK>, PK extends Serializable> extends Converter<Entity, DTO> {

    private Class<Entity> entityClazz;
    private Class<DTO> dtoClazz;

    /**
     * Retrieves an {@link Entity} given its primary key, usually using some kind of
     * {@link it.tgi.common.api.repository.Repository}
     *
     * @param id Primary key of the {@link Entity}
     * @return The requested entity, null if it does not exists
     */
    protected abstract Entity retrieveEntity(PK id);

    /**
     * Converts a {@link DTO} to the corresponding {@link Entity}.
     * <p/>
     * The {@link Entity id is automatically set}
     *
     * @param e   target {@link Entity}
     * @param dto source {@link DTO}
     * @return The converted {@link Entity}
     */
    protected abstract Entity fillEntity(Entity e, DTO dto);

    /**
     * Converts an {@link Entity} to the corresponding {@link DTO}.
     * <p/>
     * The {@link DTO id is automatically set}
     *
     * @param e   source {@link Entity}
     * @param dto target {@link DTO}
     * @return The converted {@link DTO}
     */
    protected abstract DTO fillDto(Entity e, DTO dto);

    protected GenericMapper(Class<Entity> entityClazz, Class<DTO> dtoClazz) {
        this.entityClazz = entityClazz;
        this.dtoClazz = dtoClazz;
    }

    @Override
    protected final DTO doForward(Entity e) {
        DTO buildDTO = buildDTO();
        buildDTO.setId(e.getId());
        return fillDto(e, buildDTO);
    }

    @Override
    protected final Entity doBackward(DTO input) {
        final PK id = input.getId();
        Entity e = null;

        if (id != null) {
            e = retrieveEntity(id);
        }

        if (e == null) {
            e = buildEntity();
        }

        return fillEntity(e, input);
    }


    private Entity buildEntity() {
        try {
            return entityClazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            Throwables.propagate(e);
        }
        return null;
    }

    private DTO buildDTO() {
        try {
            return dtoClazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            Throwables.propagate(e);
        }
        return null;
    }
}
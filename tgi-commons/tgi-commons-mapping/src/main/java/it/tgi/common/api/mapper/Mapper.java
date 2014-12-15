package it.tgi.common.api.mapper;

import com.google.common.base.Converter;
import it.tgi.common.api.dto.Dto;
import it.tgi.common.api.model.BaseEntity;

import java.lang.reflect.Field;

public abstract class Mapper<Entity extends BaseEntity, DTO extends Dto> extends Converter<Entity, DTO> {

    private Class<Entity> entityClazz;
    private Class<DTO> dtoClazz;

    protected abstract Entity retrieveEntity(Long id);

    protected Mapper(Class<Entity> entityClazz, Class<DTO> dtoClazz) {
        this.entityClazz = entityClazz;
        this.dtoClazz = dtoClazz;
    }

    protected Entity buildEntity() {
        try {
            return entityClazz.newInstance();
        } catch (Exception e) {
        }
        return null;
    }

    protected DTO buildDTO() {
        try {
            return dtoClazz.newInstance();
        } catch (Exception e) {
        }
        return null;
    }

    protected Entity fillEntity(Entity e, DTO DTO) {
        return e;
    }

    protected DTO fillDto(Entity e, DTO DTO) {
        return DTO;
    }

    @Override
    protected DTO doForward(Entity e) {
        if (e == null)
            return null;
        else {
            DTO buildDTO = buildDTO();
            copySameProperties(e, buildDTO);
            buildDTO.setId(e.getId());
            return fillDto(e, buildDTO);
        }
    }

    @Override
    protected Entity doBackward(DTO input) {
        Long id = input.getId();
        Entity e = null;
        if (id != null)
            e = retrieveEntity(id);

        if (e == null)
            e = buildEntity();

        copySameProperties(input, e);
        return fillEntity(e, input);
    }

    protected static void copySameProperties(Object from, Object to) {
        for (Field fromField : from.getClass().getDeclaredFields()) {
            Class<?> fromFieldClass = fromField.getType();
            try {
                Field toField = to.getClass().getDeclaredField(fromField.getName());
                if (fromFieldClass.equals(toField.getType())) {
                    toField.setAccessible(true);
                    fromField.setAccessible(true);
                    toField.set(to, fromField.get(from));
                }
            } catch (Exception e) {
            }
        }
    }
}
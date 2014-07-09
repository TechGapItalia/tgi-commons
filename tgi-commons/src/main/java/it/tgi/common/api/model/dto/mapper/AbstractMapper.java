package it.tgi.common.api.model.dto.mapper;

import it.tgi.common.api.model.AbstractEntityInterface;
import it.tgi.common.api.model.dto.AbstractDTO;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMapper<Entity extends AbstractEntityInterface<Long>, Dto extends AbstractDTO> {

	private Class<Entity> entityClazz;
	private Class<Dto> dtoClazz;

	protected abstract Entity retrieveEntity(Long id);

	protected AbstractMapper(Class<Entity> entityClazz, Class<Dto> dtoClazz) {
		this.entityClazz = entityClazz;
		this.dtoClazz = dtoClazz;
	}

	protected Entity buildEntity() {
		try {
			return entityClazz.newInstance();
		} catch (Exception e) {
		}
		return null;
	};

	protected Dto buildDto() {
		try {
			return dtoClazz.newInstance();
		} catch (Exception e) {
		}
		return null;
	};

	public Dto toDto(final Entity e) {
		return this.toDto(e, true);
	}

	public List<Dto> toDtos(List<Entity> inputs) {
		return this.toDtos(inputs, true);
	}

	/**
	 * @param fillAll
	 *            if true fills the undelying DTO entities too
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * */
	public Dto toDto(final Entity e, boolean fillAll) {
		if (e == null)
			return null;
		else {
			Dto buildDto = buildDto();
			copySameProperties(e, buildDto);
			buildDto.setId(e.getId());
			return fillAll ? fillDto(e, buildDto) : buildDto;
		}
	}

	public Entity toEntity(final Dto input) {
		Long id = input.getId();
		Entity e = null;
		if (id != null)
			e = retrieveEntity(id);

		if (e == null)
			e = buildEntity();

		copySameProperties(input, e);
		return fillEntity(e, input);
	}

	public List<Dto> toDtos(List<Entity> inputs, boolean fillAll) {
		List<Dto> results = new ArrayList<>();
		for (Entity input : inputs)
			results.add(toDto(input, fillAll));
		return results;
	}

	public List<Entity> toEntities(List<Dto> inputs) {
		List<Entity> results = new ArrayList<>();
		for (Dto input : inputs) {
			results.add(toEntity(input));
		}
		return results;
	}

	protected Entity fillEntity(Entity e, Dto dto) {
		return e;
	}

	protected Dto fillDto(Entity e, Dto dto) {
		return dto;
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
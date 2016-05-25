package it.techgap.common.api.service;

import it.techgap.common.api.dto.GenericDto;
import it.techgap.common.api.model.BaseEntity;

public abstract class CrudServiceWithList<E extends BaseEntity<Long>, D extends GenericDto<Long>> extends GenericCrudServiceWithList<E, D> {

}

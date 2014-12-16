package it.tgi.common.api.service;

import it.tgi.common.api.dto.GenericDto;
import it.tgi.common.api.model.BaseEntity;

public abstract class CrudService<E extends BaseEntity<Long>, D extends GenericDto<Long>> extends GenericCrudService<E, D, Long> {

}

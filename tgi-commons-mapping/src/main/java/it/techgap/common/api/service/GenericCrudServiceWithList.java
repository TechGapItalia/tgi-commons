package it.techgap.common.api.service;

/*-
 * #%L
 * tgi-commons-mapping
 * %%
 * Copyright (C) 2016 TechGap Italia
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */


import it.techgap.common.api.dto.GenericDto;
import it.techgap.common.api.exception.DuplicateEntityException;
import it.techgap.common.api.exception.IdMismatchException;
import it.techgap.common.api.exception.MalformedEntityException;
import it.techgap.common.api.model.BaseEntity;

public abstract class GenericCrudServiceWithList<Entity extends BaseEntity<Long>, DTO extends GenericDto<Long>> extends GenericReadOnlyServiceWithList<Entity, DTO> {

    protected abstract void assertDuplicate(Entity e, boolean isNew) throws DuplicateEntityException;


    /**
     * Call back when flag enabled changes.
     * Useful to add a behaviour like disable all children too..
     *
     * @param newEnabled     the updated not saved  {@link Entity}
     * @param previousEntity the not updated  {@link Entity}
     */
    protected abstract void onEnabledChange(boolean newEnabled, Entity previousEntity);

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

        _checkDisabledChange(dto);

        Entity e = getEntityMapper().reverse().convert(dto);
        _assertDuplicate(e, false);


        return saveOrUpdate(e);
    }

    private void _checkDisabledChange(DTO dto) {

        Entity e = getEntityRepository().findOne(dto.getId());
        if (dto.getEnabled() != e.isEnabled()) {
            onEnabledChange(dto.getEnabled(), e);
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
        assertDuplicate(e, isNew);
    }

}

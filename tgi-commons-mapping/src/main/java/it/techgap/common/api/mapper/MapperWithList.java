package it.techgap.common.api.mapper;

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
import it.techgap.common.api.model.BaseEntity;

/**
 * A bi-direction {@link Entity} to {@link DTO} converter, implemented using Guava's {@link com.google.common.base.Converter}
 *
 * An {@link Entity} having Collections of other {@link Entity}s must extends this class.
 * 
 *
 * @param <Entity> Entity class
 * @param <DTO>    DTO class
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
     * <br>
     * The {@link Entity id is automatically set}
     *
     * @param dto source {@link DTO}
     * @param e   target {@link Entity}
     * @param withDisabledChildren if true requires to put disabled entities in lists
     */
    protected abstract void fillDto(Entity e, DTO dto,boolean withDisabledChildren);
    
   
    
}
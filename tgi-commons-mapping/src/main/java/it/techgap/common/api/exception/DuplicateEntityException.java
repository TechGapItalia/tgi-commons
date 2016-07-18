package it.techgap.common.api.exception;

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

import com.google.common.collect.ImmutableMap;
import org.springframework.data.domain.Persistable;

import java.util.Map;

public class DuplicateEntityException extends EntityCheckException {

	private static final long serialVersionUID = 8804862663449997833L;

    public DuplicateEntityException(Class<? extends Persistable> entityClass, Object id) {
        this(entityClass, "id", id);
    }

	public DuplicateEntityException(Class<? extends Persistable> entityClass, String field, Object value) {
        this(entityClass, ImmutableMap.<String, Object>builder().put(field, value).build());
    }

    public DuplicateEntityException(Class<? extends Persistable> entityClass, Map<String, Object> fields) {
        super(toMessage(entityClass, fields));
    }

    protected static String toMessage(Class<? extends Persistable> entityClass, Map<String, Object> fields) {
        StringBuilder sb = new StringBuilder()
                .append("Duplicate ")
                .append(entityClass.getSimpleName())
                .append(" with ");
        for (Map.Entry<String, Object> field : fields.entrySet()) {
            sb.append(field.getKey()).append("=").append(String.valueOf(field.getValue())).append(" ");
        }
        return sb.append("found!").toString();
    }
}

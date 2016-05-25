package it.techgap.common.api.exception;

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

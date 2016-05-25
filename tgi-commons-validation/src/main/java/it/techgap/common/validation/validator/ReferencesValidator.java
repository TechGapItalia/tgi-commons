package it.techgap.common.validation.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Persistable;

import it.techgap.common.validation.References;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.Serializable;

public class ReferencesValidator implements ConstraintValidator<References, Serializable> {

    private static final Logger logger = LoggerFactory.getLogger(ReferencesValidator.class);

    @PersistenceContext
    EntityManager entityManager;

    private Class<? extends Persistable> entityClass;

    @Override
    public void initialize(References references) {
        this.entityClass = references.value();
    }

    @Override
    public boolean isValid(Serializable id, ConstraintValidatorContext constraintValidatorContext) {

        if (id == null) return true;

        logger.trace("Validating id {} for entity {}", id, entityClass);

        final Query q = entityManager.createQuery("SELECT c.id from " + entityClass.getSimpleName() + " c WHERE c.id = :id").setParameter("id", id);

        return (q.getResultList().size() > 0);
    }
}

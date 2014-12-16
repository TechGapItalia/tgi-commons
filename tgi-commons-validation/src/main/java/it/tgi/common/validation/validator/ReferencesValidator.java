package it.tgi.common.validation.validator;

import it.tgi.common.validation.References;
import org.springframework.data.domain.Persistable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.Serializable;

public class ReferencesValidator implements ConstraintValidator<References, Serializable> {

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

        final Query q = entityManager.createQuery("SELECT c.id from " + entityClass.getSimpleName() + " c WHERE c.id = :id").setParameter("id", id);

        return (q.getResultList().size() > 0);
    }
}

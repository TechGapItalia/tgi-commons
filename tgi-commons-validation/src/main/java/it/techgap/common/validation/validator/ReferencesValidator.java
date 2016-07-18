package it.techgap.common.validation.validator;

/*-
 * #%L
 * tgi-commons-validation
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

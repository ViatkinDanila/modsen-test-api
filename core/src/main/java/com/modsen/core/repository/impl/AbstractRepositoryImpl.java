package com.modsen.core.repository.impl;

import com.modsen.core.constant.ColumnConstant;
import com.modsen.core.repository.AbstractRepository;
import com.modsen.core.model.entity.Meeting;
import com.modsen.core.repository.AbstractRepository;
import com.modsen.core.repository.sort.SortParams;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Transactional
public abstract class AbstractRepositoryImpl<T> implements AbstractRepository<T> {

    @PersistenceContext
    protected final EntityManager entityManager;
    protected final CriteriaBuilder criteriaBuilder;
    protected final Class<T> entityType;

    public AbstractRepositoryImpl(EntityManager entityManager,
                                  Class<T> entityType){
        this.entityManager = entityManager;
        criteriaBuilder = entityManager.getCriteriaBuilder();
        this.entityType = entityType;

    }

    @Override
    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Optional<T> findById(long id) {
        CriteriaQuery<T> entityQuery = criteriaBuilder.createQuery(entityType);

        Root<T> entity = entityQuery.from(entityType);
        Predicate fieldPredicate = criteriaBuilder.equal(entity.get(ColumnConstant.ID), id);
        entityQuery.where(fieldPredicate);
        entityQuery.select(entity);
        TypedQuery<T> query = entityManager.createQuery(entityQuery);
        if (query.getResultList().size() == 0){
            return Optional.empty();
        }
        return Optional.of(query.getSingleResult());
    }

    @Override
    public List<T> findAll() {
        CriteriaQuery<T> query = criteriaBuilder.createQuery(entityType);
        Root<T> root = query.from(entityType);
        return entityManager.createQuery(query.select(root)).getResultList();
    }

    @Override
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }
}

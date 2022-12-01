package com.modsen.core.repository.impl;

import com.modsen.core.model.entity.Meeting;
import com.modsen.core.repository.MeetingRepository;
import com.modsen.core.repository.sort.SearchParams;
import com.modsen.core.repository.sort.SortParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Transactional
@Repository
public class MeetingRepositoryImpl extends AbstractRepositoryImpl<Meeting> implements MeetingRepository {

    private static final String ASC_TYPE = "asc";

    @Autowired
    public MeetingRepositoryImpl(EntityManager entityManager){
        super(entityManager, Meeting.class);
    }

    @Override
    public List<Meeting> findAll(SortParams sortParams, SearchParams searchParams){
        CriteriaQuery<Meeting> query = criteriaBuilder.createQuery(Meeting.class);
        Root<Meeting> root = query.from(Meeting.class);
        query.select(root);
        query.distinct(true);

        List<Predicate> predicates = ifExistAddSearchParam(root, searchParams);
        List<Order> orders = ifExistAddOrders(root, sortParams);

        Predicate[] predArray = predListToArray(predicates);
        query.where(predArray);
        query.orderBy(orders);


        return  entityManager.createQuery(query).getResultList();
    }

    private Predicate[] predListToArray(List<Predicate> predList){
        Predicate[] predArray = new Predicate[predList.size()];
        predList.toArray(predArray);
        return predArray;
    }

    private List<Predicate> ifExistAddSearchParam( Root<Meeting> certificateRoot, SearchParams searchParams){
        List<Predicate> predicates = new LinkedList<>();
        if (searchParams != null) {
            Set<String> searchParamsSet = searchParams.getSearchParamsMap().keySet();
            for (String searchParam : searchParamsSet){
                Object searchInfo = searchParams.getSearchParamsMap().get(searchParam);
                predicates.add(criteriaBuilder.equal(certificateRoot.get(searchParam),searchInfo));
            }
        }
        return predicates;
    }

    private List<Order> ifExistAddOrders(Root<Meeting> certificateRoot, SortParams sortParams){
        List<Order> orderList = new LinkedList<>();
        if (sortParams != null) {
            Set<String> columnNames = sortParams.getSortParamsMap().keySet();
            for (String columnName : columnNames){
                if (sortParams.getSortParamsMap().get(columnName).toString().equalsIgnoreCase(ASC_TYPE)){
                    orderList.add(criteriaBuilder.asc(certificateRoot.get(columnName)));
                } else {
                    orderList.add(criteriaBuilder.desc(certificateRoot.get(columnName)));
                }
            }
        }
        return orderList;
    }
}

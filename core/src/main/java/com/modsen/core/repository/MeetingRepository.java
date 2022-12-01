package com.modsen.core.repository;

import com.modsen.core.model.entity.Meeting;
import com.modsen.core.repository.sort.SearchParams;
import com.modsen.core.repository.sort.SortParams;

import java.util.List;

public interface MeetingRepository extends AbstractRepository<Meeting> {
    List<Meeting> findAll(SortParams sortParams, SearchParams searchParams);
}

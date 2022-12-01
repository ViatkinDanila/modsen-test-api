package com.modsen.core.repository.sort;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class SearchParams {
    private final Map<String, Object> searchParamsMap = new HashMap<String,Object>();

    public SearchParams(List<String> searchingColumnNames, List<String> searchingInfo){
        for (int i = 0; i < searchingColumnNames.size() && i < searchingInfo.size(); i++){
            searchParamsMap.put(searchingColumnNames.get(i),searchingInfo.get(i));
        }
    }
}

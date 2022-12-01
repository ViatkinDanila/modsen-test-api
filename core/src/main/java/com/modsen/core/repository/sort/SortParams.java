package com.modsen.core.repository.sort;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.*;



@Getter
public class SortParams {
    private final Map<String, Object> sortParamsMap = new HashMap<String,Object>();

    public SortParams(List<String> sortingColumnNames, List<String> sortingTypes){
        for (int i = 0; i < sortingColumnNames.size() && i < sortingTypes.size(); i++){
            sortParamsMap.put(sortingColumnNames.get(i),sortingTypes.get(i));
        }
    }

    public SortParams(List<String> sortingColumnNames, String sortingType){
        for (int i = 0; i < sortingColumnNames.size(); i++){
            sortParamsMap.put(sortingColumnNames.get(i),sortingType);
        }
    }

}

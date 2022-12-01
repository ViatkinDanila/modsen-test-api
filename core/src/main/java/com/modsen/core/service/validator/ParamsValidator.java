package com.modsen.core.service.validator;

import com.modsen.core.constant.ColumnConstant;



import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ParamsValidator {
    private final List<String> validSortTypes = Arrays.asList("asc", "desc");
    private final Set<String> meetingColumns = ColumnConstant.getMeetingColumnAsSet();
    public boolean isSortParamsValid(List<String> sortParams){
        if (sortParams != null) {
            for(String sortParam : sortParams){
                if(!meetingColumns.contains(sortParam)){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isSortTypesValid(List<String> sortTypes){
        if (sortTypes != null){
            for (String sortType : sortTypes){
                boolean isSortTypeValid = validSortTypes.
                        stream().anyMatch(validSortType -> sortType.equalsIgnoreCase(validSortType));
                if(!isSortTypeValid){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isSearchParamsValid(List<String> searchParams){
        if (searchParams != null) {
            for(String searchParam : searchParams){
                if(!meetingColumns.contains(searchParam)){
                    return false;
                }
            }
        }
        return true;
    }
}

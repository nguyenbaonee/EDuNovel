package com.evo.order.application.mapper;

import com.evo.common.dto.request.SearchOrderRequest;
import com.evo.order.domain.query.SearchOrderQuery;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-30T15:09:07+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class QueryMapperImpl implements QueryMapper {

    @Override
    public SearchOrderQuery from(SearchOrderRequest searchFileRequest) {
        if ( searchFileRequest == null ) {
            return null;
        }

        SearchOrderQuery searchOrderQuery = new SearchOrderQuery();

        searchOrderQuery.setPageIndex( searchFileRequest.getPageIndex() );
        searchOrderQuery.setPageSize( searchFileRequest.getPageSize() );
        searchOrderQuery.setSortBy( searchFileRequest.getSortBy() );
        searchOrderQuery.setKeyword( searchFileRequest.getKeyword() );
        searchOrderQuery.setUserId( searchFileRequest.getUserId() );
        searchOrderQuery.setOrderStatus( searchFileRequest.getOrderStatus() );
        searchOrderQuery.setStartDate( searchFileRequest.getStartDate() );
        searchOrderQuery.setEndDate( searchFileRequest.getEndDate() );
        searchOrderQuery.setPrinted( searchFileRequest.getPrinted() );

        return searchOrderQuery;
    }
}

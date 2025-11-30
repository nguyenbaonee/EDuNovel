package com.evo.order.application.mapper;

import com.evo.common.dto.event.OrderEvent;
import com.evo.order.application.dto.request.CreateOrderItemRequest;
import com.evo.order.application.dto.request.CreateOrderRequest;
import com.evo.order.domain.command.CreateOrderCmd;
import com.evo.order.domain.command.UpdateOrderStatusCmd;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-30T15:09:07+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class CommandMapperImpl implements CommandMapper {

    @Override
    public CreateOrderCmd from(CreateOrderRequest request) {
        if ( request == null ) {
            return null;
        }

        CreateOrderCmd createOrderCmd = new CreateOrderCmd();

        if ( request.getUserId() != null ) {
            createOrderCmd.setUserId( UUID.fromString( request.getUserId() ) );
        }
        createOrderCmd.setPaymentMethod( request.getPaymentMethod() );
        createOrderCmd.setNote( request.getNote() );
        createOrderCmd.setReferencesId( request.getReferencesId() );

        return createOrderCmd;
    }

    @Override
    public CreateOrderRequest from(CreateOrderItemRequest request) {
        if ( request == null ) {
            return null;
        }

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();

        return createOrderRequest;
    }

    @Override
    public UpdateOrderStatusCmd from(OrderEvent event) {
        if ( event == null ) {
            return null;
        }

        UpdateOrderStatusCmd updateOrderStatusCmd = new UpdateOrderStatusCmd();

        updateOrderStatusCmd.setOrderCode( event.getOrderCode() );
        updateOrderStatusCmd.setStatus( event.getStatus() );

        return updateOrderStatusCmd;
    }
}

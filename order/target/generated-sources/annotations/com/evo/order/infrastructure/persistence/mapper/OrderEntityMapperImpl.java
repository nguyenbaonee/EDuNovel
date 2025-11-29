package com.evo.order.infrastructure.persistence.mapper;

import com.evo.order.domain.Order;
import com.evo.order.infrastructure.persistence.entity.OrderEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-30T15:09:07+0700",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class OrderEntityMapperImpl implements OrderEntityMapper {

    @Override
    public Order toDomainModel(OrderEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Order.OrderBuilder<?, ?> order = Order.builder();

        if ( entity.getCreatedBy() != null ) {
            order.createdBy( entity.getCreatedBy().toString() );
        }
        order.lastModifiedBy( entity.getLastModifiedBy() );
        order.createdAt( entity.getCreatedAt() );
        order.id( entity.getId() );
        order.orderCode( entity.getOrderCode() );
        order.userId( entity.getUserId() );
        order.orderStatus( entity.getOrderStatus() );
        order.paymentMethod( entity.getPaymentMethod() );
        order.paymentStatus( entity.getPaymentStatus() );
        order.fromName( entity.getFromName() );
        order.fromPhoneNumber( entity.getFromPhoneNumber() );
        order.fromAddressLine1( entity.getFromAddressLine1() );
        order.fromAddressLine2( entity.getFromAddressLine2() );
        order.fromWard( entity.getFromWard() );
        order.fromWardCode( entity.getFromWardCode() );
        order.fromDistrict( entity.getFromDistrict() );
        order.fromDistrictId( entity.getFromDistrictId() );
        order.fromCity( entity.getFromCity() );
        order.toName( entity.getToName() );
        order.toPhoneNumber( entity.getToPhoneNumber() );
        order.toAddressLine1( entity.getToAddressLine1() );
        order.toAddressLine2( entity.getToAddressLine2() );
        order.toWard( entity.getToWard() );
        order.toWardCode( entity.getToWardCode() );
        order.toDistrict( entity.getToDistrict() );
        order.toDistrictId( entity.getToDistrictId() );
        order.toCity( entity.getToCity() );
        order.returnName( entity.getReturnName() );
        order.returnPhoneNumber( entity.getReturnPhoneNumber() );
        order.returnAddressLine1( entity.getReturnAddressLine1() );
        order.returnAddressLine2( entity.getReturnAddressLine2() );
        order.returnWard( entity.getReturnWard() );
        order.returnWardCode( entity.getReturnWardCode() );
        order.returnDistrict( entity.getReturnDistrict() );
        order.returnDistrictId( entity.getReturnDistrictId() );
        order.returnCity( entity.getReturnCity() );
        if ( entity.getTotalProductVariant() != null ) {
            order.totalProductVariant( entity.getTotalProductVariant() );
        }
        if ( entity.getShipmentFee() != null ) {
            order.shipmentFee( entity.getShipmentFee() );
        }
        order.totalPrice( entity.getTotalPrice() );
        order.cashbackUsed( entity.getCashbackUsed() );
        order.rejectReason( entity.getRejectReason() );
        order.note( entity.getNote() );
        order.referencesId( entity.getReferencesId() );
        if ( entity.getTotalWeight() != null ) {
            order.totalWeight( entity.getTotalWeight() );
        }
        if ( entity.getTotalHeight() != null ) {
            order.totalHeight( entity.getTotalHeight() );
        }
        if ( entity.getTotalWidth() != null ) {
            order.totalWidth( entity.getTotalWidth() );
        }
        if ( entity.getTotalLength() != null ) {
            order.totalLength( entity.getTotalLength() );
        }
        order.paymentUrl( entity.getPaymentUrl() );
        order.printed( entity.getPrinted() );
        order.GHNOrderCode( entity.getGHNOrderCode() );

        return order.build();
    }

    @Override
    public OrderEntity toEntity(Order domain) {
        if ( domain == null ) {
            return null;
        }

        OrderEntity.OrderEntityBuilder orderEntity = OrderEntity.builder();

        orderEntity.id( domain.getId() );
        orderEntity.userId( domain.getUserId() );
        orderEntity.orderStatus( domain.getOrderStatus() );
        orderEntity.orderCode( domain.getOrderCode() );
        orderEntity.paymentMethod( domain.getPaymentMethod() );
        orderEntity.paymentStatus( domain.getPaymentStatus() );
        orderEntity.fromName( domain.getFromName() );
        orderEntity.fromPhoneNumber( domain.getFromPhoneNumber() );
        orderEntity.fromAddressLine1( domain.getFromAddressLine1() );
        orderEntity.fromAddressLine2( domain.getFromAddressLine2() );
        orderEntity.fromWard( domain.getFromWard() );
        orderEntity.fromWardCode( domain.getFromWardCode() );
        orderEntity.fromDistrict( domain.getFromDistrict() );
        orderEntity.fromDistrictId( domain.getFromDistrictId() );
        orderEntity.fromCity( domain.getFromCity() );
        orderEntity.toName( domain.getToName() );
        orderEntity.toPhoneNumber( domain.getToPhoneNumber() );
        orderEntity.toAddressLine1( domain.getToAddressLine1() );
        orderEntity.toAddressLine2( domain.getToAddressLine2() );
        orderEntity.toWard( domain.getToWard() );
        orderEntity.toWardCode( domain.getToWardCode() );
        orderEntity.toDistrict( domain.getToDistrict() );
        orderEntity.toDistrictId( domain.getToDistrictId() );
        orderEntity.toCity( domain.getToCity() );
        orderEntity.returnName( domain.getReturnName() );
        orderEntity.returnPhoneNumber( domain.getReturnPhoneNumber() );
        orderEntity.returnAddressLine1( domain.getReturnAddressLine1() );
        orderEntity.returnAddressLine2( domain.getReturnAddressLine2() );
        orderEntity.returnWard( domain.getReturnWard() );
        orderEntity.returnWardCode( domain.getReturnWardCode() );
        orderEntity.returnDistrict( domain.getReturnDistrict() );
        orderEntity.returnDistrictId( domain.getReturnDistrictId() );
        orderEntity.returnCity( domain.getReturnCity() );
        orderEntity.totalProductVariant( domain.getTotalProductVariant() );
        orderEntity.totalPrice( domain.getTotalPrice() );
        orderEntity.cashbackUsed( domain.getCashbackUsed() );
        orderEntity.rejectReason( domain.getRejectReason() );
        orderEntity.note( domain.getNote() );
        orderEntity.referencesId( domain.getReferencesId() );
        orderEntity.totalWeight( domain.getTotalWeight() );
        orderEntity.totalHeight( domain.getTotalHeight() );
        orderEntity.totalWidth( domain.getTotalWidth() );
        orderEntity.totalLength( domain.getTotalLength() );
        orderEntity.printed( domain.getPrinted() );
        orderEntity.GHNOrderCode( domain.getGHNOrderCode() );
        orderEntity.paymentUrl( domain.getPaymentUrl() );
        orderEntity.shipmentFee( domain.getShipmentFee() );

        return orderEntity.build();
    }

    @Override
    public List<Order> toDomainModelList(List<OrderEntity> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<Order> list = new ArrayList<Order>( entityList.size() );
        for ( OrderEntity orderEntity : entityList ) {
            list.add( toDomainModel( orderEntity ) );
        }

        return list;
    }

    @Override
    public List<OrderEntity> toEntityList(List<Order> domainList) {
        if ( domainList == null ) {
            return null;
        }

        List<OrderEntity> list = new ArrayList<OrderEntity>( domainList.size() );
        for ( Order order : domainList ) {
            list.add( toEntity( order ) );
        }

        return list;
    }
}

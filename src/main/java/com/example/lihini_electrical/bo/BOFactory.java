package com.example.lihini_electrical.bo;


import com.example.lihini_electrical.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){

    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)?boFactory=
                new BOFactory():boFactory;

    }
    public enum BOTypes{
        CUSTOMER,DELIVERY,DEPARTMENT,DISCOUNT,EMPLOYEE,INVENTORY,ORDERS,
        PAYMENT,PRODUCT,SUPPLIER,VEHICLE,WAREHOUSE,WARRANTY
    }
    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:
                return new CustomerBOImpl();
            case DELIVERY:
                return new DeliveryBOImpl();
            case DEPARTMENT:
                return new DepartmentBOImpl();
            case DISCOUNT:
                return new DiscountBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case INVENTORY:
                return new InventoryBOImpl();
            case ORDERS:
                return new OrdersBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case PRODUCT:
                return new ProductBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case VEHICLE:
                return new VehicleBOImpl();
            case WAREHOUSE:
                return new WarehouseBOImpl();
            case WARRANTY:
                return new WarrantyBOImpl();
            default:
                return null;
        }
    }
}

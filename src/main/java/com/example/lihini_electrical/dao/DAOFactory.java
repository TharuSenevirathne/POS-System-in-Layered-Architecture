package com.example.lihini_electrical.dao;


import com.example.lihini_electrical.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){
    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory==null)?daoFactory
                =new DAOFactory():daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER,DELIVERY,DEPARTMENT,DISCOUNT,EMPLOYEE,INVENTORY,ORDERS,
        PAYMENT,PRODUCT,SUPPLIER,VEHICLE,WAREHOUSE,WARRANTY
    }
    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case DELIVERY:
                return new DeliveryDAOImpl();
            case DEPARTMENT:
                return new DepartmentDAOImpl();
            case DISCOUNT:
                return new DiscountDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case INVENTORY:
                return new InventoryDAOImpl();
            case ORDERS:
                return new OrdersDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case PRODUCT:
                return new ProductDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case VEHICLE:
                return new VehicleDAOImpl();
            case WAREHOUSE:
                return new WarehouseDAOImpl();
            case WARRANTY:
                return new WarrantyDAOImpl();
            default:
                return null;
        }
    }

}

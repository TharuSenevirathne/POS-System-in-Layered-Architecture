module LIHINI.ELECTRICAL {
    requires java.mail;
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires static lombok;
    requires net.sf.jasperreports.core;

    requires org.apache.commons.io;
    exports com.example.lihini_electrical;
    exports com.example.lihini_electrical.controller;
    opens com.example.lihini_electrical.controller to javafx.fxml;
    exports com.example.lihini_electrical.dto;
    exports com.example.lihini_electrical.entity;
    exports com.example.lihini_electrical.bo;
    exports com.example.lihini_electrical.bo.custom;
    exports com.example.lihini_electrical.dao;
    exports com.example.lihini_electrical.dao.custom;
    exports com.example.lihini_electrical.tdm;

}
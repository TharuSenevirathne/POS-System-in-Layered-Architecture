package com.example.lihini_electrical.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {
    ArrayList<T> getAll() throws SQLException;
    boolean save(T dto) throws SQLException;
    boolean update(T dto) throws SQLException;
    boolean delete(String id) throws SQLException, ClassNotFoundException;
    boolean exist(String id) throws SQLException;
    String generateId() throws SQLException;
    public T findById(String id) throws SQLException;
}

package com.example.lihini_electrical.dao.custom.impl;

import com.example.lihini_electrical.dao.SQLUtil;
import com.example.lihini_electrical.dao.custom.ProductDAO;
import com.example.lihini_electrical.entity.Delivery;
import com.example.lihini_electrical.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public ArrayList<Product> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Product");
        ArrayList<Product> productDTOS = new ArrayList<>();
        while (rst.next()) {
            productDTOS.add(new Product(rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getString(4),
                    rst.getString(5)));
        }
        return productDTOS;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select pro_id  from Product order by pro_id  desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String subString = lastId.substring(1);
            int i = Integer.parseInt(subString);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }

    @Override
    public boolean delete(String productId) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("delete from Product where pro_id =?", productId);
    }

    @Override
    public boolean save(Product product) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("insert into Product values (?,?,?,?,?)",
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getInventoryId()
        );    }

    @Override
    public boolean update(Product product) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute( "update Product set pro_name =? ,Qty  =? ,pro_quantity,in_id =? where pro_id =?",
                product.getProductId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getInventoryId()
        );    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select pro_id from Product");
        ArrayList<String> productIds = new ArrayList<>();
        while (rst.next()) {
            productIds.add(rst.getString(1));
        }

        return productIds;    }

    @Override
    public Product search(String selectedProductId) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("select * from Product where pro_id=?", selectedProductId);

        rst.next();
        return new Product( rst.getString("product id"),
                rst.getString("name"),
                rst.getDouble("price"),
                rst.getString("inventoryId"),
                rst.getString("quantity"));
        }
}

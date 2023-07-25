package com.example.demo;

import com.example.demo.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcCustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private static String SELECT_BY_NAME_SQL ="select * from customers WHERE name = ?";
    private static String SELECT_ALL_SQL ="select * from customers";
    private static String INSERT_SQL ="INSERT INTO customers(customer_id,name,email) VALUES (UNHEX(REPLACE(?,'-','')),?,?)";
    private static String UPDATE_BY_ID_SQL ="UPDATE customers SET name = ? WHERE customer_id = UNHEX(REPLACE(?,'-',''))";
    private static String DELETE_ALL_SQL ="DELETE FROM customers";


    public void transactionTest(Customer customer){
        String updateNameSql = "UPDATE customers SET name = ? WHERE customer_id = UNHEX(REPLACE(?,'-',''))";
        String updateEmailSql = "UPDATE customers SET email = ? WHERE customer_id = UNHEX(REPLACE(?,'-',''))";
        Connection connection = null;
        try {connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt",
                "root",
                "qlalfqjsgh1!");
            connection.setAutoCommit(false);
            try (
                    var updateNameStatement =connection.prepareStatement(updateNameSql);
                    var updateEmailStatement =connection.prepareStatement(updateEmailSql);
            ) {
                connection.setAutoCommit(false);
                updateNameStatement.setString(1, customer.getName());
                updateNameStatement.setBytes(2, customer.getCustomerId().toString().getBytes());
                updateNameStatement.executeUpdate();

                updateEmailStatement.setString(1, customer.getEmail());
                updateEmailStatement.setBytes(2, customer.getCustomerId().toString().getBytes());
                updateEmailStatement.executeUpdate();
                connection.setAutoCommit(true);
            }
        }catch (SQLException exception){
            if (connection != null){
                try{
                    connection.rollback();
                    connection.close();
                }catch(SQLException throwables){
                    logger.error("Got error while closing connection", throwables);
                    throw new RuntimeException(exception);
                }
            }
            logger.error("Got error while closing connection", exception);
            throw new RuntimeException(exception);
        }
    }
    public List<String> findNames(String name){
        List<String> names = new ArrayList<>();
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "qlalfqjsgh1!");
                var statement = connection.prepareStatement(SELECT_BY_NAME_SQL);
        ){
            statement.setString(1,name);
            logger.info(String.format("statement ->{%s}",statement));
            try (var resultSet = statement.executeQuery()){
                while (resultSet.next()){
                    var customerName = resultSet.getString("name");
                    var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                    var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    names.add(customerName);
                }
            }
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection",throwables);
        }
        return names;
    }
    public List<String> findAllNames(){
        List<String> names = new ArrayList<>();
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "qlalfqjsgh1!");
                var statement = connection.prepareStatement(SELECT_ALL_SQL);
                var resultSet = statement.executeQuery()
        ){
            while (resultSet.next()){
                var customerName = resultSet.getString("name");
                var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                names.add(customerName);
            }
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection",throwables);
        }
        return names;
    }

    public int insertCustomer(UUID customerId,String name,String email){
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "qlalfqjsgh1!");
                var statement = connection.prepareStatement(INSERT_SQL);
        ) {
            statement.setBytes(1, customerId.toString().getBytes());
            statement.setString(2, name);
            statement.setString(3, email);
            return statement.executeUpdate();
        }catch (SQLException throwables) {
            logger.error("Got error while closing connection",throwables);
        }
        return 0;
    }
    public int updateCustomerName(UUID customerId,String name) {
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "qlalfqjsgh1!");
                var statement = connection.prepareStatement(UPDATE_BY_ID_SQL);
        ) {
            statement.setString(1, name);
            statement.setBytes(2, customerId.toString().getBytes());
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }
        return 0;
    }
    public int deleteAllCustomers(){
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "qlalfqjsgh1!");
                var statement = connection.prepareStatement(DELETE_ALL_SQL);
        ) {
            return statement.executeUpdate();
        }catch (SQLException throwables) {
            logger.error("Got error while closing connection",throwables);
        }
        return 0;
    }
    public List<UUID> findAllIds(){
        List<UUID> names = new ArrayList<>();
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "qlalfqjsgh1!");
                var statement = connection.prepareStatement(SELECT_ALL_SQL);
                var resultSet = statement.executeQuery()
        ){
            while (resultSet.next()){
                var customerName = resultSet.getString("name");
                var customerId =toUUID(resultSet.getBytes("customer_id"));
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                names.add(customerId);
            }
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection",throwables);
        }
        return names;
    }
    static UUID toUUID(byte[] bytes){
        var byteBuffer=ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(),byteBuffer.getLong());
    }
    public static void main(String[] args) throws SQLException {
        var customerRepository = new JdbcCustomerRepository();
        customerRepository.transactionTest(new Customer(UUID.fromString("e0fba47b-1e88-43dc-9459-faf90f36e97d"),
                "update-user",
                "new-user@gmail.com", LocalDateTime.now()));

//        var count = customerRepository.deleteAllCustomers();
//        logger.info("deleted count->{}",count);
//        var customerId = UUID.randomUUID();
//        logger.info("created customerId ->{}",customerId);
//        customerRepository.insertCustomer(customerId,"new-user","new-user@gmail.com");
//        customerRepository.insertCustomer(UUID.randomUUID(),"new-user2","new-user2@gmail.com");
//        customerRepository.findAllIds().forEach(v->logger.info("Found customerId:{}",v));
    }
}

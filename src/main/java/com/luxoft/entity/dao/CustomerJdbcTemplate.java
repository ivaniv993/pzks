package com.luxoft.entity.dao;

import com.luxoft.entity.model.Customer;
import com.luxoft.entity.model.Product;
import com.luxoft.entity.service.CustomerService;
import com.luxoft.utils.CustomerUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.xml.sax.SAXException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.Map.Entry;

import static java.lang.String.format;
import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * Created by IIvaniv on 01.10.2015.
 */
@Repository
@Qualifier("CustomerJdbcTemplate")
public class CustomerJdbcTemplate implements CustomerService {

    Logger log = Logger.getLogger(CustomerJdbcTemplate.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private static final String CSV_PATH_ACTUAL_RESULT = "C://users/iivaniv/actual.csv";
    private static final String CSV_PATH_EXPECTED_RESULT = "C://users/iivaniv/expected.csv";
    private static final int PAGE_SIZE = 10000;
    private static final String SELECT_ALL_EXPECTED_CUSTOMER = "SELECT * FROM expected_customer c WHERE  c.id > ? and c.id <= ? ORDER BY c.id";
    private static final String SELECT_ALL_COMPUTED_CUSTOMER = "SELECT * FROM actual_customer dc WHERE  dc.id > ? and dc.id <= ? ORDER BY dc.id";


    @Override
    public void getProductFewCriteriaBuilder() {

        CriteriaBuilder cb = entityManagerFactory.getCriteriaBuilder();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        CriteriaQuery<Product> q = cb.createQuery(Product.class);
        Root<Product> p = q.from(Product.class);
        CriteriaQuery<Product> select = q.select(p);
        TypedQuery<Product> typedQuery = entityManager.createQuery(select);
        typedQuery.getResultList();

        for (Product product : typedQuery.getResultList()){
            System.out.println(product.getName());
        }
    }

    @Override
    public List<Customer> selectAllComputedCustomer(int fromIndex, int toIndex) {
        Object[] arg = {fromIndex, toIndex};
        return jdbcTemplate.query(SELECT_ALL_COMPUTED_CUSTOMER, arg, new CustomerMapper());
    }

    @Override
    public List<Customer> selectAllExpectedCustomer(int fromIndex, int toIndex) {
        Object[] arg = {fromIndex, toIndex};
        return jdbcTemplate.query(SELECT_ALL_EXPECTED_CUSTOMER, arg, new CustomerMapper());
    }

    @Override
    public List<String> verifyTwoTables() {
        List<String> result = new ArrayList<>();

        int fromId = 0, toId = 0;
        List<Customer> actualResult = new ArrayList<>();
        List<Customer> expectedResult;

        do {
            toId += PAGE_SIZE;
            actualResult = selectAllComputedCustomer(fromId, toId);
            if (!actualResult.isEmpty()) {
                toId = actualResult.get(actualResult.size() - 1).getId().intValue();
            }
            expectedResult = selectAllExpectedCustomer(fromId, toId);
            fromId = toId;


            Map<Long, Customer> actual = convertListToMap(actualResult);
            Map<Long, Customer> expected = convertListToMap(expectedResult);
            result.addAll(validateTwoList(expected, actual));
        } while (!actualResult.isEmpty() || !expectedResult.isEmpty());

//        for (String aResult : result) {
//            System.out.println(aResult);
//        }
        return result;
    }

    private void parceListToCsv(List<Customer> customers, String path) {

        CSVFormat csvFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
        FileWriter fileWriter = null;
        CSVPrinter csvPrinter = null;

        try {
            fileWriter = new FileWriter(path);
            csvPrinter = new CSVPrinter( fileWriter, csvFormat);
            csvPrinter.printRecords(customers);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (fileWriter != null) {
                    fileWriter.flush();
                    fileWriter.close();
                }
                if (csvPrinter != null) {
                    csvPrinter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    private List<String> validateTwoList(Map<Long, Customer> expected, Map<Long, Customer> actual) {
        Iterator<Entry<Long, Customer>> aIt = actual.entrySet().iterator();
        List<String> result = new ArrayList<>();
        while (aIt.hasNext()) {
            Customer a = aIt.next().getValue();
            aIt.remove();

            Customer e = expected.remove(a.getId());

            if (e == null) {
                result.add(format("Object with id [%d] record is redundant in computed queue ", a.getId()));
                continue;
            }

            List<String> msg = CustomerUtils.compareCustomer(a, e);
            if (isEmpty(msg)) {
                continue;
            }

            result.addAll(msg);
        }

        // missingObject()
        Iterator<Entry<Long, Customer>> eIt = expected.entrySet().iterator();
        while (eIt.hasNext()) {
            Customer e = eIt.next().getValue();
            eIt.remove();

            result.add(format("Object with id [%d] record is missing in computed queue ", e.getId()));
        }
        return result;
    }

    private static class CustomerMapper implements RowMapper<Customer> {

        public Customer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Customer customer = new Customer();
            customer.setFirstName(resultSet.getString("first_name"));
            customer.setLastName(resultSet.getString("last_name"));
            customer.setCallPhone(resultSet.getString("call_phone"));
            customer.setId(resultSet.getLong("id"));
            return customer;
        }
    }

    @Override
    public int[] batchUpdate(final List<Customer> customers) {

        return null;
//                namedJdbcTemplate.batchUpdate("update customer set first_name = ? where id < 100", new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
//                preparedStatement.setString(1, customers.get(i).getFirstName());
//            }
//
//            @Override
//            public int getBatchSize() {
//                return customers.size();
//            }
//        });
    }



    private Map<Long, Customer> convertListToMap(List<Customer> customerList) {
        Map<Long, Customer> map = new TreeMap<>();
        for (Customer customer : customerList) {
            map.put(customer.getId(), customer);
        }
        return map;
    }


}

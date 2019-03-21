package com.exam.demoApi.common;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yunsung Kim
 */
@Slf4j
public class RegionIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object o)
        throws HibernateException {
        String prefix = "region_";
        Connection connection = session.connection();

        try {
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("select count(ID) as Id from REGION");

            if (rs.next()) {
                String generatedId = prefix + StringUtils.leftPad(String.valueOf(rs.getInt(1) + 1), 4, "0");
                log.info("Generated Id: " + generatedId);
                return generatedId;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return null;
    }
}

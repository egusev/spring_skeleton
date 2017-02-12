package ru.erfolk;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import com.github.springtestdbunit.operation.DatabaseOperationLookup;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.filter.IColumnFilter;
import org.dbunit.operation.AbstractOperation;
import org.dbunit.operation.CompositeOperation;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@DbUnitConfiguration(databaseConnection = "databaseDataSourceConnectionFactoryBean", databaseOperationLookup = BaseDBUnitTest.OperationLookup.class)
@ContextConfiguration(classes = BaseDBUnitTest.class)
@TestExecutionListeners(
        {
                DirtiesContextBeforeModesTestExecutionListener.class,
                DependencyInjectionTestExecutionListener.class,
                DirtiesContextTestExecutionListener.class,
                TransactionalTestExecutionListener.class,
                SqlScriptsTestExecutionListener.class,
                WithSecurityContextTestExecutionListener.class,
                DbUnitTestExecutionListener.class
        }
)
public class BaseDBUnitTest {

    private static final String TRUNCATE_SCHEMA = "TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK";
    private static final String DISABLE_KEYS = "SET DATABASE REFERENTIAL INTEGRITY FALSE;";
    private static final String ENABLE_KEYS = "SET DATABASE REFERENTIAL INTEGRITY TRUE;";

    public static final class OperationLookup implements DatabaseOperationLookup {
        private static Map<DatabaseOperation, org.dbunit.operation.DatabaseOperation> OPERATION_LOOKUP;

        static {
            OPERATION_LOOKUP = new ConcurrentHashMap<>();
            CompositeOperation operation = new CompositeOperation(new org.dbunit.operation.DatabaseOperation[]{
                    new DisableIntegrityOperation(),
                    new TruncateWithRestartSequences(),
                    org.dbunit.operation.DatabaseOperation.INSERT,
                    new EnableIntegrityOperation()
            });
            OPERATION_LOOKUP.put(DatabaseOperation.CLEAN_INSERT, operation);
        }

        @Override
        public org.dbunit.operation.DatabaseOperation get(final DatabaseOperation operation) {
            return OPERATION_LOOKUP.get(operation);
        }
    }

    @Autowired
    private DataSource dataSource;

    @After
    public void after() throws SQLException {
        executeSql(DISABLE_KEYS);
    }

    @Before
    public void before() throws SQLException {
        executeSql(ENABLE_KEYS);
    }

    protected String loadFile(String name) throws IOException {
        return IOUtils.toString(BaseDBUnitTest.class.getResourceAsStream(name), Charset.defaultCharset());
    }

    private void executeSql(String sql) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(sql);
            }
        }
    }

    public static class DefaultColumnFilter extends BaseColumnFilter {
        public DefaultColumnFilter() {
            super(Arrays.asList("version", "creation_time", "modification_time"));
        }
    }

    public static abstract class BaseColumnFilter implements IColumnFilter {

        private final List<String> list;
        private String table;

        public BaseColumnFilter(List<String> list) {
            this.list = list;
        }

        public BaseColumnFilter(List<String> list, String table) {
            this(list);
            this.table = table;
        }

        @Override
        public boolean accept(String tableName, Column column) {
            if (StringUtils.isNotEmpty(table)) {
                return !tableName.equals(table) || !list.contains(column.getColumnName());
            }
            return !list.contains(column.getColumnName());
        }
    }

    @Bean
    public DatabaseConfigBean databaseConfigBean() {
        DatabaseConfigBean config = new DatabaseConfigBean();
        config.setEscapePattern("\"?\"");
        return config;
    }

    @Bean
    public DatabaseDataSourceConnectionFactoryBean databaseDataSourceConnectionFactoryBean() {
        DatabaseDataSourceConnectionFactoryBean bean = new DatabaseDataSourceConnectionFactoryBean();
        bean.setDatabaseConfig(databaseConfigBean());
        bean.setDataSource(dataSource);
        return bean;
    }

    @Bean(name = "mvcConversionService")
    public GenericConversionService genericConversionService() {
        return new GenericConversionService();
    }

    private static class TruncateWithRestartSequences extends AbstractOperation {
        @Override
        public void execute(IDatabaseConnection connection, IDataSet dataSet) throws DatabaseUnitException, SQLException {
            try (Statement statement = connection.getConnection().createStatement()) {
                statement.execute(TRUNCATE_SCHEMA);
            }
        }
    }

    private static class DisableIntegrityOperation extends AbstractOperation {
        @Override
        public void execute(IDatabaseConnection connection, IDataSet dataSet) throws DatabaseUnitException, SQLException {
            try (Statement statement = connection.getConnection().createStatement()) {
                statement.execute(DISABLE_KEYS);
            }
        }
    }

    private static class EnableIntegrityOperation extends AbstractOperation {
        @Override
        public void execute(IDatabaseConnection connection, IDataSet dataSet) throws DatabaseUnitException, SQLException {
            try (Statement statement = connection.getConnection().createStatement()) {
                statement.execute(ENABLE_KEYS);
            }
        }
    }

}

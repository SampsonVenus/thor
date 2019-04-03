package com.smc.csp.config;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.smc.csp.accessorial.AccessorialDao;
import com.smc.csp.audit.AuditDao;
import com.smc.csp.audit.AuditDaoImpl;
import com.smc.csp.dataset.DataSetDao;
import com.smc.csp.dataset.DataSetDaoImpl;
import com.smc.csp.fuel.FuelDao;
import com.smc.csp.lane.LaneDao;
import com.smc.csp.upload.api.DocumentDao;
import com.smc.csp.user.UserDao;
import com.smc.csp.utils.EmailService;

@Configuration
@ComponentScan("com.smc.csp")
@PropertySource({"classpath:/properties/server.properties"})
public class BaseTestConfig {
	
	@Autowired
	private InitialContext context;
	
	@Value("${db.ip}")
	private String database;

	@Value("${db.port}")
	private String port;
	
	protected final Log logger = LogFactory.getLog(getClass());

	@Primary
	@Bean(name = { "ContractDB"})
	public DataSource cDataSource() throws NamingException {
		//Construct DataSource
		BasicDataSource conPoolDS = new BasicDataSource ();
		conPoolDS.setUrl("jdbc:mysql://" + database + ":" + port + "/Contract?autoReconnect=true");
		conPoolDS.setUsername("cowner");
		conPoolDS.setPassword("BIfyitwInyJ1:5");
		
		try {
			context.bind("java:/comp/env/jdbc/ContractDB", conPoolDS);
		} catch (Exception e) {
			logger.info("JNDI Name already Bound");
		}
		return (BasicDataSource) context.lookup("java:/comp/env/jdbc/ContractDB");
	}
	
	@Bean
	public static InitialContext initialContext() throws NamingException {
		Properties properties = new Properties();
		properties.put(Context.INITIAL_CONTEXT_FACTORY,"org.apache.naming.java.javaURLContextFactory");
		properties.put(Context.URL_PKG_PREFIXES, "org.apache.naming");
		InitialContext context = new InitialContext(properties);
		try {
			context.createSubcontext("java:");
			context.createSubcontext("java:/comp");
			context.createSubcontext("java:/comp/env");
			context.createSubcontext("java:/comp/env/jdbc");
		} catch (Exception e) {
			
		}
		
		return context;
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	@Bean
    @Primary
    public DataSetDao  contractDao(DataSetDao contractDao) {
        return Mockito.spy(contractDao);
    }
	@Bean
    @Primary
    public AuditDao  auditDao(AuditDao auditDao) {
        return Mockito.spy(auditDao);
    }
	
	@Bean
    @Primary
    public UserDao  uSerDao(UserDao userDao) {
        return Mockito.spy(userDao);
    }
	
	@Bean
    @Primary
    public DocumentDao  documentDao(DocumentDao documentDao) {
        return Mockito.spy(documentDao);
    }
	
	@Bean
    @Primary
    public LaneDao  laneDao(LaneDao laneDao) {
        return Mockito.spy(laneDao);
    }
	
	@Bean
    @Primary
    public AccessorialDao  accessorialDao(AccessorialDao accessorialDao) {
        return Mockito.spy(accessorialDao);
    }
	
	@Bean
    @Primary
    public FuelDao  fuelDao(FuelDao fuelDao) {
        return Mockito.spy(fuelDao);
    }
	
	
	@Bean
    @Primary
    public EmailService emailService() {
        return Mockito.mock(EmailService.class);
    }
	
}

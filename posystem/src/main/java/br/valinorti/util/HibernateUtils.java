package br.valinorti.util;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Classe utilitaria usada para recuperar a sessao a partir {@link SessionFactory}.
 * 
 * @author Rafael Chiairnelli
 *
 */
public class HibernateUtils {
	
	private static Logger logger = Logger.getLogger(HibernateUtils.class);
	
	private static SessionFactory sessionFactory;
	
	private static AnnotationConfiguration configuration;
	
    /**
     * 
     */
    public static void initSessionFactory(String fileName) {
    	if (sessionFactory==null) {
            try {
            	configuration = new AnnotationConfiguration();
            	sessionFactory = configuration.configure(fileName).buildSessionFactory();
            } catch (Throwable ex) {
            	logger.error(ex);
                throw new ExceptionInInitializerError(ex);
            }
    	}
    }
    
    /**
     * Abre ou recupera uma sess�o ativa do session factory
     * 
     * @return a sess�o corrente da {@link SessionFactory}.
     */
    @Deprecated
    public synchronized static Session getSession() {
    	Session sess = sessionFactory.getCurrentSession(); 
        return sess;
    }
    /**
     * Fecha a sess�o do hibernate caso ainda estiver aberta.
     * @param session
     */
    @Deprecated
    public synchronized static void closeSession(Session session){
    	if (session.isOpen()) {
    		session.close();
    	}
    }
    
    /**
     * 
     * @return
     */
    public synchronized static SessionFactory getSessionFactory() {
    	return sessionFactory;
    }
    
    
    public static void exportSchema(String fileName) {
		SchemaExport schemaExport = new SchemaExport(configuration);
		schemaExport.setOutputFile(fileName);
		schemaExport.setDelimiter(";");
		schemaExport.execute(true, false, false, false);
    }
    
}

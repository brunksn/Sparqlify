package org.aksw.sparqlify.web;

import java.lang.reflect.Type;

import javax.ws.rs.core.Context;

import org.aksw.commons.sparql.api.core.QueryExecutionFactory;
import org.aksw.commons.sparql.api.core.QueryExecutionStreaming;

import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;

//@Provider
public class QueryExecutionFactoryStreamingProvider
	extends AbstractHttpContextInjectable<QueryExecutionFactory<QueryExecutionStreaming>>
	implements InjectableProvider<Context, Type>
{
	public static QueryExecutionFactory<QueryExecutionStreaming> qeFactory;
	
	/*
	public QueryExecutionFactoryStreamingProvider(QueryExecutionFactory<QueryExecutionStreaming> qeFactory) {
		this.qeFactory = qeFactory;
	}*/

    @Override
    public Injectable<QueryExecutionFactory<QueryExecutionStreaming>> getInjectable(ComponentContext ic, Context a, Type c) {
    	return this;
    }

    @Override
    public ComponentScope getScope() {
        return ComponentScope.Singleton;
    }
    
    @Override
    public QueryExecutionFactory<QueryExecutionStreaming> getValue() {
    	return qeFactory;
    }
    
    @Override
    public QueryExecutionFactory<QueryExecutionStreaming> getValue(HttpContext c) {
    	//return qeFactory;
    	return getValue();
    }
}



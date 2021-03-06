package org.aksw.sparqlify.web;

import java.awt.geom.Point2D;
import java.net.URLEncoder;
import java.util.List;

import org.aksw.commons.sparql.api.compare.QueryExecutionFactoryCompare;
import org.aksw.commons.sparql.api.core.QueryExecutionFactory;
import org.aksw.commons.sparql.api.core.QueryExecutionStreaming;
import org.aksw.commons.sparql.api.core.QueryExecutionStreamingFactory;
import org.aksw.commons.sparql.api.http.QueryExecutionFactoryHttp;
import org.aksw.commons.sparql.api.limit.QueryExecutionFactoryLimit;
import org.aksw.commons.sparql.api.timeout.QueryExecutionFactoryTimeout;
import org.aksw.sparqlify.core.QueryExecutionFactorySparqlifyDs;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.spi.container.servlet.ServletContainer;

public class CompareMain {
	/**
	 * @param exitCode
	 */
	public static void printHelpAndExit(int exitCode) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(HttpSparqlEndpoint.class.getName(), cliOptions);
		System.exit(exitCode);
	}

	private static final Logger logger = LoggerFactory
			.getLogger(HttpSparqlEndpoint.class);
	private static final Options cliOptions = new Options();

	/**
	 * @param args
	 *            the command line arguments
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		/*
		PropertyConfigurator.configure("log4j.properties");
		LogManager.getLogManager().readConfiguration(
				new FileInputStream("jdklog.properties"));
		*/

		CommandLineParser cliParser = new GnuParser();

		cliOptions.addOption("P", "port", true, "Server port");
		cliOptions.addOption("C", "context", true, "Context e.g. /sparqlify");
		cliOptions.addOption("B", "backlog", true,
				"Maximum number of connections");

		
		CommandLine commandLine = cliParser.parse(cliOptions, args);
		
		String serviceUrlA;
		String serviceUrlB;

		String[] serviceArgs = commandLine.getArgs();
		if(serviceArgs.length != 2) {
			throw new RuntimeException("Expecting two URL to SPARQL endpoints which to compare as result");
		}
		
		serviceUrlA = serviceArgs[0];
		serviceUrlB = serviceArgs[1];

		
		// Parsing of command line args
		String portStr = commandLine.getOptionValue("P", "7531");
		//String backLogStr = commandLine.getOptionValue("B", "100");
		//String contextStr = commandLine.getOptionValue("C", "/sparqlify");
		int port = Integer.parseInt(portStr);
		//int backLog = Integer.parseInt(backLogStr);

		
		QueryExecutionFactory qefA = new QueryExecutionFactoryHttp(serviceUrlA);
		QueryExecutionFactory qefB = new QueryExecutionFactoryHttp(serviceUrlB);
		
		
		QueryExecutionFactory qef = new QueryExecutionStreamingFactory(new QueryExecutionFactoryCompare(qefA, qefB, true));
		
		

		HttpSparqlEndpoint.sparqler = qef;
		
		
		ServletHolder sh = new ServletHolder(ServletContainer.class);

		sh.setInitParameter(
				"com.sun.jersey.config.property.resourceConfigClass",
				"com.sun.jersey.api.core.PackagesResourceConfig");
		sh.setInitParameter("com.sun.jersey.config.property.packages",
				"org.aksw.sparqlify.web");

		Server server = new Server(port);
		ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
		context.addServlet(sh, "/*");		

		server.start();
	}

}

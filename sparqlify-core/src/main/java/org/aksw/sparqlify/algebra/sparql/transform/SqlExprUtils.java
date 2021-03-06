package org.aksw.sparqlify.algebra.sparql.transform;

import org.aksw.commons.factory.Factory2;
import org.aksw.sparqlify.algebra.sql.exprs.S_LogicalAnd;
import org.aksw.sparqlify.algebra.sql.exprs.S_LogicalOr;
import org.aksw.sparqlify.algebra.sql.exprs.SqlExpr;
import org.aksw.sparqlify.expr.util.ExprUtils;

public class SqlExprUtils
{
	public static SqlExpr orifyBalanced(Iterable<SqlExpr> exprs) {
		return ExprUtils.opifyBalanced(exprs, new Factory2<SqlExpr>() {
			@Override
			public SqlExpr create(SqlExpr a, SqlExpr b)
			{
				return new S_LogicalOr(a, b);
			}
		});		
	}	
	
	public static SqlExpr andifyBalanced(Iterable<SqlExpr> exprs) {
		return ExprUtils.opifyBalanced(exprs, new Factory2<SqlExpr>() {
			@Override
			public SqlExpr create(SqlExpr a, SqlExpr b)
			{
				return new S_LogicalAnd(a, b);
			}
		});		
	}	
	
}

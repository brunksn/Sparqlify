package org.aksw.sparqlify.algebra.sql.exprs2;

import org.aksw.sparqlify.core.TypeToken;
import org.openjena.atlas.io.IndentedWriter;

public class S_Equals
	extends SqlExpr2
{
	public S_Equals(SqlExpr left, SqlExpr right) {
		super(TypeToken.Boolean, "equals", left, right);
	}
	
	@Override
	public void asString(IndentedWriter writer) {
		writer.print("Equals");
		writeArgs(writer);
	}

	@Override
	public S_Equals copy(SqlExpr left, SqlExpr right) {
		S_Equals result = new S_Equals(left, right);
		return result;
	}
	
	public static S_Equals create(SqlExpr a, SqlExpr b) {
		return new S_Equals(a, b);
	}
}
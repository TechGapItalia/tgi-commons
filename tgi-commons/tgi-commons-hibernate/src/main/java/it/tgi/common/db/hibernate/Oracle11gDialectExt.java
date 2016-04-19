package it.tgi.common.db.hibernate;

import java.sql.Types;

import org.hibernate.dialect.Oracle10gDialect;

/**
 * Fix hibernate column type validation on double precision values.<br>
 * Oracle accepts the type "double precision", but stores it as "float(126)".
 * <br>
 * Whenever hibernate validates such a column, oracle returns "float(126)" and
 * hibernate therefore reports a mismatch. <br>
 * See https://hibernate.atlassian.net/browse/HHH-1961 or
 * https://hibernate.atlassian.net/browse/HHH-2315
 */
public class Oracle11gDialectExt extends Oracle10gDialect {

	public Oracle11gDialectExt() {
		super();
		registerColumnType(Types.DOUBLE, "float(126)");
	}

}
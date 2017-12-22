package it.techgap.common.db.hibernate;

/*-
 * #%L
 * TGI Commons - Hibernate Integration
 * %%
 * Copyright (C) 2016 TechGap Italia
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

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
package com.raycloud.util.daogen.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Description: SQL Keywords
 * 用于检查数据库的关键字段，如果符合给字段加上单引号
 * User: ouzhouyou@raycloud.com
 * Date: 13-11-22
 * Time: 下午1:15
 * Version: 1.0
 */
public class Keywords {

    public static String checkOrReplace(String keyword) {
        if (keywordSet.contains(keyword.toUpperCase())) {
            return "`" + keyword + "`";
        }
        return keyword;
    }

    static Set<String> keywordSet;

    static {
        keywordSet = new HashSet<String>();
        keywordSet.add("ALL");
        keywordSet.add("ALTER");
        keywordSet.add("AND");
        keywordSet.add("ANY");
        keywordSet.add("AS");

        keywordSet.add("ENABLE");
        keywordSet.add("DISABLE");

        keywordSet.add("ASC");
        keywordSet.add("BETWEEN");
        keywordSet.add("BY");
        keywordSet.add("CASE");
        keywordSet.add("CAST");

        keywordSet.add("CHECK");
        keywordSet.add("CONSTRAINT");
        keywordSet.add("CREATE");
        keywordSet.add("DATABASE");
        keywordSet.add("DEFAULT");
        keywordSet.add("COLUMN");
        keywordSet.add("TABLESPACE");
        keywordSet.add("PROCEDURE");
        keywordSet.add("FUNCTION");

        keywordSet.add("DELETE");
        keywordSet.add("DESC");
        keywordSet.add("DISTINCT");
        keywordSet.add("DROP");
        keywordSet.add("ELSE");
        keywordSet.add("EXPLAIN");

        keywordSet.add("END");
        keywordSet.add("ESCAPE");
        keywordSet.add("EXISTS");
        keywordSet.add("FOR");
        keywordSet.add("FOREIGN");

        keywordSet.add("FROM");
        keywordSet.add("FULL");
        keywordSet.add("GROUP");
        keywordSet.add("HAVING");
        keywordSet.add("IN");

        keywordSet.add("INDEX");
        keywordSet.add("INNER");
        keywordSet.add("INSERT");
        keywordSet.add("INTERSECT");
        keywordSet.add("INTERVAL");

        keywordSet.add("INTO");
        keywordSet.add("IS");
        keywordSet.add("JOIN");
        keywordSet.add("KEY");
        keywordSet.add("LEFT");

        keywordSet.add("LIKE");
        keywordSet.add("LOCK");
        keywordSet.add("MINUS");
        keywordSet.add("NOT");

        keywordSet.add("NULL");
        keywordSet.add("ON");
        keywordSet.add("OR");
        keywordSet.add("ORDER");
        keywordSet.add("OUTER");

        keywordSet.add("PRIMARY");
        keywordSet.add("REFERENCES");
        keywordSet.add("RIGHT");
        keywordSet.add("SCHEMA");
        keywordSet.add("SELECT");

        keywordSet.add("SET");
        keywordSet.add("SOME");
        keywordSet.add("TABLE");
        keywordSet.add("THEN");
        keywordSet.add("TRUNCATE");

        keywordSet.add("UNION");
        keywordSet.add("UNIQUE");
        keywordSet.add("UPDATE");
        keywordSet.add("VALUES");
        keywordSet.add("VIEW");
        keywordSet.add("SEQUENCE");
        keywordSet.add("TRIGGER");
        keywordSet.add("USER");

        keywordSet.add("WHEN");
        keywordSet.add("WHERE");
        keywordSet.add("XOR");

        keywordSet.add("OVER");
        keywordSet.add("TO");
        keywordSet.add("USE");

        keywordSet.add("REPLACE");

        keywordSet.add("COMMENT");
        keywordSet.add("COMPUTE");
        keywordSet.add("WITH");
        keywordSet.add("GRANT");
    }

}

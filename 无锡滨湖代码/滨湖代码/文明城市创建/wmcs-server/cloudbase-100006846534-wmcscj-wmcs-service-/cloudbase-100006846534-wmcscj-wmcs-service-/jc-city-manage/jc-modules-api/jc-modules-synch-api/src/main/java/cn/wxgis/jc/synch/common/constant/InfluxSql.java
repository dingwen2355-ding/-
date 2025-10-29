package cn.wxgis.jc.synch.common.constant;

public class InfluxSql {

    public static final String measurement = "wmcs";

    public static final String field = "timestamp";

    public static String limitSql = "SELECT * FROM \"" + measurement + "\"  %s ORDER BY time desc LIMIT %s OFFSET %s ";

    public static String countSql = "select COUNT(\"" + field + "\") from \"" + measurement + "\" %s";

    public static String newDataSql = "SELECT * FROM \"wmcs\" ORDER BY time desc LIMIT 1 OFFSET 0";

    public static String timeSql = "SELECT * FROM \"wmcs\" WHERE time > now() - %sm and channelId = '%s' ORDER BY time desc";

    public static String groupSql = "SELECT count(\"content\")  FROM \"wmcs\"  GROUP BY  \"type\", \"channelId\"";
}

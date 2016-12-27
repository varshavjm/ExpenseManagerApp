package com.example.varsha.myfirstandroidstudioproject;

/**
 * Created by varsha on 10/8/2016.
 */

public class ExpenseConstants {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS EXPENSE"  + " (" +
                    "NAME "+ TEXT_TYPE + COMMA_SEP +
                    "DAY "+ TEXT_TYPE + COMMA_SEP +
                    "MONTH"+TEXT_TYPE + COMMA_SEP +
                    "YEAR"+TEXT_TYPE + COMMA_SEP +
                    "AMOUNT "+ "REAL"
                     + " )";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS EXPENSE";
    public static final String SQL_CREATE_BUDGET =
            "CREATE TABLE IF NOT EXISTS BUDGET"  + " (" +
                    "AMOUNT "+ "REAL" + COMMA_SEP +
                    "MONTH"+TEXT_TYPE + COMMA_SEP +
                    "YEAR"+TEXT_TYPE
                    + " )";
    public static final String SQL_DELETE_BUDGET =
            "DROP TABLE IF EXISTS BUDGET";
}

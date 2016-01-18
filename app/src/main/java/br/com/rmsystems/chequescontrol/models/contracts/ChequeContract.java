package br.com.rmsystems.chequescontrol.models.contracts;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.rmsystems.chequescontrol.models.entites.Cheque;

/**
 * Created by robson on 17/07/15.
 */
public class ChequeContract {

    public static final String TABLE = "cheque";
    public static final String ID = "id";
    public static final String CLIENT = "client";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String DATE = "date";
    public static final String VALUE = "value";
    public static final String PAID = "paid";
    public static final String DESCRIPTION = "description";
    public static final String ACTIVE = "active";

    public static final String[] COLUNS = {ID, CLIENT, PHONE, ADDRESS, DATE, VALUE, PAID, DESCRIPTION, ACTIVE};

    public static String createTable() {
        final StringBuilder sql = new StringBuilder();
        /*sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(CLIENT + " TEXT, ");
        sql.append(PHONE + " TEXT, ");
        sql.append(ADDRESS + " TEXT, ");
        sql.append(DATE + " INTEGER, ");
        sql.append(VALUE + " REAL, ");
        sql.append(PAID + " INTEGER, ");
        sql.append(DESCRIPTION + " TEXT, ");
        sql.append(ACTIVE + " INTEGER ");
        sql.append(" ); ");*/
        return sql.toString();
    }

    public static ContentValues getContentValues(Cheque cheque) {
        ContentValues content = new ContentValues();
        /*content.put(ID, cheque.getId());
        content.put(CLIENT, cheque.getClient());
        content.put(PHONE, cheque.getPhone());
        content.put(ADDRESS, cheque.getAddress());
        content.put(DATE, cheque.getDate().getTime());
        content.put(VALUE, cheque.getValue());
        content.put(PAID, cheque.isPaid() ? 1 : 0);
        content.put(DESCRIPTION, cheque.getDescription());
        content.put(ACTIVE, cheque.isActive() ? 1 : 0);*/
        return content;
    }

    public static Cheque bind(Cursor cursor) {
        /*if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            ServiceOrder serviceOrder = new ServiceOrder();
            serviceOrder.setId((cursor.getInt(cursor.getColumnIndex(ID))));
            serviceOrder.setClient(cursor.getString(cursor.getColumnIndex(CLIENT)));
            serviceOrder.setPhone(cursor.getString(cursor.getColumnIndex(PHONE)));
            serviceOrder.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
            serviceOrder.setDate(new Date(cursor.getInt(cursor.getColumnIndex(DATE))));
            serviceOrder.setValue(cursor.getLong(cursor.getColumnIndex(VALUE)));
            serviceOrder.setPaid(cursor.getInt(cursor.getColumnIndex(PAID)) == 1);
            serviceOrder.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
            serviceOrder.setActive(cursor.getInt(cursor.getColumnIndex(ACTIVE)) == 1);
            return serviceOrder;
        }*/
        return null;
    }

    public static List<Cheque> bindList(Cursor cursor) {
        final List<Cheque> serviceOrders = new ArrayList<Cheque>();
        while (cursor.moveToNext()) {
            serviceOrders.add(bind(cursor));
        }
        return serviceOrders;
    }

}

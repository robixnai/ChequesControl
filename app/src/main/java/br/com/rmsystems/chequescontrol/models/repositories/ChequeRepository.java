package br.com.rmsystems.chequescontrol.models.repositories;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.com.rmsystems.chequescontrol.models.DatabaseHelper;
import br.com.rmsystems.chequescontrol.models.contracts.ChequeContract;
import br.com.rmsystems.chequescontrol.models.entites.Cheque;
import br.com.rmsystems.chequescontrol.util.AppUtil;

/**
 * Created by robson on 17/07/15.
 */
public final class ChequeRepository {

    private static class Singleton {
        public static final ChequeRepository INSTANCE = new ChequeRepository();
    }

    private ChequeRepository() {
        super();
    }

    public static ChequeRepository getInstance() {
        return Singleton.INSTANCE;
    }

    public void save(Cheque cheque) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        /*if (cheque.getId() == null) {
            db.insert(ServiceOrderContract.TABLE, null, ServiceOrderContract.getContentValues(cheque));
        } else {
            String where = ServiceOrderContract.ID + " = ?";
            String[] args = {cheque.getId().toString()};
            db.update(ServiceOrderContract.TABLE, ServiceOrderContract.getContentValues(cheque), where, args);
        }*/
        db.close();
        helper.close();
    }

    public void delete(Cheque cheque) {
        /*DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        String where = ChequeContract.ID + " = ?";
        String[] args = {cheque.getId().toString()};
        db.delete(ChequeContract.TABLE, where, args);
        db.close();
        helper.close();*/
    }

    public List<Cheque> getAll() {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(ChequeContract.TABLE, ChequeContract.COLUNS, null, null, null, null, ChequeContract.DATE);
        List<Cheque> serviceOrders = ChequeContract.bindList(cursor);
        db.close();
        helper.close();
        return serviceOrders;
    }

    public List<Cheque> getAllByActiveFlag(boolean flagActive) {
        DatabaseHelper helper = new DatabaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();

        String where = ChequeContract.ACTIVE + " = ?";
        String[] args = {(flagActive) ? "1" : "0"};

        Cursor cursor = db.query(ChequeContract.TABLE, ChequeContract.COLUNS, where, args, null, null, ChequeContract.DATE);
        List<Cheque> serviceOrders = ChequeContract.bindList(cursor);
        db.close();
        helper.close();
        return serviceOrders;
    }

}

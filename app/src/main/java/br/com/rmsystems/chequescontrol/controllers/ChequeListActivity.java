package br.com.rmsystems.chequescontrol.controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import org.apache.http.protocol.HTTP;

import java.util.List;

import br.com.rmsystems.chequescontrol.R;
import br.com.rmsystems.chequescontrol.adapters.ChequesListAdapter;
import br.com.rmsystems.chequescontrol.models.entites.Cheque;
import br.com.rmsystems.chequescontrol.util.AppUtil;

/**
 * Created by robson on 17/07/15.
 */
public class ChequeListActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    public static final int REQUEST_CODE_ADD = 1;
    public static final int REQUEST_CODE_EDIT = 2;

    private RecyclerView mCheques;
    private ChequesListAdapter mChequesListAdapter;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheque_list);

        this.bindElements();
    }

    private void bindElements() {
        mCheques = AppUtil.get(findViewById(R.id.recyclerViewCheques));
        mCheques.setHasFixedSize(true);
        mCheques.setLayoutManager(new LinearLayoutManager(this));

        final FloatingActionButton fabAdd = AppUtil.get(findViewById(R.id.fabAdd));
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent goToAddActivity = new Intent(ChequeListActivity.this, MainActivity.class);
                startActivityForResult(goToAddActivity, REQUEST_CODE_ADD);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.updateRecyclerItens();
    }

    private void updateRecyclerItens() {
        final List<Cheque> cheques = Cheque.getAll();
        if (mChequesListAdapter == null) {
            mChequesListAdapter = new ChequesListAdapter(cheques);
            mCheques.setAdapter(mChequesListAdapter);
        } else {
            mChequesListAdapter.setItens(cheques);
            mChequesListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_ADD) {
                Toast.makeText(this, R.string.msg_add_success, Toast.LENGTH_LONG).show();
                // Force onPrepareOptionsMenu call
                supportInvalidateOptionsMenu();
            } else if (requestCode == REQUEST_CODE_EDIT) {
                Toast.makeText(this, R.string.msg_edit_success, Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        final Cheque cheque = mChequesListAdapter.getSelectedItem();
        switch (item.getItemId()) {
            case R.id.actionEdit:
                final Intent goToEditActivity = new Intent(ChequeListActivity.this, MainActivity.class);
                /*goToEditActivity.putExtra(MainActivity.EXTRA_SERVICE_ORDER, cheque);
                goToEditActivity.putExtra(MainActivity.EXTRA_START_BENCHMARK, SystemClock.elapsedRealtime());*/
                super.startActivityForResult(goToEditActivity, REQUEST_CODE_EDIT);
                return true;
            case R.id.actionDelete:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.lbl_confirm)
                        //.setMessage((cheque.isActive()) ? getString(R.string.msg_archived) : getString(R.string.msg_unArchiving))
                        .setPositiveButton(R.string.lbl_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Set attribute active
                                /*if (cheque.isActive()) cheque.setActive(false);
                                else cheque.setActive(true);*/
                                // Update and show a message
                                //cheque.save();
                                Toast.makeText(ChequeListActivity.this,
                                        "Excluido com sucesso!"/*(cheque.isActive()) ? getString(R.string.msg_unArchiving_success) : getString(R.string.msg_archived_success)*/,
                                        Toast.LENGTH_LONG).show();
                                // Update recycler view dataset
                                updateRecyclerItens();
                                // Force onPrepareOptionsMenu call
                                supportInvalidateOptionsMenu();
                            }
                        })
                        .setNeutralButton(R.string.lbl_no, null)
                        .create().show();
                return true;
            case R.id.actionCall:
                // Best Practices: http://stackoverflow.com/questions/4275678/how-to-make-phone-call-using-intent-in-android
                /*final Intent goToSOPhoneCall = new Intent(Intent.ACTION_CALL  or Intent.ACTION_DIAL (no manifest permission needed) );
                goToSOPhoneCall.setData(Uri.parse("tel:" + cheque.getPhone()));
                startActivity(goToSOPhoneCall);*/
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_cheque_list_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionShare:
                // Create the text message with a string
                final Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, Cheque.getAll().toString());
                sendIntent.setType(HTTP.PLAIN_TEXT_TYPE);

                // Create intent to show the chooser dialog
                final Intent chooser = Intent.createChooser(sendIntent, getString(R.string.lbl_share_option));

                // Verify the original intent will resolve to at least one activity
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void optionSelected(Integer option, MenuItem item) {
        switch (option) {
            case AppUtil.ARCHIVED:
                if (item.isChecked()) item.setChecked(true);
                else item.setChecked(false);
                AppUtil.changeMenuFilterOption(option);
                this.updateRecyclerItens();
                this.activedShared();
                break;
            case AppUtil.OS_UNARCHIVING:
                if (item.isChecked()) item.setChecked(true);
                else item.setChecked(false);
                AppUtil.changeMenuFilterOption(option);
                this.updateRecyclerItens();
                this.activedShared();
                break;
        }
    }

    private void activedShared() {
        final MenuItem menuShare = mMenu.findItem(R.id.actionShare);
        final boolean menuShareVisible = mChequesListAdapter.getItemCount() > 0;
        menuShare.setEnabled(menuShareVisible).setVisible(menuShareVisible);
    }

}

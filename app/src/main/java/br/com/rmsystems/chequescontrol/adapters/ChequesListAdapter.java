package br.com.rmsystems.chequescontrol.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.rmsystems.chequescontrol.R;
import br.com.rmsystems.chequescontrol.models.entites.Cheque;
import br.com.rmsystems.chequescontrol.util.AppUtil;

/**
 * Created by robson on 17/07/15.
 */
public class ChequesListAdapter extends RecyclerView.Adapter<ChequesListAdapter.ViewHolder> {

    private List<Cheque> mItens;

    private int mPosition;
    private Menu mMenu;

    public ChequesListAdapter(List<Cheque> itens) {
        mItens = itens;
    }

    public void setItens(List<Cheque> itens) {
        this.mItens = itens;
    }

    public Cheque getSelectedItem() {
        return mItens.get(mPosition);
    }

    @Override
    public ChequesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.cheque_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();

        final Cheque cheque = mItens.get(position);
        holder.mTxtValue.setText(AppUtil.formatDecimal(cheque.getValue()));
        if (cheque.isPaid()) {
            holder.mTxtValue.setTextColor(holder.mTxtBeneficiary.getTextColors());
        } else {
            holder.mTxtValue.setTextColor(context.getResources().getColor(R.color.material_red_600));
        }
        holder.mTxtBeneficiary.setText(cheque.getBeneficiary());
        //holder.mTxtBeneficiary.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_text_client, 0, 0, 0);
        holder.mTxtDateMaturity.setText(AppUtil.formatDate(cheque.getMaturity()));
        //holder.mTxtDateMaturity.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_text_date, 0, 0, 0);

        /** Popup Menu */
        holder.mImageViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = holder.getLayoutPosition();
                final PopupMenu popup = new PopupMenu(context, v);
                // This context must implements OnMenuItemClickListener
                popup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) context);
                popup.inflate(R.menu.menu_cheque_list_popup);
                mMenu = popup.getMenu();
                updateMenuTitles();
                popup.show();
            }
        });
    }

    private void updateMenuTitles() {
        final Integer itemActive = AppUtil.getMenuActive();
        final MenuItem menuItem;

        switch (itemActive) {
            case AppUtil.ARCHIVED:
                /*menuItem = mMenu.findItem(R.id.actionDelete);
                menuItem.setTitle(R.string.msg_archive);*/
                break;
            case AppUtil.OS_UNARCHIVING:
                /*menuItem = mMenu.findItem(R.id.actionDelete);
                menuItem.setTitle(R.string.lbl_unArchiving);*/
                break;
        }


    }

    @Override
    public int getItemCount() {
        return mItens.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.mImageViewMenu.setOnClickListener(null);
        super.onViewRecycled(holder);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTxtValue;
        private final TextView mTxtBeneficiary;
        private final TextView mTxtDateMaturity;
        private final ImageView mImageViewMenu;

        public ViewHolder(View view) {
            super(view);
            mTxtValue = AppUtil.get(view.findViewById(R.id.textViewValue));
            mTxtBeneficiary = AppUtil.get(view.findViewById(R.id.textViewBeneficiary));
            mTxtDateMaturity = AppUtil.get(view.findViewById(R.id.textViewDateMaturity));
            mImageViewMenu = AppUtil.get(view.findViewById(R.id.imageViewMenu));
        }
    }
}

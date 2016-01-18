package br.com.rmsystems.chequescontrol.models.entites;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

import br.com.rmsystems.chequescontrol.models.repositories.ChequeRepository;

/**
 * Created by robson on 17/07/15.
 */
public class Cheque implements Parcelable {

    private String mCheque;
    private String mBank;
    private String mAgency;
    private String mCount;
    private Integer mPortion;
    private double mValue;
    private Date mEmission;
    private Date mMaturity;
    private String mBeneficiary;
    private boolean mPaid;

    public Cheque() {
        super();
    }

    public String getCheque() {
        return mCheque;
    }

    public void setCheque(String cheque) {
        this.mCheque = cheque;
    }

    public String getBank() {
        return mBank;
    }

    public void setBank(String bank) {
        this.mBank = bank;
    }

    public String getAgency() {
        return mAgency;
    }

    public void setAgency(String agency) {
        this.mAgency = agency;
    }

    public String getCount() {
        return mCount;
    }

    public void setCount(String count) {
        this.mCount = count;
    }

    public Integer getPortion() {
        return mPortion;
    }

    public void setPortion(Integer portion) {
        this.mPortion = portion;
    }

    public double getValue() {
        return mValue;
    }

    public void setValue(double value) {
        this.mValue = value;
    }

    public Date getEmission() {
        return mEmission;
    }

    public void setEmission(Date emission) {
        this.mEmission = emission;
    }

    public Date getMaturity() {
        return mMaturity;
    }

    public void setMaturity(Date maturity) {
        this.mMaturity = maturity;
    }

    public String getBeneficiary() {
        return mBeneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.mBeneficiary = beneficiary;
    }

    public boolean isPaid() {
        return mPaid;
    }

    public void setPaid(boolean paid) {
        this.mPaid = paid;
    }

    public static List<Cheque> getAll() {
        return ChequeRepository.getInstance().getAll();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cheque)) return false;

        Cheque cheque = (Cheque) o;

        if (Double.compare(cheque.mValue, mValue) != 0) return false;
        if (mPaid != cheque.mPaid) return false;
        if (!mCheque.equals(cheque.mCheque)) return false;
        if (!mBank.equals(cheque.mBank)) return false;
        if (!mAgency.equals(cheque.mAgency)) return false;
        if (!mCount.equals(cheque.mCount)) return false;
        if (!mPortion.equals(cheque.mPortion)) return false;
        if (!mEmission.equals(cheque.mEmission)) return false;
        if (!mMaturity.equals(cheque.mMaturity)) return false;
        return mBeneficiary.equals(cheque.mBeneficiary);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = mCheque.hashCode();
        result = 31 * result + mBank.hashCode();
        result = 31 * result + mAgency.hashCode();
        result = 31 * result + mCount.hashCode();
        result = 31 * result + mPortion.hashCode();
        temp = Double.doubleToLongBits(mValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + mEmission.hashCode();
        result = 31 * result + mMaturity.hashCode();
        result = 31 * result + mBeneficiary.hashCode();
        result = 31 * result + (mPaid ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Cheque{" +
                "mCheque='" + mCheque + '\'' +
                ", mBank='" + mBank + '\'' +
                ", mAgency='" + mAgency + '\'' +
                ", mCount='" + mCount + '\'' +
                ", mPortion=" + mPortion +
                ", mValue=" + mValue +
                ", mEmission=" + mEmission +
                ", mMaturity=" + mMaturity +
                ", mBeneficiary='" + mBeneficiary + '\'' +
                ", mPaid=" + mPaid +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mCheque);
        dest.writeString(this.mBank);
        dest.writeString(this.mAgency);
        dest.writeString(this.mCount);
        dest.writeValue(this.mPortion);
        dest.writeDouble(this.mValue);
        dest.writeLong(mEmission != null ? mEmission.getTime() : -1);
        dest.writeLong(mMaturity != null ? mMaturity.getTime() : -1);
        dest.writeString(this.mBeneficiary);
        dest.writeByte(mPaid ? (byte) 1 : (byte) 0);
    }

    protected Cheque(Parcel in) {
        this.mCheque = in.readString();
        this.mBank = in.readString();
        this.mAgency = in.readString();
        this.mCount = in.readString();
        this.mPortion = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mValue = in.readDouble();
        long tmpMEmission = in.readLong();
        this.mEmission = tmpMEmission == -1 ? null : new Date(tmpMEmission);
        long tmpMMaturity = in.readLong();
        this.mMaturity = tmpMMaturity == -1 ? null : new Date(tmpMMaturity);
        this.mBeneficiary = in.readString();
        this.mPaid = in.readByte() != 0;
    }

    public static final Creator<Cheque> CREATOR = new Creator<Cheque>() {
        public Cheque createFromParcel(Parcel source) {
            return new Cheque(source);
        }

        public Cheque[] newArray(int size) {
            return new Cheque[size];
        }
    };

}

package com.models;


        import android.os.Parcel;
        import android.os.Parcelable;

public class AadhaarCard implements Parcelable{
    public String uid;
    public String name;
    public String gender;
    public String yob;
    public String co;
    public String house;
    public String lm;
    public String loc;
    public String vtc;
    public String po;
    public String dist;
    public String subdist;
    public String state;
    public String pincode;
    public String dob;
    public String originalXML;
    public String lat = "0";
    public String lng = "0";

    public String TAG = "AADHAAR_CARD MODEL CLASS";

    public static final Parcelable.Creator<AadhaarCard> CREATOR = new Creator<AadhaarCard>() {
        public AadhaarCard createFromParcel(Parcel source) {
            AadhaarCard card = new AadhaarCard();
            card.uid = source.readString();
            card.name = source.readString();
            card.gender = source.readString();
            card.yob = source.readString();
            card.co = source.readString();
            card.house = source.readString();
            card.lm = source.readString();
            card.loc = source.readString();
            card.vtc = source.readString();
            card.po =source.readString();
            card.dist = source.readString();
            card.subdist = source.readString();
            card.state = source.readString();
            card.pincode = source.readString();
            card.dob = source.readString();
            card.originalXML = source.readString();
            card.lat = source.readString();
            card.lng = source.readString();
            return card;
        }
        public AadhaarCard[] newArray(int size) {
            return new AadhaarCard[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        // TODO Auto-generated method stub
        parcel.writeString(uid);
        parcel.writeString(name);
        parcel.writeString(gender);
        parcel.writeString(yob);
        parcel.writeString(co);
        parcel.writeString(house);
        parcel.writeString(lm);
        parcel.writeString(loc);
        parcel.writeString(vtc);
        parcel.writeString(po);
        parcel.writeString(dist);
        parcel.writeString(subdist);
        parcel.writeString(state);
        parcel.writeString(pincode);
        parcel.writeString(dob);
        parcel.writeString(originalXML);
        parcel.writeString(lat);
        parcel.writeString(lng);
    }
    public String getFormattedUID() {
        if (uid.length() == 12) {
            String newUIDString = uid.substring(0, 4) + " " + uid.substring(4, 8) + " " + uid.substring(8, 12);
            return newUIDString;
        }
        return uid;
    }
    public String getAddressValue(){
        String address =null ;
        if(pincode != null){
            address +=(pincode+"+") ;
        }
        if(state != null)
            address +=(state+"+");
        if(dist !=null)
            address +=(dist+"+");
        if(vtc !=null){
            address +=(vtc);
        }
        return address;

    }

    public String getAddress() {
        return house + ", " + lm + ", " + loc + ", " + dist + ", " + subdist + ", " + state + ".\nPincode: " + pincode;
    }
    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }
    public String toString(){
        return name+" lat : \'"+lat+"\' lng : \'"+lng+"\'";
    }
}

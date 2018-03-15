package com.example.fready.ohbaedae;

/**
 * Created by fready on 2018-02-07.
 */

public class GoodInfoVO {
    String shippingCenter;  //물류센터
    String goodPrice ;//상품가격
    String tax ;//세금
    String localShipCharge ;//현지배송비
    String goodWidth ; //가로
    String  goodHeight ; //높이
    String  goodVertical ;//세로
    String  goodWeight ; //무게

    public GoodInfoVO(String shippingCenter, String goodPrice, String tax, String localShipCharge, String goodWidth, String goodHeight, String goodVertical, String goodWeight) {
        this.shippingCenter = shippingCenter;
        this.goodPrice = goodPrice;
        this.tax = tax;
        this.localShipCharge = localShipCharge;
        this.goodWidth = goodWidth;
        this.goodHeight = goodHeight;
        this.goodVertical = goodVertical;
        this.goodWeight = goodWeight;
    }

    public String getGoodPrice() {

        return goodPrice;
    }

    public void setGoodPrice(String goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getLocalShipCharge() {
        return localShipCharge;
    }

    public void setLocalShipCharge(String localShipCharge) {
        this.localShipCharge = localShipCharge;
    }

    public String getGoodWidth() {
        return goodWidth;
    }

    public void setGoodWidth(String goodWidth) {
        this.goodWidth = goodWidth;
    }

    public String getGoodHeight() {
        return goodHeight;
    }

    public void setGoodHeight(String goodHeight) {
        this.goodHeight = goodHeight;
    }

    public String getGoodVertical() {
        return goodVertical;
    }

    public void setGoodVertical(String goodVertical) {
        this.goodVertical = goodVertical;
    }

    public String getGoodWeight() {
        return goodWeight;
    }

    public void setGoodWeight(String goodWeight) {
        this.goodWeight = goodWeight;
    }

    public String getShippingCenter() {
        return shippingCenter;
    }

    public void setShippingCenter(String shippingCenter) {
        this.shippingCenter = shippingCenter;
    }
}

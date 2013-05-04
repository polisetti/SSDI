package com.test.view.backing;

import java.io.Serializable;

import javax.faces.event.ActionEvent;

public class HomeBean implements Serializable{
    public HomeBean() {
        super();
    }
    private boolean rederTab1;
    private boolean renderPrediction;
    private boolean renderCmpareStocksTab;


    public void makeAllFalse() {
        setRenderCmpareStocksTab(false);
        setRederTab1(false);
        setRenderPrediction(false);        
    }

    public void setTab1(ActionEvent actionEvent) {
       makeAllFalse();
       setRederTab1(true);
    }

    public void setPredictionTab(ActionEvent actionEvent) {
        makeAllFalse();
        setRenderPrediction(true);
    }
    public void setCompareStocksTab(ActionEvent actionEvent) {
        makeAllFalse();
        setRenderCmpareStocksTab(true);
    }

    public void setRenderCmpareStocksTab(boolean renderCmpareStocksTab) {
        this.renderCmpareStocksTab = renderCmpareStocksTab;
    }

    public boolean getRenderCmpareStocksTab() {
        return renderCmpareStocksTab;
    }
    public void setRederTab1(boolean rederTab1) {
        this.rederTab1 = rederTab1;
    }

    public boolean getRederTab1() {
        return rederTab1;
    }
    public void setRenderPrediction(boolean renderCompareStocks) {
        this.renderPrediction = renderCompareStocks;
    }

    public boolean getRenderPrediction() {
        return renderPrediction;
    }
}

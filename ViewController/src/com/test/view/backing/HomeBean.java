package com.test.view.backing;

import java.io.Serializable;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.domain.Number;

public class HomeBean implements Serializable{
    private boolean rederTab1;
    private boolean renderPrediction;
    private boolean renderCmpareStocksTab;
    private boolean renderPortfolioTab;
    private boolean renderCreatePortfolioTab;
    public String portfolioSelected;
    public Number portfolioID;

    public HomeBean() {
        super();
    }
    public void makeAllFalse() {
        setRenderCmpareStocksTab(false);
        setRederTab1(false);
        setRenderPrediction(false);  
        setRenderPortfolioTab(false);
        setRenderCreatePortfolioTab(false);
    }
    
    public void displayCreatePortfolioTab(ActionEvent actionEvent) {
        // Add event code here...
        makeAllFalse();
        setRenderCreatePortfolioTab(true);
    }
    public void displayOnePortfolio(ActionEvent actionEvent) {
        makeAllFalse();
        //System.out.println(portfolioID);
        showPortfolio();
        setRenderPortfolioTab(true);
    }
    public void showPortfolio() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{bindings}",
                                            Object.class);
        BindingContainer binding =
            (BindingContainer)valueExp.getValue(elContext);
        OperationBinding operationBinding =
            binding.getOperationBinding("showPortfolio");

        // Set the Input parameters to the operation bindings as below
        //operationBinding.getParamsMap().put("portfolioID", pfID);

        // Invoke the Application module method
        operationBinding.execute();

        //return (Number)operationBinding.getResult();
    }
    public Number getPortfolioID(String portfolioName) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{bindings}",
                                            Object.class);
        BindingContainer binding =
            (BindingContainer)valueExp.getValue(elContext);
        OperationBinding operationBinding =
            binding.getOperationBinding("getPortfolioID");

        // Set the Input parameters to the operation bindings as below
        operationBinding.getParamsMap().put("portfolioName", portfolioName);

        // Invoke the Application module method
        operationBinding.execute();

        return (Number)operationBinding.getResult();
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

    public void setPortfolioSelected(String portfolioSelected) {
        this.portfolioSelected = portfolioSelected;
    }

    public String getPortfolioSelected() {
        return portfolioSelected;
    }

    public void setPortfolioID(Number portfolioID) {
        this.portfolioID = portfolioID;
    }

    public Number getPortfolioID() {
        return portfolioID;
    }


    public void setRenderPortfolioTab(boolean renderPortfolioTab) {
        this.renderPortfolioTab = renderPortfolioTab;
    }

    public boolean getRenderPortfolioTab() {
        return renderPortfolioTab;
    }

    public void setRenderCreatePortfolioTab(boolean renderCreatePortfolioTab) {
        this.renderCreatePortfolioTab = renderCreatePortfolioTab;
    }

    public boolean getRenderCreatePortfolioTab() {
        return renderCreatePortfolioTab;
    }


}

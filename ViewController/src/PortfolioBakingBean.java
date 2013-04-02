
import java.util.ArrayList;
import java.util.List;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.model.AutoSuggestUIHints;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import java.util.ListIterator;

import oracle.jbo.domain.Number;

public class PortfolioBakingBean {
    public PortfolioBakingBean() {
    }
    public String stockName;

    public void compareStocks(ActionEvent actionEvent) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
          Application app = facesContext.getApplication();
          ExpressionFactory elFactory = app.getExpressionFactory();
          ELContext elContext = facesContext.getELContext();
          ValueExpression valueExp =
            elFactory.createValueExpression(elContext, "#{bindings}", Object.class);
          BindingContainer binding = (BindingContainer)valueExp.getValue(elContext);
          OperationBinding operationBinding = binding.getOperationBinding("compareStocks");
          
          operationBinding.getParamsMap().put("stockID1", "100");
          operationBinding.getParamsMap().put("stockID2", "200");

          operationBinding.execute();
    }

    public void deleteStocksFromPortfolio(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void deletePortfolio(ActionEvent actionEvent) {
        // Add event code here...
    }

    public List<SelectItem> auto(FacesContext facesContext,
                     AutoSuggestUIHints autoSuggestUIHints) {
        String subValue = autoSuggestUIHints.getSubmittedValue();
        subValue = subValue.toLowerCase();
        
        facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp =
          elFactory.createValueExpression(elContext, "#{bindings}", Object.class);
        BindingContainer binding= (BindingContainer)valueExp.getValue(elContext);
        OperationBinding operationBinding=binding.getOperationBinding("autoComplete");
        // Set the Input parameters to the operation bindings as below
           operationBinding.getParamsMap().put("inputValue", subValue);  
        // Invoke the Application module method
        operationBinding.execute();
        // Get the result from operation bindings
        List<String> obj = (List<String>)operationBinding.getResult();
        ListIterator<String> litr = obj.listIterator();
        List <SelectItem> cont = new ArrayList<SelectItem>();

        while (litr.hasNext()) {
          String element = litr.next();
          cont.add(new SelectItem(element));
        }
       return cont;
    }

    public void addStocksToPortfolio(ActionEvent actionEvent) {
        // Add event code here...
        /*
         *  Get the stockID from a given stockname
         *  calles the displayGraph method in the AM
         */
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp =
          elFactory.createValueExpression(elContext, "#{bindings}", Object.class);
        BindingContainer binding= (BindingContainer)valueExp.getValue(elContext);

        //get the Operation Binding
        OperationBinding operationBinding = binding.getOperationBinding("getStockID");
        
        //set the parameter
        operationBinding.getParamsMap().put("pStockName", stockName);
        
        //execute and get the StockID
        operationBinding.execute();
        Number stockID = (Number)operationBinding.getResult();
                
        operationBinding = binding.getOperationBinding("addStocksToPortfolio");
        operationBinding.getParamsMap().put("stockID", stockID);
        operationBinding.execute();
        
        return;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockName() {
        return stockName;
    }
}

import java.util.ArrayList;
import java.util.List;

import java.util.ListIterator;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.model.AutoSuggestUIHints;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

public class SearchStockPriceBean {
    private RichInputText inputTextMethod;

    public SearchStockPriceBean() {
    }
    public String stockName;

    public List autoComplete(FacesContext facesContext,
                             AutoSuggestUIHints autoSuggestUIHints) {
        // This is the backing bean autocomplete
        // AMImpl autocomplete method is invoked here
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

    public void getStockID() {
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
        OperationBinding operationBinding = binding.getOperationBinding("displayGraph");
        
        //set the parameter
        operationBinding.getParamsMap().put("pStockName", stockName);
        
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

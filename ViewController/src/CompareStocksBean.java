import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.ListIterator;

import java.util.Random;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.model.AutoSuggestUIHints;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;

import org.apache.myfaces.trinidad.model.RowKeySet;

public class CompareStocksBean {
    private List<String> listOfStocks;
    public String stockSelected;
    public CompareStocksBean() {
        if(listOfStocks == null || listOfStocks.size() == 0) {
            listOfStocks = new ArrayList<String>();
        }
    }
    
    public List autoComplete(FacesContext facesContext,
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
    
    public void compareStocks(ActionEvent actionEvent) {
        listOfStocks.add(getStockSelected());
        printStocks();
    }
    
    public void printStocks() {
        System.out.println("In the print function");
        for (String str: listOfStocks) {
            System.out.println(str);
        }
    }
    public List<Object[]> getTabularData() {
       List<Object[]> tabularData = new ArrayList<Object []>();
         
       //time, stock id, price  
       int time = 900;
       Random ran = new Random();
       List <String> totalStocks = new ArrayList <String>();
       
       //Add the selected stocks to the line graph
       for (String s: listOfStocks) {
           totalStocks.add(s);    
       }

       while (time < 1500) {
           for (String loc: totalStocks) {
               tabularData.add(new Object[]{time, loc, Double.valueOf(ran.nextInt(120) + 25)});    
           }
           time += 100;
         }
        //displayLineGraph
        return tabularData;
     }
    public void setStockSelected(String stockSelected) {
        this.stockSelected = stockSelected;
    }

    public String getStockSelected() {
        return stockSelected;
    }
}

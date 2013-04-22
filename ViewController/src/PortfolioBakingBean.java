
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.faces.bi.component.graph.UIGraph;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.nav.RichCommandToolbarButton;
import oracle.adf.view.rich.model.AutoSuggestUIHints;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import java.util.ListIterator;

import java.util.Random;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.domain.Number;

import javax.faces.context.ExternalContext;
import org.apache.myfaces.trinidad.model.RowKeySet;

public class PortfolioBakingBean {
    private RichTable pfTable;
    public String stockName;
    public List <String> stocksSelected ;
    private Boolean showLineGraph;
    private UIGraph displayLineGraph;
    private RichCommandToolbarButton compareStocksButton;

    public PortfolioBakingBean() {
        showLineGraph = Boolean.FALSE;
        stocksSelected = new ArrayList<String>();
    }


    public void compareStocks(ActionEvent actionEvent) {
        //Get the binding
        RowKeySet selectedEmps = getPfTable().getSelectedRowKeys();    
        Iterator selectedEmpIter = selectedEmps.iterator();
        DCBindingContainer bindings =
                          (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        
        //get the binding iterator
        DCIteratorBinding empIter = bindings.findIteratorBinding("PortfolioStocksVOIterator");
        RowSetIterator empRSIter = empIter.getRowSetIterator();
        stocksSelected = new ArrayList<String>();
        //iterate through all the selected rows in the table
        //TODO Bring the while loop code outside
        while(selectedEmpIter.hasNext()) {
           Key key = (Key)((List)selectedEmpIter.next()).get(0);
           Row currentRow = empRSIter.getRow(key);
           Number sid = (Number)currentRow.getAttribute("Stockid");

           FacesContext facesContext = FacesContext.getCurrentInstance();
           Application app = facesContext.getApplication();
           ExpressionFactory elFactory = app.getExpressionFactory();
           ELContext elContext = facesContext.getELContext();
           ValueExpression valueExp =
           elFactory.createValueExpression(elContext, "#{bindings}", Object.class);
           BindingContainer binding= (BindingContainer)valueExp.getValue(elContext);
           OperationBinding operationBinding=binding.getOperationBinding("getStockName");

           // Set the Input parameters to the operation bindings as below
           operationBinding.getParamsMap().put("pStockID", sid);  

           // Invoke the Application module method
           operationBinding.execute();

           // Get the result from operation bindings
           String stockName = (String)operationBinding.getResult();

           // Add the stockName returned to the global variable
           stocksSelected.add(stockName);
           System.out.println(stockName);
        }
        setShowLineGraph(Boolean.TRUE);
        //this.getTabularData();
        //return stocksSelected;
    }
    public List<Object[]> getTabularData() {
       List<Object[]> tabularData = new ArrayList<Object []>();
         
       //time, stock id, price  
       int time = 900;
       Random ran = new Random();
       List <String> Locations = new ArrayList <String>();
       
       //Add the selected stocks to the line graph
       for (String s: stocksSelected) {
           Locations.add(s);    
       }

       while (time < 1500) {
           for (String loc: Locations) {
               tabularData.add(new Object[]{time, loc, Double.valueOf(ran.nextInt(40) + 25)});    
           }
           time += 100;
         }
        //displayLineGraph
        return tabularData;
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

    public void setPfTable(RichTable pfTable) {
        this.pfTable = pfTable;
    }

    public RichTable getPfTable() {
        return pfTable;
    }

    public void setShowLineGraph(Boolean showLineGraph) {
        this.showLineGraph = showLineGraph;
    }

    public Boolean isShowLineGraph() {
        return showLineGraph;
    }

    public void setDisplayLineGraph(UIGraph displayLineGraph) {
        this.displayLineGraph = displayLineGraph;
    }

    public UIGraph  getDisplayLineGraph() {
        return displayLineGraph;
    }

    public void setCompareStocksButton(RichCommandToolbarButton compareStocksButton) {
        this.compareStocksButton = compareStocksButton;
    }

    public RichCommandToolbarButton getCompareStocksButton() {
        return compareStocksButton;
    }

}

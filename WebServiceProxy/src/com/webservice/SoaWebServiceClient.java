package com.webservice;

import com.webservice.ssdi.SOA_SSDI_BPEL;
import com.webservice.ssdi.SOA_SSDI_Service_ep;

import com.webservice.ssdi.types.ServiceInput;
import com.webservice.ssdi.types.ServiceOutput;

import java.math.BigDecimal;

import javax.xml.ws.WebServiceRef;

import org.python.modules.math;

public class SoaWebServiceClient {
    @WebServiceRef
    private static SOA_SSDI_Service_ep sOA_SSDI_Service_ep;
    public ServiceOutput invokeWebService(BigDecimal stockID, Integer startDate, Integer endDate) {
        sOA_SSDI_Service_ep = new SOA_SSDI_Service_ep();
        SOA_SSDI_BPEL sOA_SSDI_BPEL = sOA_SSDI_Service_ep.getSOA_SSDI_BPEL_pt();
        
        ServiceInput input = new ServiceInput();
        ServiceOutput output = new ServiceOutput();
        
        //BigDecimal stockID, Integer startDate, Integer endDate
        input.setStockID(stockID);
        input.setStartDate(startDate);
        input.setEndDate(endDate);
        
        output = sOA_SSDI_BPEL.process(input);
        
        return output;
    }
}

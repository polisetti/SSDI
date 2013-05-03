package com.proxy;

import com.proxy.ssdi.SOA_SSDI_BPEL;
import com.proxy.ssdi.SOA_SSDI_Service_ep;
import com.proxy.ssdi.types.ServiceInput;
import com.proxy.ssdi.types.ServiceOutput;
import oracle.jbo.domain.Number;

import java.math.BigDecimal;

public class SoaServiceClient {
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
    public static void main(String [] args) {
        Number numb = new Number(4);
        SoaServiceClient cl = new SoaServiceClient();
        ServiceOutput sout = cl.invokeWebService(numb.bigDecimalValue(), 20130415, 20130501);
        System.out.println(sout.getOutputXBar());
        System.out.println(sout.getOutputYBar());
        System.out.println(sout.getOutputBeta0());
        System.out.println(sout.getOutputBeta1());    }
}

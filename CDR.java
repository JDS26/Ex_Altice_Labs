import java.time.*;
import java.util.*;

public class CDR{
    
    private LocalDateTime TimeStamp;
    private String MSISDN;
    private String Service;
    private ChargingRequest C_Request;
    private ChargingReply C_Reply;
    private HashMap <String,Double> VBuckets;
    private HashMap <String,Integer> VCounters;
    private String T_ID;
    /*   
    - TimeStamp (do request)
    - MSISDN (Phone number)
    - Serviço (ID)
    - Charging Request/ Charging Reply
    - Buckets/Valores
    - Counters/Valores
    - Tarifário aplicado (apenas o ID)
    */
    
    //Constructors
    public CDR(){
        TimeStamp = LocalDateTime.now();
        MSISDN = "";
        Service= "";
        C_Request = null;
        C_Reply = null;
        VBuckets = new HashMap <String,Double>();
        VCounters = new HashMap <String,Integer>();
        T_ID = "";
    }
    
    public CDR(LocalDateTime timestamp,String msisdn,String service,ChargingRequest c_request,ChargingReply c_reply,HashMap <String,Double> vBuckets,HashMap <String,Integer> vCounters,String t_id){
        TimeStamp = timestamp;
        MSISDN = msisdn;
        Service= service;
        C_Request = c_request;
        C_Reply = c_reply;
        VBuckets = vBuckets;
        VCounters = vCounters;
        T_ID = t_id;
    }
    
    public CDR(CDR cdr){
        TimeStamp = cdr.getTimeStamp();
        MSISDN = cdr.getMSISDN();
        Service= cdr.getService();
        C_Request = cdr.getCRequest();
        C_Reply = cdr.getCReply();
        VBuckets = cdr.getVBuckets();
        VCounters = cdr.getVCounters();
        T_ID = cdr.getT_ID();
    }
    
    // get functions
    public LocalDateTime getTimeStamp(){
        return this.TimeStamp;
    }
    
    public String getMSISDN(){
        return this.MSISDN;
    }
    
    public String getService(){
        return this.Service;
    }
    
    public ChargingRequest getCRequest(){
        return this.C_Request;
    }
    
    public ChargingReply getCReply(){
        return this.C_Reply;
    }
    
    public HashMap<String,Double> getVBuckets(){
        HashMap<String,Double> newVBuckets= new HashMap<>();
        for (Map.Entry<String, Double> VB: this.VBuckets.entrySet())
            newVBuckets.put(VB.getKey(), VB.getValue()); //VB.getValue().clone());
        
        return newVBuckets;
    }
    
    public HashMap<String,Integer>  getVCounters(){
        HashMap<String,Integer> newVCounters= new HashMap<>();
        for (Map.Entry<String, Integer> VC: this.VCounters.entrySet())
            newVCounters.put(VC.getKey(), VC.getValue()); //VC.getValue().clone());
            
        return newVCounters;
    }
    
    public String getT_ID(){
        return this.T_ID;
    }
    
    // set functions
    public void setTimeStamp(LocalDateTime DT){
        this.TimeStamp = DT;
    }
    
    public void setMSISDN(String msisdn){
        this.MSISDN = msisdn;
    }
    
    public void setService(String S){
        this.Service = S;
    }
    
    public void setCRequest(ChargingRequest c_request){
        this.C_Request=c_request;
    }
    
    public void setCReply(ChargingReply c_reply){
        this.C_Reply= c_reply;
    }
    
    public void setVBuckets (HashMap<String, Double> newVBuckets){
        this.VBuckets = new HashMap<>();
        
        newVBuckets.entrySet().forEach(VB -> this.VBuckets.put(VB.getKey(), VB.getValue())); //VB.getValue().clone()));
    }
    
    public void setVCounters (HashMap<String,Integer> newVCounters){
        this.VCounters = new HashMap<>();
        
        newVCounters.entrySet().forEach(VC -> this.VCounters.put(VC.getKey(), VC.getValue())); //VC.getValue().clone()));
    }
    
    public void setT_ID (String t_id){
        this.T_ID = t_id;
    }
    
    // Clone, Equals, ToSTR
    public CDR clone() {
        return new CDR(this);
    }
    
    public boolean equals(Object obj) {
        if (obj == this)
          return true;
        if (obj == null || obj.getClass() != this.getClass())
          return false;
    
        CDR cr = (CDR) obj;
    
        return cr.getMSISDN().equals(this.MSISDN);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
    
        sb.append("ID: ").append(this.TimeStamp).append("\n");
        sb.append("MSISDN (Phone Number): ").append(this.MSISDN).append("\n");
        sb.append("Service (ID): ").append(this.Service).append("\n");
        sb.append("Charging Request: ").append(this.C_Request).append("\n");
        sb.append("Charging Reply: ").append(this.C_Reply).append("\n");
        sb.append("Buckets/Value: ").append(this.VBuckets).append("\n");
        sb.append("Counters/Value: ").append(this.VCounters).append("\n");
        sb.append("Tarifário aplicado (ID only): ").append(this.T_ID).append("\n");
        
        return sb.toString();
    }
    
    // Other implemented funtions
    
    /*public DayOfWeek getDia(LocalDateTime Date){
        return Date.getDayOfWeek();
    }*/
    
}

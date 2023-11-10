import java.util.*;
import java.time.*;

public class BillingAccount{
    
    private String MSISDN;
    private double Bucket1;
    private double Bucket2;
    private double Bucket3;
    private int CounterA;
    private int CounterB;
    private int CounterC;
    private LocalDateTime CounterD;
    private String TServiceA; 
    private String TServiceB;
    private HashMap <String,CDR> CDRs= new HashMap<String,CDR>();
    /*
    o MSISDN (Phone number)
    o bucket1(centimos)
    o bucket2 (centimos)
    o bucket3 (centimos)
    o counterA - contagem de SU's do Serviço A;
    o counterB - contagem do numero de pedidos do Serviço B, sob tarifário Beta1;
    o counterC - contagem de requisições em roaming
    o counterD - registro da data da ultima requisição feita
    o TarifárioServiçoA (Alfa1; Alfa2; Alfa3);
    o TarifárioServiçoB (Beta1; Beta2; Beta3);
    */
    
    // Constructors
    public BillingAccount(){
        MSISDN = "";
        Bucket1 = 0.0;
        Bucket2 = 0.0;
        Bucket3 = 0.0;
        CounterA = 0;
        CounterB = 0;
        CounterC = 0;
        CounterD = LocalDateTime.now();
        TServiceA= "";
        TServiceB= "";
        CDRs= new HashMap<String,CDR>();
    }
    
    public BillingAccount(String msisdn,double bucket1,double bucket2,double bucket3, int counterA, int counterB, int counterC, LocalDateTime counterD, String tserviceA,String tserviceB, HashMap<String,CDR> cdrs){
        MSISDN = msisdn;
        Bucket1 = bucket1;
        Bucket2 = bucket2;
        Bucket3 = bucket3;
        CounterA = counterA;
        CounterB = counterB;
        CounterC = counterC;
        CounterD = counterD;
        TServiceA= tserviceA;
        TServiceB= tserviceB;
        CDRs= cdrs;
    }
    
    public BillingAccount(BillingAccount BA){
        MSISDN = getMSISDN();
        Bucket1 = getBucket1();
        Bucket2 = getBucket2();
        Bucket3 = getBucket3();
        CounterA = getCounterA();
        CounterB = getCounterB();
        CounterC = getCounterC();
        CounterD = getCounterD();
        TServiceA= getTServiceA();
        TServiceB= getTServiceB();
        CDRs= getCDRs();
    }
    
    // get funtions
    public String getMSISDN(){
        return this.MSISDN;
    }
    
    public double getBucket1(){
        return this.Bucket1;
    }
    
    public double getBucket2(){
        return this.Bucket2;
    }
    
    public double getBucket3(){
        return this.Bucket3;
    }
    
    public int getCounterA(){
        return this.CounterA;
    }
    
    public int getCounterB(){
        return this.CounterB;
    }
    
    public int getCounterC(){
        return this.CounterC;
    }

    public LocalDateTime getCounterD(){
        return this.CounterD;
    }
    
    public String getTServiceA(){
        return this.TServiceA;
    }
    
    public String getTServiceB(){
        return this.TServiceB;
    }
    
    public HashMap<String,CDR> getCDRs(){
        HashMap<String,CDR> newCDRs= new HashMap<>();
        for (Map.Entry<String, CDR> cdr: this.CDRs.entrySet())
            newCDRs.put(cdr.getKey(), cdr.getValue().clone());
            
        return newCDRs;
    }
    
    // set funtions
    public void setMSISDN(String msisdn){
        this.MSISDN = msisdn;
    }
    
    public void setBucket1(double B1){
        this.Bucket1=B1;
    }
    
    public void setBucket2(double B2){
        this.Bucket2=B2;
    }
    
    public void setBucket3(double B3){
        this.Bucket3=B3;
    }
    
    public void setCounterA(int CA){
        this.CounterA = CA;
    }
    
    public void setCounterB(int CB){
        this.CounterB = CB;
    }
    
    public void setCounterC(int CC){
        this.CounterC = CC;
    }

    public void setCounterD(LocalDateTime CD){
        this.CounterD = CD;
    }
    
    public void setTServiceA(String TSA){
        this.TServiceA = TSA;
    }
    
    public void setTServiceB(String TSB){
        this.TServiceB = TSB;
    }
    
    public void setCDRs(HashMap <String,CDR> newCDRs){
        this.CDRs = new HashMap<>();
        
        newCDRs.entrySet().forEach(cdr -> this.CDRs.put(cdr.getKey(), cdr.getValue().clone()));
    }
    
    // Clone, Equals, ToSTR
    
    public BillingAccount clone() {
        return new BillingAccount(this);
    }
    
    public boolean equals(Object obj) {
        if (obj == this)
          return true;
        if (obj == null || obj.getClass() != this.getClass())
          return false;
    
        BillingAccount ba = (BillingAccount) obj;
    
        return ba.getMSISDN().equals(this.MSISDN);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("MSISDN (Phone Number): ").append(this.MSISDN).append("\n");
        sb.append("Bucket1: ").append(this.Bucket1).append("\n");
        sb.append("Bucket2: ").append(this.Bucket2).append("\n");
        sb.append("Bucket3: ").append(this.Bucket3).append("\n");
        sb.append("CounterA: ").append(this.CounterA).append("\n");
        sb.append("CounterB: ").append(this.CounterB).append("\n");
        sb.append("CounterC: ").append(this.CounterC).append("\n");
        sb.append("CounterD: ").append(this.CounterD).append("\n");
        sb.append("TServiceA: ").append(this.TServiceA).append("\n");
        sb.append("TServiceB: ").append(this.TServiceB).append("\n");
        sb.append("CDRs (Cliente Data Record):\n").append(this.CDRs).append("\n");
        
        return sb.toString();
    }
    
    // METHODS
    
    // método para um cliente criar um Charging request no exato momento em que é submetido
    public ChargingReply Request_Client(BillingAccount Client, String service, boolean roaming,int rsu){
        ChargingRequest CR = new ChargingRequest(service,roaming, Client.getMSISDN(),rsu);
        
        return CR.Request(Client,service,roaming,rsu);
    }
    
    // método para um cliente criar um Charging request numa data especifica
    public ChargingReply Request_Client_Timed(BillingAccount Client, String service,LocalDateTime date, boolean roaming,int rsu){
        ChargingRequest CR = new ChargingRequest(service,date,roaming, Client.getMSISDN(),rsu);
        
        return CR.Request_Timed(Client,service,date,roaming,rsu);
    }
    
    //funcoes que criam um hashMap dos buckets e Counters de um cliente, respetivamente
    public HashMap createVBuckets(BillingAccount Client){
        HashMap <String,Double> VB= new HashMap<>();
        VB.put("Bucket1",Client.getBucket1());
        VB.put("Bucket2",Client.getBucket2());
        VB.put("Bucket3",Client.getBucket3());
        
        return VB;
    }
    
    public HashMap createVCounters(BillingAccount Client){
        HashMap <String,Integer> VC= new HashMap<>();
        VC.put("CounterA",Client.getCounterA());
        VC.put("CounterB",Client.getCounterB());
        VC.put("CounterC",Client.getCounterC());
        
        return VC;
    }
    
    //Funcao que cria o CDR de um cliente através do charging request, charging reply e as informacoes do cliente
    public CDR CreateCDR (ChargingReply C_Rep,ChargingRequest CR,BillingAccount Client){
        String ID= CR.getID();
        HashMap <String,Double> VB= createVBuckets(Client);
        HashMap <String,Integer> VC= createVCounters(Client);
        String TA_ID=C_Rep.getID();
        
        return new CDR(CR.getTimeStamp(), Client.getMSISDN(), ID, CR, C_Rep, VB, VC, TA_ID);
    }
    
    // Funcao que adiciona o CDR ao hashmap de CDR's do cliente
    public void List_CDR (ChargingReply C_Rep,ChargingRequest CR,BillingAccount Client){
        HashMap <String,CDR> CDRs= Client.getCDRs();
        String ID= CR.getID();
        if (CDRs.get(ID)== null){
            //criar novo CDR
            CDR cdr = CreateCDR(C_Rep, CR, Client);
            CDRs.put(ID,cdr);
        }
        
        Client.setCDRs(CDRs);
    }
    
    
    public void incCounterA(){
        int count= this.getCounterA()+1;
        this.setCounterA(count);
    }
    
    public void incCounterB(){
        int count= this.getCounterB()+1;
        this.setCounterB(count);
    }
    
    public void incCounterC(){
        int count= this.getCounterC()+1;
        this.setCounterC(count);
    }
}

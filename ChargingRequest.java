import java.time.*;

public class ChargingRequest extends Charging{
    
    private LocalDateTime TimeStamp;
    private String Service;
    private boolean Roaming;
    private String MSISDN;
    private int RSU;
    private boolean Flag = false; // flag que indica se o pagamento de uma SU fica negativo
    
    /* 
    request ID (na superclasse Charging)
    TimeStamp
    Serviço (A; B)
    Roaming (boolean)
    MSISDN (Phone number)
    RSU (Requested Service Units)
    */
       
    //constructors
    public ChargingRequest(){
        super();
        Service= "";
        TimeStamp = LocalDateTime.now();
        Roaming = false;
        MSISDN = "";
        RSU = 0;
    }
    
    public ChargingRequest(String service,boolean roaming,String msisdn,int rsu){ //Como é criado um ChargingRequest aquando do pedido o TimeStamp é o do momento
        super();
        TimeStamp = LocalDateTime.now();
        Service= service;
        Roaming = roaming;
        MSISDN = msisdn;
        RSU = rsu;
    }
    
    public ChargingRequest(String id,String service,boolean roaming,String msisdn,int rsu){ //Como é criado um ChargingRequest aquando do pedido o TimeStamp é o do momento
        super(id);
        TimeStamp = LocalDateTime.now();
        Service= service;
        Roaming = roaming;
        MSISDN = msisdn;
        RSU = rsu;
    }
    
    public ChargingRequest(String service,LocalDateTime Time,boolean roaming,String msisdn,int rsu){ //Cria-se um charging request para uma hora especifica
        super();
        TimeStamp = Time;
        Service= service;
        Roaming = roaming;
        MSISDN = msisdn;
        RSU = rsu;
    }
    
    public ChargingRequest(ChargingRequest CR){
        super(CR.getID());
        TimeStamp = CR.getTimeStamp();
        Service= CR.getService();
        Roaming = CR.getRoaming();
        MSISDN = CR.getMSISDN();
        RSU = CR.getRSU();
        
    }
    
    // get functions
    
    public LocalDateTime getTimeStamp(){
        return this.TimeStamp;
    }
    
    public String getService(){
        return this.Service;
    }
    
    public boolean getRoaming(){
        return this.Roaming;
    }
    
    public String getMSISDN(){
        return this.MSISDN;
    }
    
    public int getRSU(){
        return this.RSU;
    }
    
    public boolean getFlag(){
        return this.Flag;
    }
    
    // set functions
    
    public void setTimeStamp(LocalDateTime DT){
        this.TimeStamp = DT;
    }
    
    public void setService(String service){
        this.Service = service;
    }
    
    public void setRoaming(boolean roaming){
        this.Roaming = roaming;
    }
    
    public void setMSISDN(String msisdn){
        this.MSISDN = msisdn;
    }
    
    public void setRSU(int rsu){
        this.RSU = rsu;
    }
    
    public void setFlag(boolean flag){
        this.Flag = flag;
    }
    
    // Clone, Equals, ToSTR
    public ChargingRequest clone() {
        return new ChargingRequest(this);
    }
    
    public boolean equals(Object obj) {
        if (obj == this)
          return true;
        if (obj == null || obj.getClass() != this.getClass())
          return false;
    
        ChargingRequest cr = (ChargingRequest) obj;
    
        return cr.getID() == super.getID();
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
    
        sb.append("ID: ").append(super.getID()).append("\n");
        sb.append("Date: ").append(this.TimeStamp).append("\n");
        sb.append("Servico: ").append(this.Service).append("\n");
        sb.append("Roaming: ").append(this.Roaming).append("\n");
        sb.append("MSISDN (Phone Number): ").append(this.MSISDN).append("\n");
        sb.append("RSU (Requested Service Units): ").append(this.RSU).append("\n");
        
        return sb.toString();
    }
    
    // Other Functions
    
    //funcao que verifica se o data registada é um fim de semana
    public boolean isWeekend(LocalDateTime Date){
        int day = Date.getDayOfWeek().getValue();
        
        if (day == 6 || day == 7) return true;
        
        return false;
    }
    
    //funcao que verifica se o data registada é de noite
    public boolean isNight(LocalDateTime Date){
        int hour = Date.getHour();
        
        if (hour >= 20 && hour <= 6) return true;
        
        return false;
    }
    
    // Função que verifica o tipo de tarifario do serviço A, retorna 0 se nenhum for elegivel
    public int VerifyA(BillingAccount Client, ChargingRequest CR){

        // Verificar Alpha1 
        LocalDateTime date=CR.getTimeStamp();
        int CA= Client.getCounterA();
        if (CA<100 && !isWeekend(date)){ // se CounterA<100 e for dia da semana sera atribuido o tarifario alpha1
            return 1;
        }
        
        // Verificar Alpha2
        double B2 = Client.getBucket2();
        if(!(CR.getRoaming()) && B2>100){ // se B2>100 e for chamada local sera atribuido o tarifario alpha2
            return 2;
        }
        
        // Verificar Alpha3
        double B3 = Client.getBucket3();
        if(CR.getRoaming() && B3>10){ // se B3>10 e for chamada internacional sera atribuido o tarifario alpha3
            return 3;
        }
        // return 0 se nenhum for elegivel
        return 0;
    }
     
    // Função que verifica o tipo de tarifario do serviço B, retorna 0 se nenhum for elegivel 
    public int VerifyB(BillingAccount Client, ChargingRequest CR){
         // Verificar Beta1 
        LocalDateTime date=CR.getTimeStamp();
        if (!isWeekend(date) || isNight(date)){ // se for um dia de semana ou for de noite (num dia qualquer) sera atribuido o tarifario beta1
            return 1;
        }
        
        // Verificar Beta2
        double B2 = Client.getBucket2();
        if(!(CR.getRoaming()) && B2>10){ // se B2>10 e for chamada internacional sera atribuido o tarifario beta2
            return 2;
        }
        
        // Verificar Beta3
        double B3 = Client.getBucket3();
        if(CR.getRoaming() && B3>10){ // se B3>10 e for chamada internacional sera atribuido o tarifario beta3
            return 3;
        }
        // return 0 se nenhum for elegivel
        return 0;
    }
    
    // funcoes que indicam qual o tarifario que vai ser usado para cada servico 
    public void wichTarifA(BillingAccount Client,int tarifario){
        if (tarifario== 1) Client.setTServiceA("Alpha1");
        else if (tarifario== 2) Client.setTServiceA("Alpha2");
        else if (tarifario== 3) Client.setTServiceA("Alpha3");
        Client.setTServiceB("");
    }
    
    public void wichTarifB(BillingAccount Client,int tarifario){
        if (tarifario== 1) Client.setTServiceB("Beta1");
        else if (tarifario== 2) Client.setTServiceB("Beta2");
        else if (tarifario== 3) Client.setTServiceB("Beta3");
        Client.setTServiceA("");
    }
    
    // Funcao que efetua os pedidos dos clientes
    public ChargingReply Request(ChargingRequest CR,BillingAccount Client, String service, boolean roaming,int rsu){
        int tarifario = 0;
        // é criado um novo Charging Request do Cliente que o pediu com as suas respetivas informações, bem como as informações do pedido
        if (service.equals("A")) { // se o cliente pretender o servico A será determinado qual o seu tarifario e, caso seja o caso, alterar nas suas informacoes de cliente
            tarifario = VerifyA(Client,CR);
            wichTarifA(Client,tarifario);
        }
        else if (service.equals("B")){ // se o cliente pretender o servico B será determinado qual o seu tarifario e, caso seja o caso, alterar nas suas informacoes de cliente
            tarifario = VerifyB(Client,CR);
            wichTarifB(Client,tarifario);
        }
        
        if (tarifario==0) { // caso nenhum tarifario seja atribuido ira ser criada uma charging reply a informar o cliente que não existe nenhum tarifario elegivel
            return new ChargingReply(super.getID()); 
        }
        
        ChargingReply C_Reply = new ChargingReply(super.getID(),"",0);
        
        return C_Reply.Reply(C_Reply,CR,Client,rsu);
    }
    
    // Funcao que efetua os pedidos dos clientes a uma hora programada
    public ChargingReply Request_Timed(ChargingRequest CR,BillingAccount Client, String service, LocalDateTime Date, boolean roaming,int rsu){
        int tarifario = 0;
        // é criado um novo Charging Request do Cliente que o pediu com as suas respetivas informações, bem como as informações do pedido
        if (service.equals("A")) {
            tarifario = VerifyA(Client,CR);
            wichTarifA(Client,tarifario);
        }
        else if (service.equals("B")){
            tarifario = VerifyB(Client,CR);
            wichTarifB(Client,tarifario);
        }
        
        if (tarifario==0) {
            return new ChargingReply(super.getID()); 
        }
        
        ChargingReply C_Reply = new ChargingReply(super.getID(),"",0);
        
        return C_Reply.Reply(C_Reply,CR,Client,rsu);
    }
}

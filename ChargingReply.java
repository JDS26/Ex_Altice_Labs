public class ChargingReply extends Charging{
    
    private String Result;
    private int GSU;
    
    /*
    request ID (na superclasse Charging)
    Result (OK; CreditLimitReached; Não Elegivel + Reason)
    GSU (Granted Service Units)
    */
    
    // Constructors
    public ChargingReply(){
        super();
        Result= "";
        GSU = 0;
    }
    
    public ChargingReply(String id){ //construtor caso nao exista nenhum tarifario elegivel
        super(id);
        Result= "Não elegível pois não existe nenhum tarifario adequado para o cliente";
        GSU = 0;
    }
    
    public ChargingReply(String id,String result,int gsu){
        super(id);
        Result= result;
        GSU = gsu;
    }
    
    public ChargingReply(ChargingReply CR){
        super(CR.getID());
        Result= CR.getResult();
        GSU = CR.getGSU();
    }
    
    // get functions
    
    public String getResult(){
        return this.Result;
    }
    
    public int getGSU(){
        return this.GSU;
    }
    
    // set functions
    
    public void setResult(String res){
        this.Result=res;
    }
    
    public void setGSU(int gsu){
        this.GSU=gsu;
    }
    
    // Clone, Equals, ToSTR
    public ChargingReply clone() {
        return new ChargingReply(this);
    }
    
    public boolean equals(Object obj) {
        if (obj == this)
          return true;
        if (obj == null || obj.getClass() != this.getClass())
          return false;
    
        ChargingReply cr = (ChargingReply) obj;
    
        return cr.getID() == super.getID();
    }
    
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
    
        sb.append("ID: ").append(super.getID()).append("\n");
        sb.append("Result (OK; CreditLimitReached; Not eligible + Reason): ").append(this.Result).append("\n");
        sb.append("GSU (Granted Service Units): ").append(this.GSU).append("\n");
        
        return sb.toString();
    }
    
    // Qual Bucket usar no pagamento dos tarifarios Alpha1 e Beta1
    public double wichBucket(BillingAccount Client,boolean roaming){
        double B1 = Client.getBucket1();
        double B2 = Client.getBucket2();
        double B3 = Client.getBucket3();
        int CB= Client.getCounterB();
        
        if (!roaming) return B1;
        else if (CB>5) return B2;
        else return B3;
        
    }
    
    // pagamentos, todos eles tem o mesmo método de verificacao de saldo usado na linha 115 
    public void Alpha1Payment(ChargingRequest CR,BillingAccount Client,boolean roaming){
        double temp;
        temp= wichBucket(Client,roaming);
        
        double B3 = Client.getBucket3();
        int CA= Client.getCounterA();
        int CB= Client.getCounterB();
        
        if(!roaming){
            // se estiver em rede local
            temp -=1.0;
            if (CR.isNight(CR.getTimeStamp())) temp -= 0.50;
            
            //DELTA
            if (CA>10) temp+= 0.25;
            if (B3>50) temp+= 0.10;
            
            // alterar o bucket necessario, caso o cliente ficasse com saldo negativo iria ser acionada uma flag e não seria feito o pagamento nem ira ser requisitada uma SU
            if (temp >=0.0) Client.setBucket1(temp);
            else CR.setFlag(true);
        }
        else {
            // se estiver em roaming
            temp-= 2.0;
            
            //DELTA
            if (CA>10) temp+= 0.25;
            if (B3>50) temp+= 0.10;
            
            // alterar o bucket necessario
            if (temp >=0.0){
                if (CB>5) Client.setBucket2(temp);
                else Client.setBucket3(temp);
            }else {
                CR.setFlag(true);
            }
            
        }
    }
    
    public void Alpha2Payment(ChargingRequest CR,BillingAccount Client){
        double B2 = Client.getBucket2();
        int CB= Client.getCounterB();
        double temp=B2;
        if (!CR.isNight(CR.getTimeStamp())){ // se nao for de noite
            temp -= 0.50;
        }
        else if (CR.isNight(CR.getTimeStamp())){ // se for de noite
            temp -= 0.25;
        }
        
        //DELTA
        if (CB>10) temp= temp + 0.2;
        if (B2>15) temp = temp + 0.05;
        
        if (temp >=0.0) Client.setBucket2(temp);
        else CR.setFlag(true);
    }
    
    public void Alpha3Payment(ChargingRequest CR,BillingAccount Client){
        double B3 = Client.getBucket3();
        int CC= Client.getCounterC();
        double temp=B3;
        
        if (!CR.isWeekend(CR.getTimeStamp())){ // se nao for de fim de semana
            temp -= 1;
        }
        else if (CR.isWeekend(CR.getTimeStamp())){ // se for de fim de semana
            temp -= 0.25;
        }
        
        //DELTA
        if (CC>10) temp= temp + 0.2;
        if (B3>15) temp = temp + 0.05;
        
        if (temp >=0.0) Client.setBucket3(temp);
        else CR.setFlag(true);
    }
    
    public void Beta1Payment(ChargingRequest CR,BillingAccount Client, boolean roaming){
        double temp;
        temp= wichBucket(Client,roaming);
        
        double B3 = Client.getBucket3();
        int CA= Client.getCounterA();
        int CB= Client.getCounterB();
        
        if(!roaming){ // se for uma chamada local
            temp -= 0.1;
            if (CR.isNight(CR.getTimeStamp())) { // se for de noite
                temp -= 0.05;
            }
            
            //DELTA
            if (CA>10) temp+= 0.025;
            if (B3>50) temp+= 0.010;
            
            // alterar o bucket necessario
            if (temp >=0.0) Client.setBucket1(temp);
            else CR.setFlag(true);
        }
        else { // se for uma chamada internacional
            temp-= 0.2;
            
            //DELTA
            if (CA>10) temp+= 0.025;
            if (B3>50) temp+= 0.010;
            
            // alterar o bucket necessario
            if (temp >=0.0){
                if (CB>5) Client.setBucket2(temp);
                else Client.setBucket3(temp);
            }else {
                CR.setFlag(true);
            }
        }
    }
    
    public void Beta2Payment(ChargingRequest CR,BillingAccount Client){
        double B2 = Client.getBucket2();
        int CB= Client.getCounterB();
        double temp=B2;
        if (!CR.isNight(CR.getTimeStamp())){ // se nao for de noite
            temp = temp-0.05;
        }
        else{ // se for de noite
            temp = temp - 0.025;
        }
        
        //DELTA
        if (CB>10) temp= temp + 0.02;
        if (B2>15) temp = temp + 0.005;
        
        if (temp >=0.0) Client.setBucket2(temp);
        else CR.setFlag(true);
    }
    
    public void Beta3Payment(ChargingRequest CR,BillingAccount Client){
        double B3 = Client.getBucket3();
        int CC= Client.getCounterC();
        double temp=B3;
        
        if (!CR.isWeekend(CR.getTimeStamp())){ // se nao for fim de semana
            temp = temp-0.10;
        }
        else if (CR.isWeekend(CR.getTimeStamp())){ // se for fim de semana
            temp = temp - 0.025;
        }
        
        //DELTA
        if (CC>10) temp= temp + 0.02;
        if (B3>15) temp = temp + 0.005;
        
        if (temp >=0.0) Client.setBucket3(temp);
        else CR.setFlag(true);
    }
    
    public void wichPayment(ChargingRequest CR,BillingAccount Client){
        String TSA = Client.getTServiceA();
        String TSB = Client.getTServiceB();
        if(TSA.equals("Alpha1")){
            Alpha1Payment(CR,Client,CR.getRoaming());
            Client.incCounterA();
        }
        else if (TSA.equals("Alpha2")){
            Alpha2Payment(CR,Client);
            Client.incCounterA();
        }
        else if (TSA.equals("Alpha3")){
            Alpha3Payment(CR,Client);
            Client.incCounterA();
        }
        
        if(TSB.equals("Beta1")){
            Beta1Payment(CR,Client,CR.getRoaming());
        }
        else if (TSB.equals("Beta2")){
            Beta2Payment(CR,Client);
        }
        else if (TSB.equals("Beta3")){
            Beta3Payment(CR,Client);
        }
    }
    
    // Resposta ao request
    public ChargingReply Reply (ChargingReply C_Rep,ChargingRequest CR,BillingAccount Client,int rsu){
        int gsu = 0;
        
        for(int i=0; i<rsu; i++){ // aqui vai fazer o pagamento consoante o numero de SU requiridas
            wichPayment(CR,Client);
            if (!CR.getFlag()) gsu++;
        }
        
        if(rsu==gsu) C_Rep.setResult("OK"); // se conseguir comprar as SU pedidas o resultado do charging reply sera "OK"
        else C_Rep.setResult("CreditLimitReached"); // se nao conseguir comprar as SU pedidas o resultado do charging reply sera "CreditLimitReached"
        
        C_Rep.setGSU(gsu); // definir o numero de SU que foram obtidas
        if (Client.getTServiceB()=="Beta1") Client.incCounterB(); // Se o tarifario for Beta 1 o counter B ira aumentar
        if (CR.getRoaming()== true) Client.incCounterC(); // Se estiver em roaming o counter C ira aumentar
        Client.setCounterD(CR.getTimeStamp()); // atualiza a data do último charging request
        Client.List_CDR(C_Rep, CR, Client); // adiciona o ultimo pedido a lista de pedidos do cliente 
        
        return C_Rep;
    }
    
}

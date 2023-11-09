import java.util.UUID;

public class Charging{
    
    private String ID;
    // ID
    
    //constructors
    public Charging(){
        ID = UUID.randomUUID().toString(); // método que cria uma
    }
    
    public Charging(String id){
        ID = id;
    }
    
    public Charging(Charging C){
        ID = C.getID();
    }
    
    // get function
    public String getID(){
        return this.ID;
    }
    
    // set function
    public void setID(String id){
        this.ID = id;
    }
    
    //Clone, Equals, ToStr
    
    public Charging clone() {
        return new Charging(this);
    }
    
    public boolean equals(Object obj) {
        if (obj == this)
          return true;
        if (obj == null || obj.getClass() != this.getClass())
          return false;
    
        ChargingRequest cr = (ChargingRequest) obj;
    
        return cr.getID() == this.ID;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
    
        sb.append("ID: ").append(ID).append("\n");
        
        return sb.toString();
    }
}

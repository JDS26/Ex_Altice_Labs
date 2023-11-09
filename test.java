import java.util.HashMap;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class test{
    public static void main() {
        HashMap<String,CDR> HC1=new HashMap <String,CDR>();
        HashMap<String,CDR> HC2=new HashMap <String,CDR>();
        HashMap<String,CDR> HC3=new HashMap <String,CDR>();
        HashMap<String,CDR> HC4=new HashMap <String,CDR>();
        HashMap<String,CDR> HC5=new HashMap <String,CDR>();
        HashMap<String,CDR> HC6=new HashMap <String,CDR>();
        HashMap<String,CDR> HC7=new HashMap <String,CDR>();
        
        BillingAccount Cliente_1 = new BillingAccount("123456789",11.0,10.0,5.0,100,2,3,LocalDateTime.MIN,"","",HC1); 
        
        BillingAccount Cliente_2 = new BillingAccount("987654321",11.0,10.0,1.0,10,2,3,LocalDateTime.MIN,"","",HC2);
        
        BillingAccount Cliente_3 = new BillingAccount("924391412",20.0,15.0,14.0,2,3,11,LocalDateTime.MIN,"","",HC3);
        
        BillingAccount Cliente_4 = new BillingAccount("933789264",11.0,10.0,11.0,101,2,3,LocalDateTime.MIN,"","",HC4);
        
        BillingAccount Cliente_5 = new BillingAccount("933789265",11.0,101.0,11.0,101,2,3,LocalDateTime.MIN,"","",HC5);
        
        BillingAccount Cliente_6 = new BillingAccount("933789266",11.0,101.0,11.0,101,2,3,LocalDateTime.MIN,"","",HC6);
        
        BillingAccount Cliente_7 = new BillingAccount("933789267",11.0,101.0,11.0,101,2,3,LocalDateTime.MIN,"","",HC7);
        
        String str = "2023-11-11 12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        
        /*
        System.out.println("Cliente_1:"+ Cliente_1);
        
        ChargingReply RQC1;
        RQC1 = Cliente_1.Request_Client(Cliente_1, "A", true,1); // nao tem nenhum tarifario disponivel
        System.out.println("\nRequest Cliente_1:"+ RQC1);
        
        System.out.println("\nCliente_1:"+ Cliente_1);
        
        System.out.println("\n");
        
        System.out.println("Cliente_2:"+ Cliente_2);
        
        ChargingReply RQC2;
        
        String str = "2023-11-11 12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        
        RQC2 = Cliente_2.Request_Client_Timed(Cliente_2, "B",dateTime, false,4); // insuficient funds
        System.out.println("\nRequest Cliente_2:"+ RQC2);
        
        System.out.println("\nCliente_2:"+ Cliente_2);
        
        System.out.println("Cliente_3:"+ Cliente_3);
        
        ChargingReply RQC3;
        RQC3 = Cliente_3.Request_Client(Cliente_3, "A", true,1); // Aplha 1
        System.out.println("\nRequest Cliente_3:"+ RQC3);
        
        System.out.println("\nCliente_3:"+ Cliente_3);
        
        ChargingReply RQC4;
        RQC4 = Cliente_3.Request_Client(Cliente_3, "B", false,1); // Beta 1
        System.out.println("\nRequest Cliente_3:"+ RQC4);
        
        System.out.println("\nCliente_3:"+ Cliente_3);
        */
       
        /*
        System.out.println("\nCliente_4:"+ Cliente_4);
        
        ChargingReply RQC5;
        RQC5 = Cliente_4.Request_Client(Cliente_4, "A", true,1); // Alpha 3
        System.out.println("\nRequest Cliente_3:"+ RQC5);
        
        System.out.println("\nCliente_4:"+ Cliente_4);
        */
        
        /*
        System.out.println("\nCliente_5:"+ Cliente_5);
        
        ChargingReply RQC6;
        RQC6 = Cliente_5.Request_Client(Cliente_5, "A", false,1); // Alpha 2
        System.out.println("\nRequest Cliente_3:"+ RQC6);
        
        System.out.println("\nCliente_4:"+ Cliente_5);
        */
        
        /*
        System.out.println("\nCliente_6:"+ Cliente_6);
        
        ChargingReply RQC7;
        RQC7 = Cliente_6.Request_Client_Timed(Cliente_6, "B",dateTime, false,4); // Beta 2 
        System.out.println("\nRequest Cliente_6:"+ RQC7);
        
        System.out.println("\nCliente_6:"+ Cliente_6);
        */
        
        System.out.println("\nCliente_7:"+ Cliente_7);
        
        ChargingReply RQC8;
        RQC8 = Cliente_7.Request_Client_Timed(Cliente_7, "B", dateTime, true,1); // Beta 3
        System.out.println("\nRequest Cliente_7:"+ RQC8);
        
        System.out.println("\nCliente_7:"+ Cliente_7);
        
    }
}

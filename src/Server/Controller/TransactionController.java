package Server.Controller;

import Common.Factories.SingletonTransactionFactory;
import Common.Model.Transaction;
import Common.Utilities.FileHandler;
import Common.Utilities.XMLCreator;
import Common.Utilities.XMLParser;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TransactionController {
    XMLParser parser;
    XMLCreator writer;
    FileHandler handler;
    File file;
    ArrayList<Transaction> transactions;

    public TransactionController(String path) {
        initializeCompontents();
        loadTransactions(path);
    }

    private void loadTransactions(String path) {
        transactions = new ArrayList<>();
        if (path.equals("base")){
            file = handler.getXMLFile("Transaction");
        }else {
            file = new File(path);
        }

        if (!file.exists()){
            System.out.println("No transactions found in: " + file.getName());
        }else {
           transactions =  parser.parse(SingletonTransactionFactory.getInstance(),file);
        }
    }

    public TransactionController(){
        initializeCompontents();
        loadTransactions("base");
    }
    void initializeCompontents(){
        parser = new XMLParser();
        writer = new XMLCreator();
        handler = new FileHandler();   
    }
    public ArrayList<Transaction> getUserTransaction(String idNumber){
        transactions.forEach(e-> System.out.println(e.getUserId()));

        return (ArrayList<Transaction>) transactions.stream().filter(e-> e.getUserId().equals(idNumber)).collect(Collectors.toList());
    }
    public synchronized boolean writeUserToXml(Transaction transaction){
      if (writer.createXML(transaction,"Transaction")){
          loadTransactions("base");
          return true;


      }else return false;
    }


    public ArrayList<Transaction> getArrayList() {
        return this.transactions;
    }

    public String[] getDateAndTime(){
        return new String[]{
          "date","time"
        };
    }

    /*
    scan xml file
    check date and time
    if same date and time > qty
    return false
    else true
     */

    public synchronized boolean concurrencyCheck(String itemName, String date, String time) {
        loadTransactions("base"); // Load transactions from the XML

        // Count occurrences of the item name
        long totalCount = transactions.stream()
                .filter(t -> t.getEquipmentName().equals(itemName))
                .count();

        // Check the maximum allowed quantity from one of the transactions
        int maxQuantity = transactions.stream()
                .filter(t -> t.getEquipmentName().equals(itemName))
                .map(Transaction::getQty)
                .findFirst()
                .orElse(0);

        if (totalCount >= maxQuantity) {
            // Count occurrences for the same date and time
            long dateTimeCount = transactions.stream()
                    .filter(t -> t.getEquipmentName().equals(itemName) &&
                            t.getDate().equals(date) &&
                            t.getTime().equals(time))
                    .count();

            if (dateTimeCount >= maxQuantity) {
                return false;
            }
        }
        return true;
    }


}

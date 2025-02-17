package Server.Controller;

import Common.Factories.SingletonTransactionFactory;
import Common.Model.Transaction;
import Common.Model.User;
import Common.Utilities.FileHandler;
import Common.Utilities.XMLCreator;
import Common.Utilities.XMLParser;

import java.io.File;
import java.util.ArrayList;

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
            file = new File("path");
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
         return (ArrayList<Transaction>) transactions.stream().filter(e-> e.getUserId().equals(idNumber));
    }
    public synchronized boolean writeUserToXml(User user){
       return writer.createXML(user,"Transaction");
    }


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.accounting.accountmanager.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 * Classe para encapsular as entradas do extrato
 */
public class AccountStatement implements Comparable<AccountStatement> {
    @JsonFormat(pattern="dd-MM-yyyy hh:mm:ss", timezone="Brazil/East")
    private Date day;//dd-MM-yyyy
    private float balance;
    private List<AccountEntry> entries;//movimentações do dia
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy", new Locale("pt", "BR"));
    
    public Date getDay() {
        return day;
    }
//
//    public void setDay(Date day) {
//        this.day = day;
//    }
//
    public float getBalance() {
        return balance;
    }
//
//    public void setBalance(float balance) {
//        this.balance = balance;
//    }
    
    public void retrieveRetrieveData(){
        Collections.reverse(entries);
        balance = entries.get(0).getAccountBalance();
        String strDay = DATE_FORMAT.format(entries.get(0).getChargedDate());
        try {
            day = DATE_FORMAT.parse(strDay);
        } catch (ParseException ex) {
            Logger.getLogger(AccountStatement.class.getName()).log(Level.SEVERE, "erro ao fazer parse de data", ex);
        }
    }
    
    public List<AccountEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<AccountEntry> entries) {
        this.entries = entries;
    }

    @Override//ordenação será feit por data
    public int compareTo(AccountStatement o) {
        if (this.day.after(o.getDay())) {
            return 1;
        } else if (this.day.before(o.getDay())){
                return -1;
        } else {
            return 0;
        }
    }
}

package com.lqf;

import com.lqf.vo.PCCSeqDAO;

public class HelloWorld {
    private String    pCCMessage;

    private PCCSeqDAO pCCSeqDAO;

    /**
     * Setter method for property <tt>pCCSeqDAO</tt>.
     * 
     * @param pCCSeqDAO value to be assigned to property pCCSeqDAO
     */
    public void setpCCSeqDAO(PCCSeqDAO pCCSeqDAO) {
        this.pCCSeqDAO = pCCSeqDAO;
    }

    /**
     * Getter method for property <tt>pCCMessage</tt>.
     * 
     * @return property value of pCCMessage
     */
    public String getpCCMessage() {
        return pCCMessage;
    }

    /**
     * Setter method for property <tt>pCCMessage</tt>.
     * 
     * @param pCCMessage value to be assigned to property pCCMessage
     */
    public void setpCCMessage(String pCCMessage) {
        this.pCCMessage = pCCMessage;
    }

    public void print() {
        pCCSeqDAO.toString();
        System.out.println("dddddddddddddddd");
    }

}
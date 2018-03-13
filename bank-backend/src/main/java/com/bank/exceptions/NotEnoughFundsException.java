package com.bank.exceptions;

public class NotEnoughFundsException extends Exception
{
    
    public NotEnoughFundsException()
    {
        super();
    }
    
    public NotEnoughFundsException(String message)
    {
        super(message);
    }
    
    public NotEnoughFundsException(String message, Throwable cause)
    {
        super(message, cause);
    }
}

package com.ibytecode.business;
import javax.ejb.Remote;
 
@Remote
public interface HelloWorld {
    public String sayHello();
}
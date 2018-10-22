package ict376.murdoch.edu.au.musikonline;

import android.content.ContentValues;

import static ict376.murdoch.edu.au.musikonline.CustomerDBSchema.*;

public class Customer {

    private String cust_id;
    private String cust_username;
    private String cust_email;
    private String cust_password;

    public Customer()
    {
        cust_id = "";
        cust_username = "";
        cust_email = "";
        cust_password = "";
    }
    public Customer(String id, String username, String email, String password)
    {
        cust_id = id;
        cust_username = username;
        cust_email = email;
        cust_password = password;
    }

    public void setCust_id(String id)
    {
        cust_id = id;
    }

    public String getCust_id()
    {
        return cust_id;
    }

    public void setCust_username(String username)
    {
        cust_username = username;
    }

    public String getCust_username()
    {
        return cust_username;
    }

    public void setCust_email(String email)
    {
        cust_email = email;
    }

    public String getCust_email()
    {
        return cust_email;
    }

    public void setCust_password(String password)
    {
        cust_password = password;
    }

    public String getCust_password()
    {
        return cust_password;
    }


}

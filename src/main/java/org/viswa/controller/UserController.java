package org.viswa.controller;

import org.viswa.database.InMemoryDB;


public class UserController {
    
    public int validateUser(String userName, String userPassWord){
           for(Integer id: InMemoryDB.users.keySet()){
              String name=InMemoryDB.users.get(id).getUserName();
              String password=InMemoryDB.users.get(id).getPassWord();
              if(name.equals(userName) && password.equals(userPassWord)){
                return id;
              }

           }
           return 0;
    }
}

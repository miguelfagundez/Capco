package com.devproject.miguelfagundez.capco.model.pojo;


/********************************************
 * Class- AuthenticationClaim
 * Model layer where I check if user is a
 * basic or admin user
 * @author: Miguel Fagundez
 * @date: May 11th, 2020
 * @version: 1.0
 * *******************************************/
public class Authority {

    private String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "authority='" + authority + '\'' +
                '}';
    }
}

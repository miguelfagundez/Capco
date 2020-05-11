package com.devproject.miguelfagundez.capco.model.pojo;

/********************************************
 * Class- Jwt
 * Pojo class to authentication token
 * @author: Miguel Fagundez
 * @date: May 10th, 2020
 * @version: 1.0
 * *******************************************/
public class Jwt {

    private String jwt;

    public Jwt(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "Jwt{" +
                "jwt='" + jwt + '\'' +
                '}';
    }
}

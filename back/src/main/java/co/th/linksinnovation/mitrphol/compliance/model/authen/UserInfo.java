/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.model.authen;

import com.fasterxml.jackson.annotation.JsonProperty;



/**
 *
 * @author Piyawut Chiradejnunt<pchiradejnunt@gmail.com>
 */
@lombok.Data
public class UserInfo {
    private String id;
    private String username;
    private Fullname fullname;
    private String email;
    private String citizenId;
    private String nickname;
    private String photo;
    private String description;
    private String line;
    private Telephone telephone;
}

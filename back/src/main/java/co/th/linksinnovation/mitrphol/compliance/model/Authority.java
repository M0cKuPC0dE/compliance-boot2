/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author Piyawut Chiradejnunt<pchiradejnunt@gmail.com>
 */
@Data
@Entity
public class Authority implements GrantedAuthority{

    @Id
    private String authority;
    
    @Override
    public String getAuthority() {
        return this.authority;
    }

    public Authority() {
    }
    
    public Authority(String authority) {
        this.authority = authority;
    }
    
    

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author Piyawut Chiradejnunt<pchiradejnunt@gmail.com>
 */
@Data
@Entity
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    
    @Id
    private String username;
    private String password;
    private String userId;
    private String nameEn;
    private String nameTh;
    private String email;
    private String photo;
    private String uuid;
    
    
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void addAuthority(Authority authority){
        if(this.authorities == null){
            this.authorities = new ArrayList<>();
        }
        this.authorities.add(authority);
    }
    
    public void removeAuthority(Authority authority){
        if(this.authorities.contains(authority)){
            this.authorities.remove(authority);
        }
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    
}

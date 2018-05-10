/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.repository;

import co.th.linksinnovation.mitrphol.compliance.model.UserDetails;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Piyawut Chiradejnunt<pchiradejnunt@gmail.com>
 */
public interface UserDetailsRepository extends JpaRepository<UserDetails, String>{

    public List<UserDetails> findByUserId(String userId);
    
    @Query( "select u from UserDetails u inner join u.authorities r where r.authority in :roles" )
    public List<UserDetails> findUserByRole(@Param("roles") List<String> roles);

    public List<UserDetails> findByUuidAndUuidNotNull(String name);
    
}

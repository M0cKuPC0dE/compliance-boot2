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
public class Data {
    @JsonProperty("user_info")
    private UserInfo userInfo;
    
    @JsonProperty("commander_info")
    private CommanderInfo commanderInfo;
    
    @JsonProperty("job_info")
    private JobInfo jobInfo;
    
    private String accessToken;
}

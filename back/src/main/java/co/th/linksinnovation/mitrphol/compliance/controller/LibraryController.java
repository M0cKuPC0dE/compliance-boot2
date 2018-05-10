/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.controller;

import co.th.linksinnovation.mitrphol.compliance.model.Category;
import co.th.linksinnovation.mitrphol.compliance.model.Compliance;
import co.th.linksinnovation.mitrphol.compliance.model.JsonViewer;
import co.th.linksinnovation.mitrphol.compliance.service.LibraryService;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jirawong
 */
@RestController
@RequestMapping("/public/library")
public class LibraryController {
    
    @Autowired
    private LibraryService libraryService;
    
    @JsonView({JsonViewer.LibraryCategoryView.class})
    @Cacheable(value = "library-categories")
    @GetMapping
    public List<Category> get(){
        return libraryService.getCategory();
    }
    
    @JsonView({JsonViewer.LibraryComplianceView.class})
    @Cacheable(value = "library-compliance")
    @GetMapping("/compliance/{id}")
    public Compliance getCompliance(@PathVariable("id") Long id) throws Exception{
        return libraryService.getCompliance(id);
    }
    
}

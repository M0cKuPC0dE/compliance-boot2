/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.service;

import co.th.linksinnovation.mitrphol.compliance.model.Category;
import co.th.linksinnovation.mitrphol.compliance.model.Compliance;
import co.th.linksinnovation.mitrphol.compliance.repository.CategoryRepository;
import co.th.linksinnovation.mitrphol.compliance.repository.ComplianceRepository;
import java.util.List;
import java.util.Optional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jirawong
 */
@Service
public class LibraryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ComplianceRepository complianceRepository;

    public List<Category> getCategory() {
        List<Category> categories = categoryRepository.findByParentIsNullAndDeletedIsFalse();
        categoryInitialze(categories);
        return categories;
    }

    private void categoryInitialze(List<Category> categories) {
        categories.forEach((c) -> {
            Hibernate.initialize(c.getChilds());
            Hibernate.initialize(c.getCompliances());
            categoryInitialze(c.getChilds());
        });
    }

    public Compliance getCompliance(Long id) {
        Optional<Compliance> compliance = complianceRepository.findById(id);
        if(compliance.isPresent()){
            Compliance get = compliance.get();
            Hibernate.initialize(get.getLegalFiles());
            return get;
        }
        return null;
    }

}

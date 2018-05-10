/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.controller;

import co.th.linksinnovation.mitrphol.compliance.service.DownloadService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author jirawong
 */
@Controller
@RequestMapping("/public/download")
public class DownloadController {

    @Autowired
    private DownloadService downloadService;

    @GetMapping("/legal/{id}")
    public void legalFile(@PathVariable("id") Long id, HttpServletResponse response) {
        downloadService.legalFileDownload(id, response);
    }

    @GetMapping("/license/{id}")
    public void licenseFile(@PathVariable("id") Long id, HttpServletResponse response) {
        downloadService.licenseFileDownload(id, response);
    }

    @GetMapping("/evidence/{id}")
    public void evidenceFile(@PathVariable("id") Long id, HttpServletResponse response) {
        downloadService.evidenceFileDownload(id, response);
    }
}

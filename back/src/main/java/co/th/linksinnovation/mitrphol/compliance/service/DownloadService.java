/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.linksinnovation.mitrphol.compliance.service;

import co.th.linksinnovation.mitrphol.compliance.model.EvidenceFile;
import co.th.linksinnovation.mitrphol.compliance.model.LegalFile;
import co.th.linksinnovation.mitrphol.compliance.model.LicenseFile;
import co.th.linksinnovation.mitrphol.compliance.repository.EvidenceFileRepository;
import co.th.linksinnovation.mitrphol.compliance.repository.LegalFileRepository;
import co.th.linksinnovation.mitrphol.compliance.repository.LicenseFileRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

/**
 *
 * @author jirawong
 */
@Service
public class DownloadService {

    @Autowired
    private LegalFileRepository legalFileRepository;
    @Autowired
    private LicenseFileRepository licenseFileRepository;
    @Autowired
    private EvidenceFileRepository evidenceFileRepository;

    public void legalFileDownload(Long id, HttpServletResponse response) {
        LegalFile legalFile = legalFileRepository.findById(id).get();
        String path = (legalFile.getUuid() == null ? legalFile.getName() : legalFile.getUuid());
        servletFileDownload(legalFile.getName(), path, response);
    }

    public void licenseFileDownload(Long id, HttpServletResponse response) {
        LicenseFile licenseFile = licenseFileRepository.findById(id).get();
        String path = (licenseFile.getUuid() == null ? licenseFile.getName() : licenseFile.getUuid());
        servletFileDownload(licenseFile.getName(), path, response);
    }

    public void evidenceFileDownload(Long id, HttpServletResponse response) {
        EvidenceFile evidenceFile = evidenceFileRepository.findById(id).get();
        String path = (evidenceFile.getUuid() == null ? evidenceFile.getName() : evidenceFile.getUuid());
        servletFileDownload(evidenceFile.getName(), path, response);
    }

    private void servletFileDownload(String name, String path, HttpServletResponse response) {
        InputStream in = null;
        try {
            final String dataDirectory = "/mnt/data/files/" + path;
            final File file = new File(dataDirectory);
            in = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(name, "UTF-8"));
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("language", "th-TH");
            response.setContentType("application/octet-stream");
            FileCopyUtils.copy(in, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            Logger.getLogger(DownloadService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(DownloadService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

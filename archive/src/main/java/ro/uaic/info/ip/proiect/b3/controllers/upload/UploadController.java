package ro.uaic.info.ip.proiect.b3.controllers.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.uaic.info.ip.proiect.b3.authentication.AuthenticationManager;
import ro.uaic.info.ip.proiect.b3.controllers.DashboardController;
import ro.uaic.info.ip.proiect.b3.storage.StorageException;
import ro.uaic.info.ip.proiect.b3.storage.StorageService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Aceasta clasa reprezinta un controller pentru metoda POST a paginii de upload a unui fisier.
 */
@Controller
public class UploadController {
    private final StorageService storageService;

    @Autowired
    public UploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public String welcome() {
        return "upload";
    }

    @RequestMapping(value="/uploadFile", method=RequestMethod.POST)
    public @ResponseBody String upload(@RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
        String filename = new Date().getTime() + "_" + file.getOriginalFilename();

        try{
            byte barr[]=file.getBytes();

            BufferedOutputStream bout=new BufferedOutputStream(new FileOutputStream("src/main/resources/uploaded-user-files/" + filename));
            bout.write(barr);
            bout.flush();
            bout.close();

        }catch(Exception e){
            System.out.println(e);
        }

        return "Succes upload";
    }
//    public @ResponseBody String upload(@RequestParam("file") MultipartFile file, HttpServletResponse response, @CookieValue(value = "user", defaultValue = "-1") String loginToken) {
//        if (AuthenticationManager.isUserLoggedIn(loginToken)) {
//            String username = AuthenticationManager.getUsernameLoggedIn(loginToken);
//
//            try {
//                storageService.store(username, file);
//            } catch (StorageException e) {
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                return "error=" + e.getMessage();
//            }
//
//            return "success";
//        } else {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return "error=unauthorized";
//        }
//    }
}
package br.com.casadocodigo.loja.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileSaver {
	@Autowired
    private HttpServletRequest request;
   
	// trata da transferencia do arquivo - recebe onde será salvo e o arquivo
    public String write(String baseFolder, MultipartFile file) {
        try {
            String realPath = request.getServletContext().getRealPath("/"+baseFolder);
            String path = realPath + "/" + file.getOriginalFilename();
            //String path = "/Users/adrianareigadas/Documents/Study/casadocodigo/"+baseFolder+"/"+file.getOriginalFilename();
            System.out.println("BaseFolder "+baseFolder);
            System.out.println("Realpath "+realPath);
            System.out.println("Path "+path);
            file.transferTo(new File(path));
            return baseFolder + "/" + file.getOriginalFilename();


        } catch (IllegalStateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

	
}

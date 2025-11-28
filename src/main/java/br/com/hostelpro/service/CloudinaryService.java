package br.com.hostelpro.service;
import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImageBase64(String base64, Integer estabelecimentoId) throws IOException {

        String prefix = "";
        String data = base64;

        if (base64.contains(",")) {
            String[] parts = base64.split(",");
            prefix = parts[0]; 
            data = parts[1];
        }

        String cloudinaryPayload = prefix.isEmpty() ? "data:image/png;base64," + data : prefix + "," + data;

        // Pasta dinâmica baseada no ID do estabelecimento
        String folderName = "estabelecimentos/" + estabelecimentoId;

        Map uploadResult = cloudinary.uploader().upload(
                cloudinaryPayload,
                ObjectUtils.asMap(
                        "folder", folderName
                )
        );

        return uploadResult.get("secure_url").toString();
    }
}

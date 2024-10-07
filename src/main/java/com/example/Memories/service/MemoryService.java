package com.example.Memories.service;

import com.example.Memories.model.Memory;
import com.example.Memories.model.User;
import com.example.Memories.model.repositories.MemoryRepository;
import com.example.Memories.model.repositories.UserRepository;
import com.example.Memories.model.response.ImgurAlbumCreationResponse;
import com.example.Memories.model.response.ImgurImage;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemoryService {
    private final MemoryRepository memoryRepository;
    private final ImgurService imgurService;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(MemoryService.class);

    public MemoryService(MemoryRepository memoryRepository, ImgurService imgurService, UserRepository userRepository) {
        this.memoryRepository = memoryRepository;
        this.imgurService = imgurService;
        this.userRepository = userRepository;
    }

    public List<Memory> findAll(User user) {
        logger.info("Finding all user's memories.");
        List<Memory> memories = memoryRepository.findByUser(user);
        logger.info("Memories found.");
        return memories;
    }

    @Transactional
    public User saveMemory(
            User user,
            String title,
            String description,
            String startDate,
            String endDate,
            List<MultipartFile> files
    ) throws ParseException, IOException {
        // Upload all files to account
        List<ImgurImage> imgurImages = new ArrayList<>();
        for (MultipartFile file : files) {
            imgurImages.add(imgurService.uploadImage(user, file, "", ""));
        }

        // Create album - representation of Memory object
        ImgurAlbumCreationResponse imgurAlbumCreationResponse = imgurService.createAlbum(user, title, description, imgurImages);

        // Create and save Memory in database
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Memory memory = new Memory(
                title,
                description,
                imgurAlbumCreationResponse.getId(),
                formatter.parse(startDate),
                formatter.parse(endDate),
                user
        );
        memoryRepository.save(memory);
        return user;
    }
}

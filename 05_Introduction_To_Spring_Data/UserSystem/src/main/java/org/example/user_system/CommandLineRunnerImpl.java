package org.example.user_system;

import jakarta.validation.ConstraintViolation;
import jdk.jfr.Category;
import org.example.user_system.data.entities.Album;
import org.example.user_system.data.entities.Picture;
import org.example.user_system.data.entities.Town;
import org.example.user_system.data.entities.User;
import org.example.user_system.service.AlbumService;
import org.example.user_system.service.PictureService;
import org.example.user_system.service.TownService;
import org.example.user_system.service.UserService;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.validation.Validator;
import java.time.LocalDateTime;
import java.util.Set;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final UserService userService;
    private final TownService townService;
    private final AlbumService albumService;
    private final PictureService pictureService;

    public CommandLineRunnerImpl(UserService userService,
                                 TownService townService,
                                 AlbumService albumService,
                                 PictureService pictureService) {
        this.userService = userService;
        this.townService = townService;
        this.albumService = albumService;
        this.pictureService = pictureService;
    }

    @Override
    public void run(String... args) throws Exception {
        fillTheDB();
//        this.userService.findAllByEmailEndingWith("abv.bg");
//
//        // Example: Remove inactive users who haven't logged in since one year ago
//        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
//        int removedUsersCount = userService.removeInactiveUsers(oneYearAgo);
//        System.out.println(removedUsersCount + " users have been marked as deleted.");
//
    }

    private void fillTheDB() {
        // Generate and save towns
        Town town1 = new Town("Plovdiv", "Bulgaria");
        Town town2 = new Town("Sofia", "Bulgaria");
        townService.saveTown(town1);
        townService.saveTown(town2);

        // Generate and save users
        User user1 = new User("----Ivan", "Dachev", "dachev1", "11111111", "dachev3131@gmail.com");
        user1.setBornTown(town1);
        user1.setCurrentTown(town2);
        user1.setRegisteredOn(LocalDateTime.now());
        user1.setLastTimeLoggedIn(LocalDateTime.now());
        userService.saveUser(user1);

        User user2 = new User("Petq", "Staikova", "pepaS", "pepaPig", "pepa_smile@abv.bg");
        user2.setBornTown(town1);
        user2.setCurrentTown(town2);
        user2.setRegisteredOn(LocalDateTime.now());
        user2.setLastTimeLoggedIn(LocalDateTime.now());
        userService.saveUser(user2);

        // Generate and save albums
        Album album1 = new Album("My Vacation", "blue", true);
        album1.setOwner(user1);
        albumService.saveAlbum(album1);

        Album album2 = new Album("Family Photos", "green", false);
        album2.setOwner(user2);
        albumService.saveAlbum(album2);

        // Generate and save pictures
        Picture picture1 = new Picture("Beach", "At the beach", "/images/beach.jpg");
        picture1.getAlbums().add(album1);
        pictureService.savePicture(picture1);

        Picture picture2 = new Picture("Dinner", "Family dinner", "/images/dinner.jpg");
        picture2.getAlbums().add(album2);
        pictureService.savePicture(picture2);

        System.out.println("Sample data initialized successfully.");
    }
}